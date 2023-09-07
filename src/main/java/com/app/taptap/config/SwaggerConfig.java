package com.app.taptap.config;


import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {


    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        return new SwaggerUiConfigParameters(swaggerUiConfigProperties);
    }

    @Bean
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("后端 API文档")
                        .description("taptap API 演示")
                        .version("v1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("Authorization",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi InitApi() {
        return GroupedOpenApi.builder()
                .group("Init")
                .pathsToMatch("/Init/**")
                .build();
    }

    @Bean
    public GroupedOpenApi RegisterAndLoginApi() {
        return GroupedOpenApi.builder()
                .group("RegisterAndLogin")
                .pathsToMatch("/RAL/**")
                .build();
    }

    @Bean
    public GroupedOpenApi PersonInfoApi() {
        return GroupedOpenApi.builder()
                .group("PersonInfo")
                .pathsToMatch("/PersonInfo/**")
                .build();
    }

    @Bean
    public GroupedOpenApi MainPageApi() {
        return GroupedOpenApi.builder()
                .group("MainPage")
                .pathsToMatch("/Main/**")
                .build();
    }

    @Bean
    public GroupedOpenApi CommunityApi() {
        return GroupedOpenApi.builder()
                .group("Community")
                .pathsToMatch("/Community/**")
                .build();
    }

    @Bean
    public GroupedOpenApi SearchApi() {
        return GroupedOpenApi.builder()
                .group("Search")
                .pathsToMatch("/Search/**")
                .build();
    }

    @Bean
    public GroupedOpenApi SidebarApi() {
        return GroupedOpenApi.builder()
                .group("Sidebar")
                .pathsToMatch("/Sidebar/**")
                .build();
    }

    @Bean
    public GroupedOpenApi RankApi() {
        return GroupedOpenApi.builder()
                .group("Rank")
                .pathsToMatch("/Rank/**")
                .build();
    }

    @Bean
    public GroupedOpenApi FindApi() {
        return GroupedOpenApi.builder()
                .group("Find")
                .pathsToMatch("/Find/**")
                .build();
    }

    @Bean
    public GroupedOpenApi WeChatApi() {
        return GroupedOpenApi.builder()
                .group("WeChat")
                .pathsToMatch("/WeChat/**")
                .build();
    }
}
