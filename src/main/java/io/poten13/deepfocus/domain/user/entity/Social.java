package io.poten13.deepfocus.domain.user.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.user.dto.CreateSocialCommand;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tbl_social")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Social extends BaseTimeEntity {

    @Id
    private String socialId;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    private String providerUserId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Social from(CreateSocialCommand command) {
        Social social = new Social();
        social.socialId = UUID.randomUUID().toString();
        social.provider = command.getProviderType();
        social.providerUserId = command.getProviderUserId();
        social.user = command.getUser();
        return social;
    }
}
