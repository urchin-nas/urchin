package urchin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import urchin.security.LinuxAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String URCHIN_ADMIN = "URCHIN_ADMIN";

    private final LinuxAuthenticationProvider linuxAuthenticationProvider;

    @Autowired
    public SecurityConfiguration(LinuxAuthenticationProvider linuxAuthenticationProvider) {
        this.linuxAuthenticationProvider = linuxAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/authenticate/add-first-admin").permitAll()
                .antMatchers("/api/**").hasRole(URCHIN_ADMIN)
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.ACCEPTED))
                .and()
            .csrf()
                .disable(); //TODO enable
        //@formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(linuxAuthenticationProvider);
    }

}
