package io.poten13.deepfocus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtSubject {
    private final String userToken;

    @Override
    public String toString() {
        return this.userToken;
    }

    public static JwtSubject parseFrom(String decodedToken) {
        return new JwtSubject(decodedToken);
    }
}
