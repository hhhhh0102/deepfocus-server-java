package io.poten13.deepfocus.global.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class JwtAlgorithmConfig {

    private final AppProperties appProperties;

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(appProperties.getJwt().getSecret());
    }

    @Bean
    public JWTVerifier verifier(Algorithm algorithm) {
        return JWT
                .require(algorithm)
                .withIssuer(appProperties.getJwt().getTokenIssuer())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(appProperties.getJwt().getPasswordStrength());
    }
}
