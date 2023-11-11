package io.poten13.deepfocus.domain.user.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.user.dto.CreateUserCommand;
import io.poten13.deepfocus.global.constants.RoleType;
import io.poten13.deepfocus.global.constants.Severity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userToken;

    private String nickname;

    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    public static User from(CreateUserCommand command) {
        User user = new User();
        user.userToken = command.getUserToken();
        user.nickname = command.getNickname();
        user.roleType = RoleType.ROLE_USER;
        return user;
    }

    public void updateSeverity(Severity severity) {
        this.severity = severity;
    }
}
