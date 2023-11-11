package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.CreateUserCommand;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.domain.user.repository.UserRepository;
import io.poten13.deepfocus.domain.user.support.exception.UserNotFoundException;
import io.poten13.deepfocus.global.constants.Severity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommander {
    private final UserRepository userRepository;

    @Transactional
    public User save(CreateUserCommand command) {
        User user = User.from(command);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void updateSeverity(String userToken, Severity severity) {
        User user = userRepository.findByUserToken(userToken)
                .orElseThrow(UserNotFoundException::new);
        user.updateSeverity(severity);
    }
}
