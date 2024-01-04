package io.poten13.deepfocus.auth;

import io.poten13.deepfocus.auth.dto.AuthToken;
import io.poten13.deepfocus.auth.oauth.OAuthServiceFactory;
import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.auth.dto.UserAuthInfo;
import io.poten13.deepfocus.auth.refresh.RefreshTokenService;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.service.UserService;
import io.poten13.deepfocus.global.constants.LoginType;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;
    private final OAuthServiceFactory oAuthServiceFactory;
    private final RefreshTokenService refreshTokenService;

    public UserAuthInfo getUserAuthInfo(LoginType loginType, String credentials) {
        return loginType == LoginType.DEVICE ?
                processDeviceLogin(credentials) :
                processSocialLogin(ProviderType.valueOf(loginType.name()), credentials);
    }

    public AuthToken reissueToken(String refreshToken) {
        return refreshTokenService.reissue(refreshToken);
    }

    private UserAuthInfo processSocialLogin(ProviderType providerType, String credentials) {
        OAuthAttributes oAuthAttributes = oAuthServiceFactory
                .getService(providerType)
                .getOAuthAttributes(credentials);

        boolean isNew = false;
        UserModel user;
        Optional<UserModel> optionalUser = userService.findBySocialProviderUserId(oAuthAttributes.getProviderGivenId());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            isNew = true;
            user = userService.save(oAuthAttributes);
        }
        return createUserAuthInfo(user, isNew);
    }

    private UserAuthInfo processDeviceLogin(String deviceToken) {
        boolean isNew = false;
        UserModel user;
        Optional<UserModel> optionalUser = userService.findByDeviceToken(deviceToken);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            isNew = true;
            user = userService.save(deviceToken);
        }

        return createUserAuthInfo(user, isNew);
    }

    private UserAuthInfo createUserAuthInfo(UserModel user, boolean isNew) {
        AuthToken authToken = refreshTokenService.createAuthTokenForLogin(user.getUserToken());

        return UserAuthInfo.builder()
                .isNew(isNew)
                .accessToken(authToken.getAccessToken())
                .refreshToken(authToken.getRefreshToken())
                .build();
    }
}


