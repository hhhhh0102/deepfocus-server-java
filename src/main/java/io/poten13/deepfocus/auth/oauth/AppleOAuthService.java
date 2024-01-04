package io.poten13.deepfocus.auth.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.poten13.deepfocus.auth.oauth.client.AppleOAuthClient;
import io.poten13.deepfocus.auth.oauth.dto.AppleOAuthAttributes;
import io.poten13.deepfocus.auth.oauth.dto.ApplePublicKey;
import io.poten13.deepfocus.auth.oauth.dto.ApplePublicKeyResponse;
import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleOAuthService implements OAuthService {

    private final AppleOAuthClient client;

    @Override
    public OAuthAttributes getOAuthAttributes(String credentials) {
        DecodedJWT jwt = verifyIdentityToken(credentials);
        return extractAppleOAuthAttributes(jwt);
    }

    @Override
    public boolean supports(ProviderType providerType) {
        return providerType == ProviderType.APPLE;
    }

    // todo : apple 소셜 인증 검증 후에 Runtime Exception 제거

    private DecodedJWT verifyIdentityToken(String identityToken) {
        ApplePublicKeyResponse response = client.getApplePublicKeys();
        String tokenHeader = identityToken.split("\\.")[0];
        Map<String, String> header = null;
        try {
            header = new ObjectMapper()
                    .readValue(new String(Base64.getUrlDecoder().decode(tokenHeader), StandardCharsets.UTF_8), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> finalHeader = header;
        ApplePublicKey applePublicKey = response.getKeys().stream()
                .filter(key -> key.getKid().equals(finalHeader.get("kid")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid token kid"));

        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(applePublicKey.getModulus()));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(applePublicKey.getExponent()));

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        RSAPublicKey publicKey = null;
        try {
            publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(identityToken);
    }


    private AppleOAuthAttributes extractAppleOAuthAttributes(DecodedJWT jwt) {
        AppleOAuthAttributes attributes = new AppleOAuthAttributes();
        attributes.setIss(jwt.getIssuer());
        attributes.setSub(jwt.getSubject());
        attributes.setAud(jwt.getAudience().get(0));
        attributes.setIat(jwt.getIssuedAt().toString());
        attributes.setExp(jwt.getExpiresAt().toString());
        // 기타 필요한 클레임 설정
        return attributes;
    }
}
