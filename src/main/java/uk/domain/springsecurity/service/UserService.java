package uk.domain.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.domain.springsecurity.model.AppUser;
import uk.domain.springsecurity.model.VerificationToken;
import uk.domain.springsecurity.repository.AppUserRepository;
import uk.domain.springsecurity.repository.VerificationTokenRepository;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;
    private VerificationTokenRepository verificationTokenRepository;

    private final String OWNER_ADDRESS_EMAIL = "alice.mine.36@gmail.com";

    @Autowired
    public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService, VerificationTokenRepository verificationTokenRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void saveUserApp(AppUser appUser, HttpServletRequest request){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        if (appUser.getRole().equals("ROLE_ADMIN")){
            appUser.setAdminRequested(true);
        }
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(appUser, token);
        verificationTokenRepository.save(verificationToken);

        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            mailSenderService.sendMail(appUser.getUsername(), "Verification token", url + "/verify-token?token=" + token, false);
            if (appUser.getRole().equals("ROLE_ADMIN")){
                mailSenderService.sendMail(OWNER_ADDRESS_EMAIL, "Verification token", url + "/verify-token-owner?token=" + token, false);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepository.findByValue(token).getAppUser();
        if (!appUser.getRole().equals("ROLE_USER")) {
            appUser.setRole("ROLE_USER");
        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
    }

    public void verifyTokenOwner(String token) {
        AppUser appUser = verificationTokenRepository.findByValue(token).getAppUser();
        if (appUser.isAdminRequested()) {
            appUser.setRole("ROLE_ADMIN");
            appUser.setEnabled(true);
        }
        appUserRepository.save(appUser);
    }
}
