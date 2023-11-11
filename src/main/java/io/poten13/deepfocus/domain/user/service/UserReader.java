package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public Optional<UserModel> readByDeviceToken(String deviceToken) {
        return userRepository.findUserModelByDeviceToken(deviceToken);
    }

    public Optional<User> readByUserToken(String userToken) {
        return userRepository.findByUserToken(userToken);
    }
}
