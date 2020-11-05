package uk.domain.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.domain.springsecurity.model.AppUser;
import uk.domain.springsecurity.repository.AppUserRepository;

@Component
public class StartController {

    PasswordEncoder passwordEncoder;
    AppUserRepository appUserRepository;

    @Autowired
    public StartController(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;

        AppUser appUser = new AppUser();
        appUser.setUsername("Alicja");
        appUser.setEnabled(true);
        appUser.setRole("ROLE_USER");
        appUser.setSessionStorageInDays("7");
        appUser.setPassword(passwordEncoder.encode("Alicja123"));
        appUserRepository.save(appUser);
    }

}
