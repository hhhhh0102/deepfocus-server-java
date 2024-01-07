package io.poten13.deepfocus.domain.user.repository;

import io.poten13.deepfocus.domain.user.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepository extends JpaRepository<Social, String>, SocialCustomRepository {
}
