package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medal extends AbstractEntity {
    private String title;
    private String description;

    public Medal(Long id) {
        super(id);
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment image;


    @OneToMany(mappedBy = "medal", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<MedalDetails> medalDetails;
}