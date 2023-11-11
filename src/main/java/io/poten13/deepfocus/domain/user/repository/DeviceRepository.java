package io.poten13.deepfocus.domain.user.repository;

import io.poten13.deepfocus.domain.user.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
