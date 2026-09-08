package com.skillsphere.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getPrincipal() instanceof Jwt jwt) {
                return jwt.getClaimAsString("preferred_username");
            }
            return auth.getName();
        }
        return "anonymousUser";
    }

    public static boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        String expectedRole = "ROLE_" + role.toUpperCase();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase(expectedRole) || a.getAuthority().equalsIgnoreCase(role));
    }
}
