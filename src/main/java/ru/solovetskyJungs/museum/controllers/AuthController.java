package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solovetskyJungs.museum.dto.AuthRequestDTO;
import ru.solovetskyJungs.museum.dto.AuthResponseDTO;
import ru.solovetskyJungs.museum.dto.RegistrationRequestDTO;
import ru.solovetskyJungs.museum.entities.Account;
import ru.solovetskyJungs.museum.security.JWTUtil;
import ru.solovetskyJungs.museum.services.RegistrationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> performRegistration(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        registrationService.register(modelMapper.map(registrationRequestDTO, Account.class));
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> performLogin(@RequestBody AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequestDTO.username(), authRequestDTO.password());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtil.generateToken(authRequestDTO.username(), tokenExpiresIn);

        return new ResponseEntity<>(new AuthResponseDTO(token, tokenExpiresIn), HttpStatus.OK);
    }
}
