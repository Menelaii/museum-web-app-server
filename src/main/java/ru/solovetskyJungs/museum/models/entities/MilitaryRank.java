package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MilitaryRank extends AbstractEntity {
    private String title;

    public MilitaryRank(Long id) {
        super(id);
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment image;

    @OneToMany(mappedBy = "rank", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<MilitaryRankDetails> militaryRankDetails;
}
