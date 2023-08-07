package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account extends AbstractEntity {
    private String username;
    private String password;
    private String role;
    private boolean isLocked;
}
