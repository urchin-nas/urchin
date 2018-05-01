package urchin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UrchinSecurityContext {

    public static Authentication getLoggedInUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return getLoggedInUserAuthentication().isAuthenticated();
    }
}
