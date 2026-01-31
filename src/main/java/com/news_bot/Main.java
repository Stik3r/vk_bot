package com.news_bot;

import com.news_bot.job.NewsChecker;
import org.quartz.Scheduler;


public class Main {

    public static void main(String[] args) {
        try {
            Scheduler scheduler = NewsChecker.makeScheduler();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
