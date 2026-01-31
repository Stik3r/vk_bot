package com.news_bot.job;

import com.news_bot.NewsBot;
import com.news_bot.models.exception.JobCreateException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class NewsChecker implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        NewsBot.generateNews();
    }

    public static Scheduler makeScheduler() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(NewsChecker.class)
                    .withIdentity("newsCheck", "group")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger", "group")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?")) // Каждые 5 секунд
                    .build();
            scheduler.scheduleJob(job, trigger);
            return scheduler;
        } catch (SchedulerException e) {
            throw new JobCreateException("Ошибка создания задачи", e);
        }
    }
}
