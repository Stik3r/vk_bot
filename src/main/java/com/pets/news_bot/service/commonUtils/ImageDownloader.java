package com.pets.news_bot.service.commonUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ImageDownloader {

    public static Object downloadImage(String link){
        try {
            Connection.Response resultImageResponse = Jsoup.connect(link)
                    .ignoreContentType(true).execute();
            return resultImageResponse.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
