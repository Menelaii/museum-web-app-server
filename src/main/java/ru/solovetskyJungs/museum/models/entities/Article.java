package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends AbstractEntity {
    private String title;
    private LocalDate publishDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment preview;

    public Article(Long id, String title, LocalDate publishDate, FileAttachment preview) {
        super(id);
        this.title = title;
        this.publishDate = publishDate;
        this.preview = preview;
    }
}
