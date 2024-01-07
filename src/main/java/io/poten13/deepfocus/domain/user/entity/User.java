package io.poten13.deepfocus.domain.user.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.user.dto.CreateUserCommand;
import io.poten13.deepfocus.global.constants.RoleType;
import io.poten13.deepfocus.global.constants.Severity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    private String userId;

    private String userToken;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;

    public static User from(CreateUserCommand command) {
        User user = new User();
        user.userId = UUID.randomUUID().toString();
        user.userToken = command.getUserToken();
        user.nickname = command.getNickname();
        user.roleType = RoleType.ROLE_USER;
        return user;
    }

    public void updateSeverity(Severity severity) {
        this.severity = severity;
    }

    public void deleteUser() {
        LocalDateTime now = LocalDateTime.now();
        this.isDeleted = true;
        this.deletedAt = now;
        this.userToken = null;
        this.nickname = null;
        this.roleType = null;
        this.severity = null;
    }
}
