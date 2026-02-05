package com.group05.TC_LLM_Generator.infrastructure.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String signerKey;

    @Value("${jwt.expiration-accessToken}")
    private long accessTokenDuration; // in milliseconds

    @Value("${jwt.expiration-refreshToken}")
    private long refreshTokenDuration; // in milliseconds

    public String generateAccessToken(String email) {
        return generateToken(email, accessTokenDuration);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, refreshTokenDuration);
    }

    private String generateToken(String email, long duration) {
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(signerKey.getBytes());

            // Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + duration))
                    .build();

            // Create JWS object with signature algorithm and payload
            JWSObject jwsObject = new JWSObject(
                    new JWSHeader(JWSAlgorithm.HS512),
                    new Payload(claimsSet.toJSONObject()));

            // Apply the HMAC
            jwsObject.sign(signer);

            // Serialize to compact form
            return jwsObject.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }
}
