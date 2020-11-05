package uk.domain.springsecurity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String value;

    @OneToOne
    private AppUser appUser;

    public VerificationToken(AppUser appUser, String value) {
        this.appUser = appUser;
        this.value = value;
    }

    public VerificationToken() {
    }
}
