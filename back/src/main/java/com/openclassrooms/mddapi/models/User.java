package com.openclassrooms.mddapi.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions = new ArrayList<>();

    //non utilisé
    @OneToMany(mappedBy = "author", fetch=FetchType.LAZY)
    private  List<Comment> comments = new ArrayList<>();
    //non utilisé
    @OneToMany(mappedBy = "author", fetch=FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();
}
