package urchin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static urchin.model.user.Role.URCHIN_ADMIN;

public class UrchinSecurityContext {

    private UrchinSecurityContext() {
    }

    public static Authentication getLoggedInUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthorized() {
        List<String> roles = getLoggedInUserAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return roles.contains(URCHIN_ADMIN.getRole());
    }
}
