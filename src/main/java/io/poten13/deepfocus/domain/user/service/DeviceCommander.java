package io.poten13.deepfocus.domain.user.service;

import io.poten13.deepfocus.domain.user.dto.CreateDeviceCommand;
import io.poten13.deepfocus.domain.user.entity.Device;
import io.poten13.deepfocus.domain.user.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceCommander {

    private final DeviceRepository deviceRepository;

    public Device save(CreateDeviceCommand command) {
        Device device = Device.from(command);
        deviceRepository.save(device);
        return device;
    }
}
