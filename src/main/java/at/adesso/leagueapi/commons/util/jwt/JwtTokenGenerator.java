package at.adesso.leagueapi.commons.util.jwt;

import at.adesso.leagueapi.commons.security.Encryptor;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenGenerator extends AbstractJwtConfig {


    protected JwtTokenGenerator(@Value("${jwt.key.signature}") final String encryptedPassword,
                                final Encryptor encryptor) {
        super(encryptedPassword, encryptor);
    }

    public String generateToken(final String userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(Date.from(LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .setExpiration(Date.from(LocalDateTime.now()
                        .plus(1, ChronoUnit.DAYS)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(getSigningKey())
                .compact();
    }


}
