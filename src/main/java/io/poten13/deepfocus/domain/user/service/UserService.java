package io.poten13.deepfocus.domain.user.service;

import static io.poten13.deepfocus.global.constants.Constants.MAX_NICKNAME_LENGTH;
import static io.poten13.deepfocus.global.constants.Constants.NICKNAME_RESPONSE_FORMAT;
import static io.poten13.deepfocus.global.constants.Constants.REQUIRED_NICKNAME_COUNT;

import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.domain.user.client.NicknameGeneratorClient;
import io.poten13.deepfocus.domain.user.dto.CreateDeviceCommand;
import io.poten13.deepfocus.domain.user.dto.CreateSocialCommand;
import io.poten13.deepfocus.domain.user.dto.CreateUserCommand;
import io.poten13.deepfocus.domain.user.dto.DeleteSocialCommand;
import io.poten13.deepfocus.domain.user.dto.DeleteUserCommand;
import io.poten13.deepfocus.domain.user.dto.SocialModel;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.domain.user.support.exception.SocialNotFoundException;
import io.poten13.deepfocus.domain.user.support.exception.UserNotFoundException;
import io.poten13.deepfocus.global.constants.Severity;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserCommander userCommander;
    private final DeviceCommander deviceCommander;
    private final SocialCommander socialCommander;

    private final UserReader userReader;
    private final SocialReader socialReader;

    private final NicknameGeneratorClient nicknameGeneratorClient;

    public Optional<UserModel> findByDeviceToken(String deviceToken) {
        return userReader.readByDeviceToken(deviceToken);
    }

    public Optional<UserModel> findBySocialProviderUserId(String providerId) {
        return userReader.readBySocialProviderUserId(providerId);
    }

    public Optional<UserModel> findByUserToken(String userToken) {
        return userReader.readByUserToken(userToken)
                .map(UserModel::from);
    }

    public UserModel getByUserToken(String userToken) {
        return findByUserToken(userToken)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserModel save(String deviceToken) {
        User user = createUser();

        CreateDeviceCommand deviceCommand = CreateDeviceCommand.builder()
                .deviceToken(deviceToken)
                .user(user)
                .build();

        deviceCommander.save(deviceCommand);
        return UserModel.from(user);
    }

    @Transactional
    public UserModel save(OAuthAttributes oAuthAttributes) {
        User user = createUser();

        CreateSocialCommand command = CreateSocialCommand.builder()
                .providerType(oAuthAttributes.getProvider())
                .providerUserId(oAuthAttributes.getProviderGivenId())
                .user(user)
                .build();
        socialCommander.save(command);
        return UserModel.from(user);
    }

    private User createUser() {
        String nickname = nicknameGeneratorClient.generateNickname(
                NICKNAME_RESPONSE_FORMAT,
                REQUIRED_NICKNAME_COUNT,
                MAX_NICKNAME_LENGTH
        );

        CreateUserCommand command = CreateUserCommand.builder()
                .nickname(nickname)
                .userToken(UUID.randomUUID().toString())
                .build();
        return userCommander.save(command);
    }

    @Transactional
    public void updateUserSeverity(String userToken, Severity severity) {
        userCommander.updateSeverity(userToken, severity);
    }

    @Transactional
    public void deleteUser(String userToken) {
        UserModel user = findByUserToken(userToken)
            .orElseThrow(UserNotFoundException::new);
        SocialModel social = socialReader.readByUserId(user.getUserId())
            .orElseThrow(SocialNotFoundException::new);

        socialCommander.delete(DeleteSocialCommand.from(social));
        userCommander.delete(DeleteUserCommand.from(user));

    }
}
