package io.poten13.deepfocus.domain.user.repository;

import io.poten13.deepfocus.domain.user.dto.SocialModel;
import java.util.Optional;

public interface SocialCustomRepository {
    Optional<SocialModel> findByUserId(String userId);
}
