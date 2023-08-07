package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.ArtifactDTO;
import ru.solovetskyJungs.museum.dto.ArtifactUploadDTO;
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.mappers.ArtifactMapper;
import ru.solovetskyJungs.museum.mappers.FileAttachmentMapper;
import ru.solovetskyJungs.museum.services.ArtifactService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artifacts")
public class ArtifactController {
    private final ArtifactService service;
    private final ArtifactMapper artifactMapper;

    @GetMapping
    public ResponseEntity<List<ArtifactDTO>> getAllArtifacts() {
        List<Artifact> artifacts = service.getAll();
        List<ArtifactDTO> artifactDTOs = artifacts.stream()
                .map(artifactMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(artifactDTOs);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createArtifact(
            @RequestPart("artifact") ArtifactUploadDTO artifactUploadDTO,
            @RequestPart("images") List<MultipartFile> images) {

        Artifact artifact = artifactMapper.map(artifactUploadDTO);
        service.create(artifact, images, artifactUploadDTO.imagesDescriptions());

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable Long id) {
        service.deleteArtifact(id);
        return ResponseEntity.ok().build();
    }
}
