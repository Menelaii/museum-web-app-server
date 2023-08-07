package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.FileAttachmentUploadDTO;
import ru.solovetskyJungs.museum.dto.MedalDTO;
import ru.solovetskyJungs.museum.dto.MedalUploadDTO;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.Medal;
import ru.solovetskyJungs.museum.mappers.FileAttachmentMapper;
import ru.solovetskyJungs.museum.mappers.MedalMapper;
import ru.solovetskyJungs.museum.services.MedalService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medals")
public class MedalController {
    private final MedalService service;
    private final MedalMapper medalMapper;

    @GetMapping
    public ResponseEntity<List<MedalDTO>> getAll() {
        List<Medal> medals = service.getAll();
        List<MedalDTO> medalDTOs = medals.stream()
                .map(medalMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(medalDTOs);
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createMedal(
            @RequestPart("medal") MedalUploadDTO medalUploadDTO,
            @RequestPart("image") MultipartFile image) {

        try {
            Medal medal = medalMapper.map(medalUploadDTO);
            service.create(medal, image, medalUploadDTO.imageDescription());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedal(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
