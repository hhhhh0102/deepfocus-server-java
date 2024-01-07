package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.CreateSocialCommand;
import io.poten13.deepfocus.domain.user.dto.DeleteSocialCommand;
import io.poten13.deepfocus.domain.user.entity.Social;
import io.poten13.deepfocus.domain.user.repository.SocialRepository;
import io.poten13.deepfocus.domain.user.support.exception.SocialNotFoundException;
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

    @Transactional
    public void delete(DeleteSocialCommand command) {
        Social social = socialRepository.findById(command.getSocialId())
            .orElseThrow(SocialNotFoundException::new);

        socialRepository.delete(social);
    }
}
