package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<?> getAllArticles() {
        try {
            List<ArticleDTO> articles = articleService.getAllArticle();
            return ResponseEntity.ok(articles);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getArticleById(@PathVariable int id) {
//        try {
//            ArticleDTO article = articleService.getArticleById(id);
//            return ResponseEntity.ok(article);
//
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleWithComments(@PathVariable Integer id) {
        try {
            ArticleWithCommentsDTO article = articleService.getArticleWithComments(id);
            return ResponseEntity.ok(article);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createArticle(@RequestBody ArticleCreateRequestDTO dto) {
        try {
            ArticleDTO createdArticle = articleService.createArticle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> commentArticle(@PathVariable Integer id, @RequestBody CommentCreateRequestDTO dto){
        try{
            CommentDTO createdComment = articleService.createComment(id, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
