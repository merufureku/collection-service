package com.merufureku.aromatica.collection_service.utilities;

import com.merufureku.aromatica.collection_service.config.KeyConfig;
import com.merufureku.aromatica.collection_service.exceptions.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static com.merufureku.aromatica.collection_service.constants.CollectionConstants.COLLECTION_SERVICE;
import static com.merufureku.aromatica.collection_service.enums.CustomStatusEnums.INVALID_TOKEN;

@Component
public class TokenUtility {

    private final KeyConfig keyConfig;

    public TokenUtility(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    public Claims parseToken(String token) {

        try{
            var secretKey = Keys.hmacShaKeyFor(Base64.getDecoder()
                    .decode(keyConfig.getJwtAccessSecretKey()));

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (Exception e){
            throw new ServiceException(INVALID_TOKEN);
        }
    }

    public Claims validateInternalToken(String token) {

        var secretKey = Keys.hmacShaKeyFor(Base64.getDecoder()
                .decode(keyConfig.getJwtInternalCollectionSecretKey()));

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException ex) {
            throw new ServiceException(INVALID_TOKEN);
        }
    }

    public boolean isValidService(Claims claims) {


        return "internal".equals(claims.get("type"))
                && COLLECTION_SERVICE.equals(claims.get("service"));
    }
}
