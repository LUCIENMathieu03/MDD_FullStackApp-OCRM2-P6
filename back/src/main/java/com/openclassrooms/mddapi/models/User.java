package com.openclassrooms.mddapi.models;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String email;

    String name;

    String password;

    Integer[] themeSubscription;
}
