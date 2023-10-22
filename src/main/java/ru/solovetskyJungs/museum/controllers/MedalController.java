package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.dto.medals.MedalDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalShortDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalUploadDTO;
import ru.solovetskyJungs.museum.models.entities.Medal;
import ru.solovetskyJungs.museum.mappers.MedalMapper;
import ru.solovetskyJungs.museum.services.MedalService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medals")
public class MedalController {
    private final MedalService service;
    private final MedalMapper medalMapper;

    @GetMapping
    public ResponseEntity<List<MedalShortDTO>> getAll() {
        List<Medal> medals = service.getAll();
        List<MedalShortDTO> medalDTOs = medals.stream()
                .map(medalMapper::toShortDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(medalDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedalDTO> getMedal(@PathVariable Long id) {
        Medal medal = service.getById(id);
        return ResponseEntity.ok(medalMapper.map(medal));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createMedal(
            @RequestPart("medal") MedalUploadDTO medalUploadDTO,
            @RequestPart("image") MultipartFile image
    ) {
        Medal medal = medalMapper.map(medalUploadDTO);
        service.create(medal, image);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedal(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> editMedal(@PathVariable("id") Long id,
                                              @RequestBody MedalUploadDTO uploadDTO
    ) {
        Medal medal = medalMapper.map(uploadDTO);
        service.edit(id, medal);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/preview")
    public ResponseEntity<Void> changePreview(@PathVariable("id") Long id,
                                                 @RequestBody MultipartFile image) {
        service.changePreview(id, image);
        return ResponseEntity.ok().build();
    }
}
