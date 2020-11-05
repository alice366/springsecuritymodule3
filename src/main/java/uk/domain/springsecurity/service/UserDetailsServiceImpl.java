package uk.domain.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.domain.springsecurity.repository.AppUserRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    AppUserRepository appUserRepository;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return appUserRepository.findAllByUsername(s);
    }

    public long loadSessionStorageTimeForUser(String username) {
        return Long.parseLong(appUserRepository.findAllByUsername(username).getSessionStorageInDays());
    }
}
