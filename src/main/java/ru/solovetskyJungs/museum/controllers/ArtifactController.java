package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.ArtifactDTO;
import ru.solovetskyJungs.museum.dto.ArtifactShortDTO;
import ru.solovetskyJungs.museum.dto.ArtifactUploadDTO;
import ru.solovetskyJungs.museum.dto.PageDTO;
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.mappers.ArtifactMapper;
import ru.solovetskyJungs.museum.searchCriterias.ArtifactsSearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.services.ArtifactService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artifacts")
public class ArtifactController {
    private final ArtifactService service;
    private final ArtifactMapper artifactMapper;


    @GetMapping
    public ResponseEntity<PageDTO<ArtifactShortDTO>> getAll(XPage page, ArtifactsSearchCriteria searchCriteria) {
        Page<ArtifactShortDTO> dtosPage = service.findAllWithFilters(page, searchCriteria)
                .map(artifactMapper::mapToShort);

        return ResponseEntity.ok(new PageDTO<>(
                dtosPage.getContent(),
                dtosPage.getTotalElements()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtifactDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Artifact artifact = service.getById(id);

        return ResponseEntity.ok(artifactMapper.map(artifact));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createArtifact(
            @RequestPart("artifact") ArtifactUploadDTO artifactUploadDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart("preview") MultipartFile preview) {

        Artifact artifact = artifactMapper.map(artifactUploadDTO);
        service.create(artifact, images, preview);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable Long id) {
        service.deleteArtifact(id);
        return ResponseEntity.ok().build();
    }
}
