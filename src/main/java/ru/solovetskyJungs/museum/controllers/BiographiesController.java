package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.*;
import ru.solovetskyJungs.museum.searchCriterias.BiographySearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.services.BiographiesService;
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.mappers.BiographyMapper;

import java.util.List;

@RestController
@RequestMapping("/api/biographies")
@RequiredArgsConstructor
public class BiographiesController {
    private final BiographiesService service;
    private final BiographyMapper biographyMapper;

    @GetMapping
    public ResponseEntity<PageDTO<BiographyShortDTO>> getAll(XPage page, BiographySearchCriteria searchCriteria) {
        Page<BiographyShortDTO> dtosPage = service.findAllWithFilters(page, searchCriteria)
                .map(biographyMapper::mapToShort);

        return ResponseEntity.ok(new PageDTO<>(
                dtosPage.getContent(),
                dtosPage.getTotalElements()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BiographyDTO> getById(@PathVariable("id") Long id) {
        Biography biography = service.getById(id);
        return ResponseEntity.ok(biographyMapper.map(biography));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> create(
            @RequestPart("biography") BiographyUploadDTO biographyUploadDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart("preview") MultipartFile preview,
            @RequestPart(value = "presentation", required = false) MultipartFile presentation) {

        Biography biography = biographyMapper.map(biographyUploadDTO);
        service.save(biography, images, preview, presentation);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
