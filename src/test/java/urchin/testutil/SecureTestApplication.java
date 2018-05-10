package urchin.testutil;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import urchin.cli.UserCli;
import urchin.exception.AdminNotFoundException;
import urchin.model.user.Password;
import urchin.model.user.Username;
import urchin.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public abstract class SecureTestApplication extends TestApplication {

    @Autowired
    protected UserCli userCli;
    @Autowired
    protected AdminRepository adminRepository;

    protected static Username username;
    protected static Password password;
    private static List<String> cookies = new ArrayList<>();

    @Before
    public void setUpTestApplication() {
        if (adminIsMissing()) {
            setupAdmin();
            login(username, password);
            attachCookies();
        }
    }

    private void setupAdmin() {
        Username user = Username.of(USERNAME_PREFIX + System.currentTimeMillis());
        Password pwd = Password.of(randomAlphanumeric(10));
        userCli.addUser(user);
        userCli.setUserPassword(user, pwd);
        adminRepository.saveAdmin(user);

        username = user;
        password = pwd;
        cookies = new ArrayList<>();
    }

    protected void login(Username username, Password password) {
        assertThat(username).isNotNull();
        assertThat(password).isNotNull();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", username.getValue());
        body.add("password", password.getValue());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/login", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        Optional.ofNullable(response.getHeaders().get("Set-Cookie"))
                .ifPresent(strings -> cookies.addAll(strings));
    }

    protected void logout() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/logout", null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        cookies = new ArrayList<>();
    }

    private void attachCookies() {
        testRestTemplate.getRestTemplate().setInterceptors(
                singletonList((request, body, execution) -> {
                    cookies.forEach(s -> request.getHeaders().add(HttpHeaders.COOKIE, s));
                    return execution.execute(request, body);
                }));
    }

    private boolean adminIsMissing() {
        if (isNull(username)) {
            return true;
        }

        if (!userCli.checkIfUsernameExist(username)) {
            return true;
        }

        try {
            adminRepository.getAdminByUsername(username);
        } catch (AdminNotFoundException e) {
            return true;
        }
        return false;
    }
}
