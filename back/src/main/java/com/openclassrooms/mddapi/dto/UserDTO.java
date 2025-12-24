package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Europe/Paris" )
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Europe/Paris" )
    private Timestamp updatedAt;
}