package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private SecurityUtils() {}

    // Get the currently logged-in username
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity user) { // your custom entity
            return user.getUsername();
        }

        return principal.toString();
    }

    // Get the currently logged-in UserEntity
    public static UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity user) { // make sure UserEntity is stored in Authentication
            return user;
        }

        return null;
    }

    // Get the currently logged-in user's ID directly
    public static Long getCurrentUserId() {
        UserEntity user = getCurrentUser();
        return (user != null) ? user.getId() : null;
    }
}
