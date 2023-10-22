package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.models.entities.Account;
import ru.solovetskyJungs.museum.repositories.AccountsRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistrationService {
    private final static String DEFAULT_ROLE = "ROLE_USER";

    private final AccountsRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Account account) {
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setRole(DEFAULT_ROLE);

        repository.save(account);
    }
}
