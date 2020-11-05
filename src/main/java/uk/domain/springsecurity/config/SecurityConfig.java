package uk.domain.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import uk.domain.springsecurity.service.UserDetailsServiceImpl;

import javax.sql.DataSource;
import java.security.Principal;

//-- daj do token repo
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private UserDetailsServiceImpl userDetailsService;
//    private Principal principal;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserDetailsServiceImpl userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
//        this.principal = principal;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").authenticated()
                .antMatchers("/signup").permitAll()
                .antMatchers("/all").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/user").permitAll()
                .and()
                .logout()
                .and().rememberMe().tokenRepository(persistenceTokenRepository()).tokenValiditySeconds(24 * 60 * 60).rememberMeCookieName("refresh").rememberMeParameter("rememberme");
//        (int) userDetailsService.loadSessionStorageTimeForUser(principal.getName())
    }

    @Bean
    public PersistentTokenRepository persistenceTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
