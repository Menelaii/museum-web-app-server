package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiographyAttachment extends AbstractEntity {
    private boolean isPreview;

    @ManyToOne
    @JoinColumn(name = "biography_id", referencedColumnName = "id")
    private Biography biography;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment fileAttachment;
}
