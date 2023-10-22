package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileAttachment extends AbstractEntity {
    private String uri;

    public FileAttachment(Long id, String uri) {
        super(id);
        this.uri = uri;
    }

    public FileAttachment(String uri) {
        this.uri = uri;
    }
}
