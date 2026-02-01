package com.news_bot.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class ImageDto {
    private String name;
    private Path path;
}
