package ru.solovetskyJungs.museum.entities;

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
public class ArtifactAttachment extends AbstractEntity {
    private boolean isPreview;

    @ManyToOne
    @JoinColumn(name = "artifact_id", referencedColumnName = "id")
    private Artifact artifact;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment fileAttachment;
}
