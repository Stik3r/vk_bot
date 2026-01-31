package com.news_bot.parser;

import com.news_bot.models.dto.NewsData;
import com.news_bot.models.exception.NewsParseException;
import com.news_bot.parser.utils.ImageDownloader;
import com.news_bot.parser.utils.RssData;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class CherInfo implements Parser {

    private final static String rssLink = "https://cherinfo.ru/rss/news";

    private Date lastPubDate;

    @Override
    public List<NewsData> parseData() {
        List<NewsData> newsList = new ArrayList<>();
        SyndFeed feed = checkNewsUpdate();

        if (feed == null) {
            return newsList;
        }

        feed.setEntries(findFirstNews(feed.getEntries()));

        for (SyndEntry entry : feed.getEntries()) {
            NewsData newsData = getNews(entry.getLink());
            newsData.setTitle(entry.getTitle());
            newsData.setDate(entry.getPublishedDate());

            newsList.add(newsData);
        }

        return newsList;
    }

    private SyndFeed checkNewsUpdate() {
        SyndFeed feed = RssData.getData(rssLink);
        Date lastNewsDate = feed.getEntries().get(0).getPublishedDate();

        if (lastNewsDate.equals(lastPubDate)) {
            return null;
        }

        return feed;
    }

    private List<SyndEntry> findFirstNews(List<SyndEntry> entries) {
        int indx = 0;

        for (SyndEntry entry : entries) {
            if (entry.getPublishedDate().equals(lastPubDate) || indx == 5) {
                indx = entries.indexOf(entry) + 1;
                break;
            }
            indx++;
        }

        entries = entries.subList(0, indx);
        lastPubDate = entries.get(0).getPublishedDate();
        Collections.reverse(entries);

        return entries;
    }

    private NewsData getNews(String link) {
        try {
            Document doc = Jsoup.connect(link).get();
            Element element = doc.getElementsByClass("article-text").first();
            StringBuilder description = new StringBuilder();
            NewsData news = new NewsData();

            for (Element e : element.getAllElements().subList(1, element.getAllElements().size())) {

                switch (e.tagName()) {
                    case "p":
                        description.append((e.text().isEmpty() || !e.getElementsByTag("iframe").isEmpty()) ? "" : e.text());
                        Element img = e.getElementsByTag("img").first();
                        if (img != null) {
                            news.setImages(downloadImages(new Elements(List.of(img))));
                        }
                        break;
                    case "div":
                        news.setImages(downloadImages(e.getElementsByTag("a")));
                        break;
                    default:
                }
            }

            news.setDescription(description.toString());
            return news;

        } catch (IOException e) {
            throw new NewsParseException("Ошибка парсинга", e);
        }
    }

    private List<Object> downloadImages(Elements images) {
        List<Object> result = new ArrayList<>();

        for (Element img : images) {
            String link = img.attr("href").isEmpty() ? img.attr("src") : img.attr("href");
            result.add(ImageDownloader.downloadImage(link));
        }
        return result;
    }

}
