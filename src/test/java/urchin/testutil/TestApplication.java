package urchin.testutil;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

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
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
}
