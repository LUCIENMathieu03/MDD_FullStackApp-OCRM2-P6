package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    String email;
    String name;
    String password;
}
