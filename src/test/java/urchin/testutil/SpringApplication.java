package urchin.testutil;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import urchin.Application;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties;

@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest({"server.port=0"})
public abstract class SpringApplication {

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
        url = baseUrl + getPath();
        template = new TestRestTemplate();
    }

    protected abstract String getPath();
}
