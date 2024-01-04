package io.poten13.deepfocus.domain.user.repository;

import io.poten13.deepfocus.domain.user.dto.UserModel;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<UserModel> findUserModelByDeviceToken(String deviceToken);

    Optional<UserModel> findUserModelBySocialProviderUserId(String providerId);
}
