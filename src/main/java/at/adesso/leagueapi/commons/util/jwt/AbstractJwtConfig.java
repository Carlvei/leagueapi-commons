package at.adesso.leagueapi.commons.util.jwt;

import at.adesso.leagueapi.commons.security.Encryptor;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public abstract class AbstractJwtConfig {

    private final String encryptedPassword;
    private final Encryptor encryptor;

    protected AbstractJwtConfig(@Value("${jwt.key.signature}") final String encryptedPassword,
                                final Encryptor encryptor) {
        this.encryptedPassword = encryptedPassword;
        this.encryptor = encryptor;
    }

    protected Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(encryptor.decrypt(encryptedPassword));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
