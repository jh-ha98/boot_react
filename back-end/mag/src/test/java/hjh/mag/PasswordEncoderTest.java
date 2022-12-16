package hjh.mag;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("비밀번호 암호화 테스트")
    @Test
    void passwordTest() {
        String password = "1234";

        String encodedPassword = passwordEncoder.encode(password);

        assertTrue(passwordEncoder.matches(password, encodedPassword));
    }
}