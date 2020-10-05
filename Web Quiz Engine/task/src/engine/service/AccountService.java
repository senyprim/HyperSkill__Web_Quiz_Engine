package engine.service;

import engine.model.Account;
import engine.model.Question;
import engine.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =  accountRepo.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("Username["+username+"] not found"));
        return new User(account.getUsername(),account.getPassword()
                , Collections.singletonList(Role.USER));
    }
    public Account loadAccountByUsername(String username){
        return   accountRepo.findByUsername(username).orElse(null);
    }

    public boolean addAccount(Account account){
        Optional<Account> existAccount = accountRepo.findByUsername(account.getUsername());
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        try {
            accountRepo.save(account);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Account updateAccount(Account account){
        return accountRepo.save(account);
    }
}
