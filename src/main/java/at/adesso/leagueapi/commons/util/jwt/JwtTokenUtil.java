package at.adesso.leagueapi.commons.util.jwt;

import at.adesso.leagueapi.commons.security.Encryptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil extends AbstractJwtConfig {

    public static final String USER_ID_TOKEN_PARAMETER_NAME = "userId";
    public static final String ROLE_TOKEN_PARAMETER_NAME = "role";

    protected JwtTokenUtil(@Value("${jwt.key.signature}") final String encryptedPassword,
                           final Encryptor encryptor) {
        super(encryptedPassword, encryptor);
    }

    public String getUserIdFromToken(final String token) {
        return getClaimFromToken(token, claims -> claims.get(USER_ID_TOKEN_PARAMETER_NAME, String.class));
    }

    public String getRoleFromToken(final String token) {
        return getClaimFromToken(token, claims -> claims.get(ROLE_TOKEN_PARAMETER_NAME, String.class));
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
