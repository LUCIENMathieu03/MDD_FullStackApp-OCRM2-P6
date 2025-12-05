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
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;

    String author;

    String theme;

    String content;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    // RELATION : Un article → plusieurs commentaires
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
