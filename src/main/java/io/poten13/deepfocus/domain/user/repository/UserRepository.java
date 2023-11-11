package io.poten13.deepfocus.domain.user.repository;

import io.poten13.deepfocus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository {
    Optional<User> findByUserToken(String userToken);
}
