package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private Integer id;
    private String content;
    private String author;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris")
    private Timestamp createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris")
    private Timestamp updatedAt;


    private Integer articleId;
}