package com.pets.news_bot.service.parser;

import core.models.NewsData;

import java.util.List;


public interface Parser {

    List<NewsData> parseData();
}
