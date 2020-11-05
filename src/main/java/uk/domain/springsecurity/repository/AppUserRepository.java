package uk.domain.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.domain.springsecurity.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findAllByUsername(String username);
}
