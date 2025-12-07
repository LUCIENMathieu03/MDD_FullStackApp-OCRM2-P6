package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    ThemeRepository themeRepository;

    public List<ArticleDTO> getAllArticle() {
        List<ArticleDTO> ArticleDtoList = new ArrayList<>();
        for (Article article : articleRepository.findAll()) {
            ArticleDtoList.add(toDTO(article));
        }
        return ArticleDtoList;
    }

    //now this is useless because of function below
    public ArticleDTO getArticleById(Integer id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));
        return toDTO(article);
    }

    public ArticleWithCommentsDTO getArticleWithComments(Integer id){
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article introuvable"));
        return toArticleWithCommentsDTO(article);
    }

    public ArticleDTO createArticle(ArticleCreateRequestDTO dto) {
        if (articleRepository.existsByTitle(dto.getTitle())) {
            throw new RuntimeException("Article Title already used");
        }

        Article articleToCreate = new Article();
        User currentUser = userService.getCurrentUserFromSecurityContext();
        Theme dtoTheme = themeRepository.findByName(dto.getTheme());

        articleToCreate.setTitle(dto.getTitle());
        articleToCreate.setAuthor(currentUser.getName());
        articleToCreate.setTheme(dtoTheme);
        articleToCreate.setContent(dto.getContent());

        Article savedArticle = articleRepository.save(articleToCreate);

        return toDTO(savedArticle);
    }

    public CommentDTO createComment(Integer id, CommentCreateRequestDTO dto){
        Comment commentToCreate = new Comment();

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));

        commentToCreate.setArticle(article);
        commentToCreate.setAuthor(article.getAuthor());
        commentToCreate.setContent(dto.getContent());

        Comment savedComment = commentRepository.save(commentToCreate);

        return toCommentDTO(savedComment);
    }

    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setAuthor(article.getAuthor());
        articleDTO.setTheme(article.getTheme().getName());
        articleDTO.setContent(article.getContent());

        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUpdatedAt(article.getUpdatedAt());

        return articleDTO;
    }

    public ArticleWithCommentsDTO toArticleWithCommentsDTO(Article article) {
        ArticleWithCommentsDTO dto = new ArticleWithCommentsDTO();

        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setAuthor(article.getAuthor());
        dto.setTheme(article.getTheme().getName());
        dto.setContent(article.getContent());
        dto.setCreatedAt(article.getCreatedAt());
        dto.setUpdatedAt(article.getUpdatedAt());

        dto.setComments(article.getComments().stream()
                .map(this::toCommentDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public CommentDTO toCommentDTO(Comment comment){
        CommentDTO dto = new CommentDTO();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthor(comment.getAuthor());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setArticleId(comment.getArticle().getId());

        return dto;

    }
}
