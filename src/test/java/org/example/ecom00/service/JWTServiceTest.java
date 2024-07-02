package org.example.ecom00.service;

import org.example.ecom00.model.DAO.localUserDAO;
import org.example.ecom00.model.LocalUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {
    /** The JWTService to test. */
    @Autowired
    private JWTService jwtService;
    /** The Local User DAO. */
    @Autowired
    private localUserDAO l;
    @Test
    public void testVerificationTokenNotUsableForLogin() {
        LocalUser user = l.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    /**
     * Tests that the authentication token generate still returns the username.
     */
    @Test
    public void testAuthTokenReturnsUsername() {
        LocalUser user = l.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
    }
    /**
     * Tests the password reset generation and verification.
     */
    @Test
    public void testPasswordResetToken() {
        LocalUser user = l.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generatePasswordResetJWT(user);
        Assertions.assertEquals(user.getEmail(),
                jwtService.getResetPasswordEmail(token), "Email should match inside " +
                        "JWT.");
    }

}
