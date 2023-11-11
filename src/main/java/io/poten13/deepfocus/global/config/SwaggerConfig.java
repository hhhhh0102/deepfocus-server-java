package io.poten13.deepfocus.global.config;

import io.poten13.deepfocus.global.constants.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private static final String API_NAME = "DEEP FOCUS API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "DEEP FOCUS API";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_NAME)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION))
                .addSecurityItem(new SecurityRequirement().addList(Constants.API_KEY))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(Constants.API_KEY,
                                new SecurityScheme()
                                        .name(Constants.API_KEY)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(Constants.USER_TOKEN_HEADER_KEY)));
    }
}
