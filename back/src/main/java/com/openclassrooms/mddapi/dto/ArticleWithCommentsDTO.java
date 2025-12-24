package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Europe/Paris" )
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris" )
    private Timestamp updatedAt;


    private List<CommentDTO> comments;
}
