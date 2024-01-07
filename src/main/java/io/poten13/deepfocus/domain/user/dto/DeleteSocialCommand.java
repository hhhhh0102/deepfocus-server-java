package io.poten13.deepfocus.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DeleteSocialCommand {
    private final String socialId;

    public static DeleteSocialCommand from(SocialModel socialModel) {
        return DeleteSocialCommand.builder()
            .socialId(socialModel.getSocialId())
            .build();
    }
}
