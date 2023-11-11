package io.poten13.deepfocus.domain.user.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.user.dto.CreateDeviceCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "tbl_device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    private String deviceToken;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Device from(CreateDeviceCommand command) {
        Device device = new Device();
        device.deviceToken = command.getDeviceToken();
        device.user = command.getUser();
        return device;
    }
}
