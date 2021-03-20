package com.halmeida.clientapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.OAS_30)
                .securitySchemes(Collections.singletonList(apiKey()))
                .apiInfo(metaData())
                .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Client API")
                .description("Client CRUD api using standard REST API")
                .version("1.0.0")
                .contact(new Contact("Hallyson Almeida", null, "hallysonh@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(o -> PathSelectors.regex("/.*").test(o.requestMappingPattern()))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("api_key", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("api_key", "ApiKey", "header");
    }
}

