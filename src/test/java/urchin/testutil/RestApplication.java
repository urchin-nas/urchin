package urchin.testutil;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestApplication {

    @Value("${local.server.port}")
    protected int port;
    protected String contextPath;
    protected String baseUrl;
    protected String url;
    protected RestTemplate template;

    @Before
    public void setupSpringApplication() throws IOException {
        Properties properties = loadProperties(new ClassPathResource("application.properties"));
        contextPath = properties.getProperty("server.contextPath");
        baseUrl = "http://localhost:" + port + contextPath;
        url = baseUrl + discoverPath();
        template = new TestRestTemplate();
    }

    protected String discoverPath() {
        try {
            return Class.forName(this.getClass().getName().replace("IT", "")).getAnnotation(RequestMapping.class).value()[0];
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to discover path", e);
        }
    }
}
