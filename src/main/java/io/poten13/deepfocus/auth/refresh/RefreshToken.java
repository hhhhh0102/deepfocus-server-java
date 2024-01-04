package io.poten13.deepfocus.auth.refresh;

import io.poten13.deepfocus.auth.refresh.dto.RefreshTokenCreateCommand;
import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Entity
@Table(name = "tbl_refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column
    private Instant expiryDate;

    @Column
    private String userToken;

    public boolean verifyExpiration(Instant time) {
        return this.expiryDate.compareTo(time) > 0;
    }

    public static RefreshToken create(RefreshTokenCreateCommand command) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.token = command.getToken();
        refreshToken.expiryDate = command.getExpiryDate();
        refreshToken.userToken = command.getUserToken();
        return refreshToken;
    }
}
