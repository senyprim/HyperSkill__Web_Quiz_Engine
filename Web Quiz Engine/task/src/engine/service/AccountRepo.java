package engine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AccountRepo extends CrudRepository<Account,Integer> {
    Optional<Account> findByUsername(String username);
}
