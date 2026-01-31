package com.news_bot.parser;

import com.news_bot.models.NewsData;

import java.util.List;


public interface Parser {

    List<NewsData> parseData();
}
