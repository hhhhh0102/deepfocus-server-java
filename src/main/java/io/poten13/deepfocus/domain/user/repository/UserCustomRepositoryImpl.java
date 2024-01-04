package io.poten13.deepfocus.domain.user.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.poten13.deepfocus.domain.user.dto.QUserModel;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.entity.QDevice;
import io.poten13.deepfocus.domain.user.entity.QSocial;
import io.poten13.deepfocus.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QUser user = QUser.user;
    private static final QDevice device = QDevice.device;
    private static final QSocial social = QSocial.social;

    @Override
    public Optional<UserModel> findUserModelByDeviceToken(String deviceToken) {
        return Optional.ofNullable(
                queryFactory.select(getUserModelProjection())
                        .from(device)
                        .join(device.user, user)
                        .where(device.deviceToken.eq(deviceToken))
                        .fetchOne()
        );
    }

    @Override
    public Optional<UserModel> findUserModelBySocialProviderUserId(String providerId) {
        return Optional.ofNullable(
                queryFactory.select(getUserModelProjection())
                        .from(social)
                        .join(social.user, user)
                        .where(social.providerUserId.eq(providerId))
                        .fetchOne()
        );
    }

    private Expression<UserModel> getUserModelProjection() {
        return new QUserModel(user.userId, user.userToken, user.nickname, user.roleType, user.severity);
    }
}
