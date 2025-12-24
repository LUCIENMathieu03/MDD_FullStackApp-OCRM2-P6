package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ThemeCreateRequestDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theme")
@Tag(name = "Theme", description = "Gestion des theme")
@SecurityRequirement(name = "bearerAuth")
public class ThemeController {

    @Autowired
    ThemeService themeService;

    @PostMapping("")
    public ResponseEntity<?> createTheme(@RequestBody ThemeCreateRequestDTO dto){
        try{
            ThemeDTO createdTheme = themeService.createTheme(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTheme(){
        try{
            List<ThemeDTO> themes = themeService.getAllTheme();
            return ResponseEntity.ok(themes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
