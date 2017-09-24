package urchin.testutil;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class TestApplication {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
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
