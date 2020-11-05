package uk.domain.springsecurity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "persistent_logins")
@Getter
@Setter
public class PersistentLogin {

    @Id
    private String series;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDate lastUsed;

    public PersistentLogin(String series, String username, String token, LocalDate lastUsed) {
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }

    public PersistentLogin() {
    }
}
