package com.news_bot;

import com.news_bot.models.NewsData;
import com.news_bot.parser.CherInfo;
import com.news_bot.parser.Parser;
import com.news_bot.vk_client.VKService;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;

public class NewsBot {

    private static List<Parser> parsers;
    private static VKService vkService;

    static {
        Dotenv dotenv = Dotenv.load();
        vkService = new VKService(dotenv.get("GROUP_ACCESS_TOKEN"),
                Long.parseLong(dotenv.get("GROUP_ID")),
                Long.parseLong(dotenv.get("MY_ID")));

        parsers = new ArrayList<>();
        parsers.add(new CherInfo());
    }

    public static void generateNews() {
        for (Parser parser : parsers) {
            sendNews(parser.parseData());
        }
    }

    private static void sendNews(List<NewsData> news) {
        for (NewsData newsData : news) {
            if (newsData.isEmpty()) {
                continue;
            }

            vkService.sendMessage(newsData.getDescription());
        }
    }
}
