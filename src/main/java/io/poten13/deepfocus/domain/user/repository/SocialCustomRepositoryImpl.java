package io.poten13.deepfocus.domain.user.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.poten13.deepfocus.domain.user.dto.QSocialModel;
import io.poten13.deepfocus.domain.user.dto.SocialModel;
import io.poten13.deepfocus.domain.user.entity.QSocial;
import io.poten13.deepfocus.domain.user.entity.QUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SocialCustomRepositoryImpl implements SocialCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QUser user = QUser.user;
    private static final QSocial social = QSocial.social;

    @Override
    public Optional<SocialModel> findByUserId(String userId) {
        return Optional.ofNullable(
            queryFactory.select(getSocialModelProjection())
                .from(social)
                .join(social.user, user)
                .where(user.userId.eq(userId))
                .fetchOne()
        );
    }

    private Expression<SocialModel> getSocialModelProjection() {
        return new QSocialModel(social.socialId, social.provider, social.providerUserId, user.userId);
    }
}
