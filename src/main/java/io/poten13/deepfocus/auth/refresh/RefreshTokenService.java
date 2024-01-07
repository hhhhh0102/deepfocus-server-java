package io.poten13.deepfocus.auth.refresh;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.poten13.deepfocus.auth.dto.AuthToken;
import io.poten13.deepfocus.auth.dto.JwtSubject;
import io.poten13.deepfocus.auth.exception.RefreshTokenInvalidException;
import io.poten13.deepfocus.auth.refresh.dto.RefreshTokenCreateCommand;
import io.poten13.deepfocus.auth.refresh.dto.RefreshTokenModel;
import io.poten13.deepfocus.global.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final AppProperties appProperties;
    private final Algorithm algorithm;

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_AUTO_REISSUE_THRESHOLD = 3 * 24 * 60 * 60 * 1000;

    @Transactional
    public AuthToken createAuthTokenForLogin(String userToken) {
        JwtSubject jwtSubject = new JwtSubject(userToken);
        String refreshToken = getRefreshToken(userToken);
        String accessToken = getToken(jwtSubject);
        return AuthToken.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public String getRefreshToken(String userToken) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMillis(appProperties.getJwt().getRefreshTokenExpiry()));
        String token = JWT.create().withIssuer(appProperties.getJwt().getTokenIssuer())
                .withIssuedAt(Date.from(now)).withExpiresAt(Date.from(expiry))
                .withSubject(appProperties.getJwt().getSecret()).sign(algorithm);

        RefreshTokenModel refreshToken = createRefreshToken(userToken, token);
        return refreshToken.getToken();
    }

    @Transactional
    public AuthToken reissue(String requestToken) {

        RefreshTokenModel refreshToken = RefreshTokenModel.from(refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(RefreshTokenInvalidException::new));

        JwtSubject jwtSubject = new JwtSubject(refreshToken.getUserToken());
        String accessToken = getToken(jwtSubject);

        final long remainingTime = refreshToken.getExpiryDate().toEpochMilli() - System.currentTimeMillis();

        if (remainingTime <= REFRESH_TOKEN_AUTO_REISSUE_THRESHOLD) {
            refreshToken = createRefreshToken(jwtSubject.getUserToken(), refreshToken.getToken());
        }

        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Transactional
    public RefreshTokenModel createRefreshToken(String userToken, String refreshToken) {
        refreshTokenRepository.deleteByUserToken(userToken);
        RefreshTokenCreateCommand command = RefreshTokenCreateCommand.builder()
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(appProperties.getJwt().getRefreshTokenExpiry()))
                .userToken(userToken)
                .build();
        RefreshToken newRefreshToken = RefreshToken.create(command);
        refreshTokenRepository.save(newRefreshToken);
        return RefreshTokenModel.from(newRefreshToken);
    }

    public String getToken(JwtSubject subject) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofMillis(appProperties.getJwt().getTokenExpiry()));
        return JWT.create().withIssuer(appProperties.getJwt().getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(subject.toString()).sign(algorithm);
    }
}
