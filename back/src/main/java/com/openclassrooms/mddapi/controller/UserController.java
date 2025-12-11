package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserUpdateRequestDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        try {
            User user = userService.getCurrentUserFromSecurityContext();

            return ResponseEntity.ok(userService.toDTO(user));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/me/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDTO dto) {
        try {
            UserDTO updatedUserDTO = userService.updateUser(dto);
            return ResponseEntity.ok(updatedUserDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me/subscription")
    public ResponseEntity<?> getUserSubscription() {
        try {
            return ResponseEntity.ok(userService.getAllUserSubscription());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("me/subscription/{themeId}")
    public ResponseEntity<?> addUserSubscription(@PathVariable int themeId){
        try {
            userService.subscribeToATheme(themeId);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("me/subscription/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable int subscriptionId){
        try {
            SubscriptionDTO DeletedSubscriptionDTO = userService.unSubscribeToATheme(subscriptionId);
            return ResponseEntity.ok(DeletedSubscriptionDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
