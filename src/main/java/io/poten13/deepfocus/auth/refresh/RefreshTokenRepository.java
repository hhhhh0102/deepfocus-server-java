package io.poten13.deepfocus.auth.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserToken(String userToken);

    void deleteByToken(String token);

    void deleteByUserToken(String userToken);
}
