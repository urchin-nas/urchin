package urchin.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import urchin.cli.UserCli;
import urchin.model.user.LinuxUser;
import urchin.model.user.Password;

@Component
public class LinuxAuthenticationProvider implements AuthenticationProvider {

    private static final String BAD_CREDENTIALS = "Invalid username and/or password";

    private final UserCli userCli;

    @Autowired
    public LinuxAuthenticationProvider(UserCli userCli) {
        this.userCli = userCli;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Password password = Password.of(authentication.getCredentials().toString());

        LinuxUser linuxUser = userCli.whoAmI();

        if (linuxUser.getUsername().getValue().equals(username) && userCli.verifyPassword(linuxUser, password)) {
            return authentication;
        }

        throw new BadCredentialsException(BAD_CREDENTIALS);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
