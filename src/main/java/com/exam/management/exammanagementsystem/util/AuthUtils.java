package com.exam.management.exammanagementsystem.util;

import com.exam.management.exammanagementsystem.dto.UserPrinciple;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;


public final class AuthUtils {
    private AuthUtils() {
    }

    private static UserPrinciple getPrincipal() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                return (UserPrinciple) authentication.getPrincipal();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static UserPrinciple getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserPrinciple user = (UserPrinciple) authentication.getPrincipal();
            return user;
        }
        return null;
    }


    public static Long getLoggedInUserId() {
        UserPrinciple principal = getPrincipal();
        if (principal != null) {
            return principal.getId();
        }
        return null;
    }

    public static String getLoggedInUsername() {
        UserPrinciple principal = getPrincipal();
        if (principal != null) {
            return principal.getUsername();
        }
        return null;
    }

    public static Collection<? extends GrantedAuthority> getLoggedInAuthorities() {
        UserPrinciple principal = getPrincipal();
        if (principal != null) {
            return principal.getAuthorities();
        }
        return null;
    }

    public static Boolean hasAuthorized(String facilityName) {
        boolean hasAuthorized = true;
        UserPrinciple currentUser = getCurrentUser();
        if (currentUser != null) {
            {
                hasAuthorized = false;
            }
        }
        return hasAuthorized;
    }
}
