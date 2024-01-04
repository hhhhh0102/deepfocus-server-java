package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.CreateSocialCommand;
import io.poten13.deepfocus.domain.user.entity.Social;
import io.poten13.deepfocus.domain.user.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialCommander {
    private final SocialRepository socialRepository;

    @Transactional
    public Social save(CreateSocialCommand command) {
        Social social = Social.from(command);
        socialRepository.save(social);
        return social;
    }
}
