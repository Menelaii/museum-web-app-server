package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.BiographyDTO;
import ru.solovetskyJungs.museum.dto.BiographyUploadDTO;
import ru.solovetskyJungs.museum.services.BiographiesService;
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.mappers.BiographyMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/biographies")
@RequiredArgsConstructor
public class BiographiesController {
    private final BiographiesService service;
    private final BiographyMapper biographyMapper;

    @GetMapping
    public ResponseEntity<List<BiographyDTO>> getAll() {
        List<BiographyDTO> dtos = service.getAll()
                .stream()
                .map(biographyMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> create(
            @RequestPart("biography") BiographyUploadDTO biographyUploadDTO,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("presentation") MultipartFile presentation) {

        Biography biography = biographyMapper.map(biographyUploadDTO);
        service.save(biography, images, biographyUploadDTO.imagesDescriptions(), presentation);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
