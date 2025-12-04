package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.RegisterRequestDTO;
import com.openclassrooms.mddapi.dto.TokenResponseDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public User getUserById(final Integer Id){
        return userRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }


    public void registerUser(RegisterRequestDTO registerRequestDTO) throws RuntimeException {
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already used");
        }

        User user = new User();
        user.setName(registerRequestDTO.getName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        userRepository.save(user);
    }

    public TokenResponseDTO authenticateUser(String login, String password) {

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(login, password);
        Authentication authentication = authenticationManager.authenticate(authRequest);
        String token = jwtService.generateToken(authentication);

        return new TokenResponseDTO(token);
    }
}
