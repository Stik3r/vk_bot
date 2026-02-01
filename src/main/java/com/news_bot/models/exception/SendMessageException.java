package com.news_bot.models.exception;

public class SendMessageException extends RuntimeException {
    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
