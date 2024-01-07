package io.poten13.deepfocus.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DeleteUserCommand {
    private final String userId;

    public static DeleteUserCommand from(UserModel userModel) {
        return DeleteUserCommand.builder()
            .userId(userModel.getUserId())
            .build();
    }
}
