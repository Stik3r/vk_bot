package com.pets.news_bot;

import com.pets.news_bot.service.VKService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsBotApplication.class, args);
        try {
            VKService vk = new VKService();
            vk.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
