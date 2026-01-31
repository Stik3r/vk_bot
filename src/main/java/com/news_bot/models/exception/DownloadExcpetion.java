package com.news_bot.models.exception;

public class DownloadExcpetion extends RuntimeException {
    public DownloadExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
