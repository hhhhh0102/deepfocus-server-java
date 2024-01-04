package io.poten13.deepfocus.global.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.poten13.deepfocus.auth.exception.AccessTokenExpiredException;
import io.poten13.deepfocus.auth.exception.AccessTokenInvalidException;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.service.UserService;
import io.poten13.deepfocus.domain.user.support.exception.UserNotFoundException;
import io.poten13.deepfocus.global.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JWTVerifier verifier;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(Constants.USER_TOKEN_HEADER_KEY);
        if (StringUtils.isNotBlank(accessToken)) {

            try {
                DecodedJWT decodedToken = getDecodedToken(accessToken);
                String userToken = decodedToken.getSubject();
                UserModel user = userService.findByUserToken(userToken)
                        .orElseThrow(UserNotFoundException::new);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserToken(), "", user.getRoleType().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (UserNotFoundException ex) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 토큰이 누락되었습니다.");
        }
    }

    private DecodedJWT getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token)).orElseThrow(RuntimeException::new);
        } catch (JWTDecodeException ex) {
            throw new AccessTokenInvalidException();
        } catch (JWTVerificationException ex) {
            throw new AccessTokenExpiredException();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (StringUtils.equals("/api/v1/users/deviceLogin", request.getServletPath())
                && StringUtils.equals(HttpMethod.POST.name(), request.getMethod())) ||
                StringUtils.startsWith(request.getServletPath(), "/swagger-ui") ||
                StringUtils.startsWith(request.getServletPath(), "/v3/api-docs/swagger-config") ||
                StringUtils.equals(request.getServletPath(), "/v3/api-docs") ||
                StringUtils.equals("/api/v1/auth/login", request.getServletPath()) ||
                StringUtils.equals("/api/v1/auth/token/reissue", request.getServletPath()) ||
                StringUtils.equals(request.getServletPath(), "/actuator");
    }
}
