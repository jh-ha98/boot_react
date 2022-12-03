package jh.project.myproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void passwordTest() {
        String password = "1234";

        String encodedPassword = passwordEncoder.encode(password);

        assertEquals(true, passwordEncoder.matches(password, encodedPassword));
    }
}