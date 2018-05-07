package urchin.testutil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import urchin.cli.UserCli;
import urchin.model.user.Password;
import urchin.model.user.Username;
import urchin.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:", webEnvironment = WebEnvironment.RANDOM_PORT)
@Import({
        UnixUserAndGroupCleanup.class
})
public abstract class TestApplication {

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    protected UserCli userCli;
    @Autowired
    protected AdminRepository adminRepository;

    protected static Username username;
    private static Password password;
    private static List<String> cookies = new ArrayList<>();

    @Before
    public void setUpTestApplication() {
        setupAdmin();
        login();
        setSessionIdInterceptor();
    }

    protected String discoverControllerPath() {
        return discoverControllerPath(this.getClass());
    }

    protected String discoverControllerPath(Class clazz) {
        try {
            return "/" + Class.forName(clazz.getName().replace("IT", "")).getAnnotation(RequestMapping.class).value()[0];
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to discover path", e);
        }
    }

    private void setupAdmin() {
        if (Objects.isNull(username)) {
            Username user = Username.of(USERNAME_PREFIX + System.currentTimeMillis());
            password = Password.of(randomAlphanumeric(10));
            userCli.addUser(user);
            userCli.setUserPassword(user, password);
            adminRepository.saveAdmin(user);
            username = user;
        }
    }

    protected void login() {
        if (cookies.isEmpty()) {
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
    }

    protected void logout() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/logout", null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        cookies = new ArrayList<>();
    }

    private void setSessionIdInterceptor() {
        testRestTemplate.getRestTemplate().setInterceptors(
                singletonList((request, body, execution) -> {
                    cookies.forEach(s -> request.getHeaders().add(HttpHeaders.COOKIE, s));
                    return execution.execute(request, body);
                }));
    }
}
