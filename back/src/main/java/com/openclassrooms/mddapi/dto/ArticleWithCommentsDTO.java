package com.openclassrooms.mddapi.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ArticleWithCommentsDTO {
    private Integer id;
    private String title;
    private String author;
    private String theme;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    private List<CommentDTO> comments;
}
