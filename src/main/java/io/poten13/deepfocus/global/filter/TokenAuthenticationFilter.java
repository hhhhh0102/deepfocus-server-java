package io.poten13.deepfocus.global.filter;

import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.service.UserService;
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

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userToken = request.getHeader(Constants.USER_TOKEN_HEADER_KEY);
        if (StringUtils.isNotBlank(userToken)) {
            UserModel user = userService.findByUserToken(userToken)
                    .orElseThrow(RuntimeException::new);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserToken(), "", user.getRoleType().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (StringUtils.equals("/api/v1/users/login", request.getServletPath())
                && StringUtils.equals(HttpMethod.POST.name(), request.getMethod())) ||
                StringUtils.startsWith(request.getServletPath(), "/swagger-ui") ||
                StringUtils.startsWith(request.getServletPath(), "/v3/api-docs/swagger-config") ||
                StringUtils.equals(request.getServletPath(), "/v3/api-docs") ||
                StringUtils.equals(request.getServletPath(), "/actuator");
    }
}
