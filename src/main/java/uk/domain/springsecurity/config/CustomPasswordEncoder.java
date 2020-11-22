package uk.domain.springsecurity.config;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return BCrypt.hashpw(charSequence.toString(), BCrypt.gensalt(12));
    }

    @Override
    public boolean matches(CharSequence charSequence, String encoded) {
        return  BCrypt.checkpw(charSequence.toString(), encoded);
    }
}
