package urchin.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import urchin.model.user.Password;
import urchin.model.user.Username;
import urchin.service.AdminService;

import java.util.Collections;

@Component
public class LinuxAuthenticationProvider implements AuthenticationProvider {

    private static final String BAD_CREDENTIALS = "Invalid username and/or password";

    private final AdminService adminService;

    @Autowired
    public LinuxAuthenticationProvider(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Username username = Username.of(authentication.getName());
        Password password = Password.of(authentication.getCredentials().toString());

        if (adminService.authenticateAdmin(username, password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }

        throw new BadCredentialsException(BAD_CREDENTIALS);
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
