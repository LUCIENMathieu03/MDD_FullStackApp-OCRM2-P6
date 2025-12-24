package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ThemeDTO {
    private int id;
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Europe/Paris" )
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris" )
    private Timestamp updatedAt;
}
