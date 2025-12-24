package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ArticleDTO {
    private int id;
    private String title;
    private String author;
    private String theme;
    private String content;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Europe/Paris" )
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris" )
    private Timestamp updatedAt;
}
