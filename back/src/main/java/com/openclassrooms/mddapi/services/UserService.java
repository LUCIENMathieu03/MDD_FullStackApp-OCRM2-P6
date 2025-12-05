package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.RegisterRequestDTO;
import com.openclassrooms.mddapi.dto.TokenResponseDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserUpdateRequestDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public User getCurrentUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {

            throw new AccessDeniedException("User not authenticated");
        }
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public UserDTO updateUser(UserUpdateRequestDTO dto){
        User currentUser = getCurrentUserFromSecurityContext();

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            currentUser.setName(dto.getName().trim());
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            if (userRepository.existsByEmailAndIdNot(dto.getEmail().trim(), currentUser.getId())) {
                throw new RuntimeException("Cet email est déjà utilisé");
            }
            currentUser.setEmail(dto.getEmail().trim());
        }

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(currentUser);

        return toDTO(currentUser);
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setThemeSubscription(user.getThemeSubscription());

        return userDTO;
    }
}
