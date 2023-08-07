package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.entities.Account;
import ru.solovetskyJungs.museum.repositories.AccountsRepository;
import ru.solovetskyJungs.museum.security.UserDetailsImpl;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = repository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("USER_NOT_FOUND");
        }

        return new UserDetailsImpl(user.get());
    }
}
