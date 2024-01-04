package io.poten13.deepfocus.global.config;

import com.auth0.jwt.JWTVerifier;
import io.poten13.deepfocus.domain.user.service.UserService;
import io.poten13.deepfocus.global.constants.Constants;
import io.poten13.deepfocus.global.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTVerifier verifier;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> {
                    request.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
                    request.antMatchers("/actuator/**").permitAll()
                            .antMatchers(Constants.SWAGGER_URL_LIST).permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/auth/token/reissue").permitAll();
                    request.anyRequest().authenticated();
                })
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new TokenAuthenticationFilter(verifier, userService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
