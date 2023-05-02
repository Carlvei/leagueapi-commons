package at.adesso.leagueapi.commons.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {

    private final String secret;


    public Encryptor(@Value("${jwt.key.secret}") final String secret) {
        this.secret = secret;
    }

    public String decrypt(final String encryptedString) {
        final StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(secret);
        return decryptor.decrypt(encryptedString);
    }

    public String encrypt(final String decryptedString) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secret);
        return encryptor.encrypt(decryptedString);
    }
}
