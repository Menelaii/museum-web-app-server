package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.mappers.ArtifactMapper;
import ru.solovetskyJungs.museum.models.dto.ArtifactShortDTO;
import ru.solovetskyJungs.museum.models.dto.ArtifactUploadDTO;
import ru.solovetskyJungs.museum.models.dto.PageDTO;
import ru.solovetskyJungs.museum.models.dto.articles.ArtifactDTO;
import ru.solovetskyJungs.museum.models.entities.Artifact;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable Long id,
                                     @RequestBody ArtifactUploadDTO uploadDTO
    ) {
        Artifact artifact = artifactMapper.map(uploadDTO);
        service.edit(id, artifact);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/preview")
    public ResponseEntity<Void> changePreview(@PathVariable Long id, @RequestParam Long imageId) {
        service.changePreview(id, imageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
    public ResponseEntity<Void> addImage(@PathVariable Long id,
                                         @RequestPart("image") MultipartFile image,
                                         @RequestParam("isPreview") Boolean isPreview
    ) {
        service.addImage(id, image, isPreview);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{artifactId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long artifactId,
                                            @PathVariable Long imageId) {
        service.deleteImage(artifactId, imageId);
        return ResponseEntity.ok().build();
    }
}
