package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ArticleCreateRequestDTO {
    private String title;
    private String theme;
    private String content;
}
