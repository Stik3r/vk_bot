package com.news_bot.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NewsData {
    private String title;
    private String description;
    private Date date;
    private List<Object> images;

    public boolean isEmpty(){
        return description == null || description.isEmpty();
    }
}
