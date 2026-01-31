package com.news_bot;

import com.news_bot.job.NewsChecker;
import com.news_bot.vk_client.VKService;
import io.github.cdimascio.dotenv.Dotenv;
import org.quartz.Scheduler;


public class Main {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            VKService vk = new VKService(dotenv.get("GROUP_ACCESS_TOKEN"),
                    Long.parseLong(dotenv.get("GROUP_ID")),
                    Long.parseLong(dotenv.get("MY_ID")));
            Scheduler scheduler = NewsChecker.makeScheduler();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
