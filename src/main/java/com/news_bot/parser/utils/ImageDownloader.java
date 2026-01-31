package com.news_bot.parser.utils;

import com.news_bot.models.exception.DownloadExcpetion;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public class ImageDownloader {

    private ImageDownloader() {
        throw new IllegalStateException("Utility class");
    }

    public static Object downloadImage(String link){
        try {
            Connection.Response resultImageResponse = Jsoup.connect(link)
                    .ignoreContentType(true).execute();
            return resultImageResponse.body();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new DownloadExcpetion("Ошибка загрузки", e);
        }
    }
}
