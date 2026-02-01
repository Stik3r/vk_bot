package com.news_bot;

import com.news_bot.models.dto.NewsData;
import com.news_bot.parser.CherInfo;
import com.news_bot.parser.Parser;
import com.news_bot.vk_client.VKService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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

    private NewsBot() {
        throw new IllegalStateException("Static class");
    }

    public static void generateNews() {
        log.info("Начата задача получения новостей");
        int count;
        String logInfo;

        for (Parser parser : parsers) {
            count = sendNews(parser.parseData());
            logInfo = String.format("Новостей из ресурса %s - %d", parser.getName(), count);
            log.info(logInfo);
        }
    }

    private static int sendNews(List<NewsData> news) {
        int count = 0;
        for (NewsData newsData : news) {
            if (newsData.isEmpty()) {
                continue;
            }

            if(newsData.getImages() == null){
                vkService.sendMessage(newsData.getDescription());
            }
            else {
                vkService.sendMessage(newsData.getDescription(), newsData.getImages());
            }

            count++;
        }
        return count;
    }
}
