package org.example.ecom00.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.ecom00.API.model.LoginBody;
import org.example.ecom00.API.model.PasswordResetBody;
import org.example.ecom00.API.model.RegistrationBody;
import org.example.ecom00.exception.EmailFailureException;
import org.example.ecom00.exception.EmailNotFoundException;
import org.example.ecom00.exception.UserAlreadyExistsException;
import org.example.ecom00.exception.UserNotVerifiedException;
import org.example.ecom00.model.DAO.VerificationTokenDAO;
import org.example.ecom00.model.DAO.localUserDAO;
import org.example.ecom00.model.LocalUser;
import org.example.ecom00.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    localUserDAO l;
    private JWTService jwtService;
    private EncryptionService encryptionService;
    /** The VerificationTokenDAO. */
    private VerificationTokenDAO verificationTokenDAO;
    /** The email service. */
    private EmailService emailService;
    public UserService(localUserDAO l, VerificationTokenDAO verificationTokenDAO, EncryptionService encryptionService,
                       JWTService jwtService, EmailService emailService) {
        this.l = l;
        this.verificationTokenDAO = verificationTokenDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }
    public LocalUser registerUser(@Valid RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        if (l.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || l.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        //TODO: Encrypt passwords!!

        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return l.save(user);
    }
    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }
    public String loginUser(LoginBody loginBody) throws EmailFailureException, UserNotVerifiedException {
        Optional<LocalUser> opUser = l.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }
    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
       // System.out.println(opToken.get());
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                l.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
    /**
     * Sends the user a forgot password reset based on the email provided.
     * @param email The email to send to.
     * @throws EmailNotFoundException Thrown if there is no user with that email.
     * @throws EmailFailureException
     */
    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = l.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }
    /**
     * Resets the users password using a given token and email.
     * @param body The password reset information.
     */
    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = l.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            l.save(user);
        }
    }
}
