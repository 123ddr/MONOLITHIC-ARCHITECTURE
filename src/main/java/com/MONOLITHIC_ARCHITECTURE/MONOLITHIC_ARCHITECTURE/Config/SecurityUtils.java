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

        if (authentication == null) {
            throw new RuntimeException("No authentication found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return principal.toString();
    }

    // Get the currently logged-in UserEntity
    public static UserEntity getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {

            UserEntity user = new UserEntity();

            user.setEmail(userDetails.getUsername());
            user.setRole(
                    userDetails.getAuthorities()
                            .stream()
                            .findFirst()
                            .map(a -> a.getAuthority().replace("ROLE_", ""))
                            .orElse(null)
            );

            return user; // ONLY partial object
        }

        return null;
    }

    // Get the currently logged-in user's ID directly
    public static Long getCurrentUserId() {
        UserEntity user = getCurrentUser();
        return (user != null) ? user.getId() : null;
    }
}
