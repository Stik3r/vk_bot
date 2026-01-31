package com.news_bot;

import com.news_bot.job.NewsChecker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            Scheduler scheduler = NewsChecker.makeScheduler();
            scheduler.start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
