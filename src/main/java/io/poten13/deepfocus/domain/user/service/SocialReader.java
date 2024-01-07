package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.SocialModel;
import io.poten13.deepfocus.domain.user.repository.SocialRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialReader {
    private final SocialRepository socialRepository;

    @Transactional
    public Optional<SocialModel> readByUserId(String userId) {
        return socialRepository.findByUserId(userId);
    }
}
