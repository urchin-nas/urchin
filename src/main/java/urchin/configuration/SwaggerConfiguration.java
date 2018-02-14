package urchin.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    private static final String SCAN_PACKAGE = "urchin.controller";

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(.*)\\/api\\/(.*)"))
                .build()
                .alternateTypeRules(getAlternateTypeRules());
    }

    private AlternateTypeRule[] getAlternateTypeRules() {
        log.info("discovering rest controllers in {} package", SCAN_PACKAGE);
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        List<String> classNames = provider.findCandidateComponents(SCAN_PACKAGE).stream()
                .map(BeanDefinition::getBeanClassName)
                .collect(Collectors.toList());

        List<Class> classes = findClasses(classNames);
        List<Class> immutableClasses = findImmutableClasses(classes);
        List<AlternateTypeRule> alternateTypeRules = createAlternateTypeRules(classes, immutableClasses);

        return alternateTypeRules.toArray(new AlternateTypeRule[0]);
    }

    private List<AlternateTypeRule> createAlternateTypeRules(List<Class> classes, List<Class> immutableClasses) {
        List<AlternateTypeRule> alternateTypeRules = new ArrayList<>();
        classes.forEach(c -> immutableClasses.stream()
                .filter(ic -> ic.getName().equals(getImmutableClassName(c)))
                .findFirst()
                .ifPresent(ic -> alternateTypeRules.add(newRule(c, ic)))
        );
        return alternateTypeRules;
    }

    private List<Class> findImmutableClasses(List<Class> classes) {
        return getClasses(classes.stream()
                .map(this::getImmutableClassName)
                .collect(Collectors.toList()));
    }

    private List<Class> findClasses(List<String> classNames) {
        return getClasses(classNames).stream()
                .map(this::getParameterClasses)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Class> getClasses(List<String> classNames) {
        List<Class> classes = new ArrayList<>();
        classNames.forEach(className -> {
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        });
        return classes;
    }

    private List<Class> getParameterClasses(Class clazz) {
        List<Class<?>> parameterTypes = Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getParameterTypes)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return getClasses(parameterTypes.stream()
                .map(Class::getName)
                .collect(Collectors.toList()));
    }

    private String getImmutableClassName(Class c) {
        String className = c.getName();
        int i = className.lastIndexOf('.');
        return className.substring(0, i) + ".Immutable" + className.substring(i + 1, className.length());
    }
}
