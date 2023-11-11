package io.poten13.deepfocus.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.global.constants.RoleType;
import io.poten13.deepfocus.global.constants.Severity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserModel {
    private final Long userId;
    private final String userToken;
    private final String nickname;
    private final RoleType roleType;
    private final Severity severity;

    @QueryProjection
    public UserModel(Long userId, String userToken, String nickname, RoleType roleType, Severity severity) {
        this.userId = userId;
        this.userToken = userToken;
        this.nickname = nickname;
        this.roleType = roleType;
        this.severity = severity;
    }

    public static UserModel from(User user) {
        return UserModel.builder()
                .userId(user.getUserId())
                .userToken(user.getUserToken())
                .nickname(user.getNickname())
                .roleType(user.getRoleType())
                .severity(user.getSeverity())
                .build();
    }
}
