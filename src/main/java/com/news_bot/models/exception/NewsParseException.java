package com.news_bot.models.exception;

public class NewsParseException extends RuntimeException {
    public NewsParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
