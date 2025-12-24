package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.List;

public class ThemeWithArticleDTO {

    private int id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Europe/Paris" )
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris" )
    private Timestamp updatedAt;

    private List<CommentDTO> article;

}
