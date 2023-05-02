package at.adesso.leagueapi.commons.util.jwt;

import at.adesso.leagueapi.commons.security.Encryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenValidator extends AbstractJwtConfig {

    private final JwtTokenUtil jwtTokenUtil;

    protected JwtTokenValidator(@Value("${jwt.key.signature}") final String encryptedPassword,
                                final Encryptor encryptor,
                                final JwtTokenUtil jwtTokenUtil) {
        super(encryptedPassword, encryptor);
        this.jwtTokenUtil = jwtTokenUtil;
    }


    private Boolean isTokenExpired(final String token) {
        final Date expiration = jwtTokenUtil.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(final String token) {
        return !isTokenExpired(token);
    }
}
