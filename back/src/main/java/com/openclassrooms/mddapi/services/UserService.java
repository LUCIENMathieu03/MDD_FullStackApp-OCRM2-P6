package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ThemeRepository themeRepository;


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

    public UserDTO registerUser(RegisterRequestDTO registerRequestDTO) throws RuntimeException {
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already used");
        }

        User user = new User();
        user.setName(registerRequestDTO.getName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public UserDTO updateUser(UserUpdateRequestDTO dto) {
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

    public List<ThemeDTO> getAllUserSubscription() {
        User currentUser = getCurrentUserFromSecurityContext();

        List<Theme> themes = currentUser.getSubscriptions()
                .stream()
                .map(Subscription::getTheme)
                .toList();

        List<ThemeDTO> themeDTOList = new ArrayList<>();
        for (Theme theme : themes) {
            themeDTOList.add(toThemeDTO(theme));
        }

        return themeDTOList;
    }

    public SubscriptionDTO subscribeToATheme(int themeId) {
        User currentUser = getCurrentUserFromSecurityContext();
        Theme themeToSubscribe = themeRepository.findById(themeId).orElseThrow(() -> new RuntimeException("Theme introuvable"));

        if(subscriptionRepository.existsByUserAndTheme(currentUser, themeToSubscribe)){
            throw new RuntimeException();
        }
        Subscription subscription = new Subscription();

        subscription.setUser(currentUser);
        subscription.setTheme(themeToSubscribe);

        Subscription suscribedTheme = subscriptionRepository.save(subscription);

        return toSubscriptionDTO(suscribedTheme);
    }

    public SubscriptionDTO unSubscribeToATheme(int themeId){
        User currentUser = getCurrentUserFromSecurityContext();
        Subscription subscription = subscriptionRepository
                .findByUserAndThemeId(currentUser, themeId)
                .orElseThrow(() -> new RuntimeException("Subscription au thème introuvable"));
        SubscriptionDTO dto = toSubscriptionDTO(subscription);
        subscriptionRepository.delete(subscription);

        return dto;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }

    public ThemeDTO toThemeDTO(Theme theme) {
        ThemeDTO themeDTO = new ThemeDTO();

        themeDTO.setId(theme.getId());
        themeDTO.setName(theme.getName());
        themeDTO.setDescription(theme.getDescription());
        themeDTO.setCreatedAt(theme.getCreatedAt());
        themeDTO.setUpdatedAt(theme.getUpdatedAt());

        return themeDTO;
    }

    public SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();

        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setUser(subscription.getUser().getName());
        subscriptionDTO.setTheme(subscription.getTheme().getName());

        return subscriptionDTO;
    }
}
