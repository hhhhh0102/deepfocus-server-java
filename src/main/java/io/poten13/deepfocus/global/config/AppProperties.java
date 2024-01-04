package io.poten13.deepfocus.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final JwtConfig jwt = new JwtConfig();

    @Getter
    @Setter
    public static class JwtConfig {
        private String tokenIssuer;
        private String secret;
        private int tokenExpiry;
        private int refreshTokenExpiry;
        private int passwordStrength;
    }
}
