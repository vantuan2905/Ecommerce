package org.example.ecom00.model.DAO;

import org.example.ecom00.model.LocalUser;
import org.example.ecom00.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the VerificationToken data.
 */
public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
    void deleteByUser(LocalUser user);

}