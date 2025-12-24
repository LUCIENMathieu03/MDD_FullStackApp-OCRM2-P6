package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.RegisterRequestDTO;
import com.openclassrooms.mddapi.dto.TokenResponseDTO;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth", description = "Gestion de l'authentification")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "Bienvenue sur l’API MDD 👋";
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        try {
            userService.registerUser(dto);
            TokenResponseDTO token = userService.authenticateUser(dto.getEmail(), dto.getPassword());

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            TokenResponseDTO token = userService.authenticateUser(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
