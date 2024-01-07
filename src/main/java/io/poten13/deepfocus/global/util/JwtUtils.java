package io.poten13.deepfocus.global.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

    public static long getExpirationSecond(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().getTime() / 1000;
        } catch (JWTDecodeException exception){
            // todo : custom exception 변경
            throw new RuntimeException("Invalid JWT Token");
        }
    }
}
