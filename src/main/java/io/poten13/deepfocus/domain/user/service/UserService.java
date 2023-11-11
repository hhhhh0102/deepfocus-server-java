package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.client.NicknameGeneratorClient;
import io.poten13.deepfocus.domain.user.dto.CreateDeviceCommand;
import io.poten13.deepfocus.domain.user.dto.CreateUserCommand;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.global.constants.Severity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static io.poten13.deepfocus.global.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserCommander userCommander;
    private final DeviceCommander deviceCommander;
    private final UserReader userReader;
    private final NicknameGeneratorClient nicknameGeneratorClient;
    
    public Optional<UserModel> findByDeviceToken(String deviceToken) {
        return userReader.readByDeviceToken(deviceToken);
    }

    public Optional<UserModel> findByUserToken(String userToken) {
        return userReader.readByUserToken(userToken)
                .map(UserModel::from);
    }

    public UserModel getByUserToken(String userToken) {
        return findByUserToken(userToken)
                .orElseThrow(RuntimeException::new);
    }

    public UserModel save(String deviceToken) {
        String nickname = nicknameGeneratorClient.generateNickname(
                NICKNAME_RESPONSE_FORMAT,
                REQUIRED_NICKNAME_COUNT,
                MAX_NICKNAME_LENGTH
        );

        CreateUserCommand command = CreateUserCommand.builder()
                .nickname(nickname)
                .userToken(UUID.randomUUID().toString())
                .build();
        User user = userCommander.save(command);

        CreateDeviceCommand deviceCommand = CreateDeviceCommand.builder()
                .deviceToken(deviceToken)
                .user(user)
                .build();

        deviceCommander.save(deviceCommand);
        return UserModel.from(user);
    }

    @Transactional
    public void updateUserSeverity(String userToken, Severity severity) {
        userCommander.updateSeverity(userToken, severity);
    }
}
