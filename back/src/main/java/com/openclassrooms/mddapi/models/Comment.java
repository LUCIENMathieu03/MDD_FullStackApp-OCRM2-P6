package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String content;
    String author;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    // FOREIGN KEY : Chaque commentaire appartient à UN article
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId")
    private Article article;
}
