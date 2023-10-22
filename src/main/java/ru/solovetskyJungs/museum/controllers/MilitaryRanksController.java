package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankShortDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankUploadDTO;
import ru.solovetskyJungs.museum.models.entities.MilitaryRank;
import ru.solovetskyJungs.museum.mappers.MilitaryRankMapper;
import ru.solovetskyJungs.museum.services.MilitaryRankService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/military-ranks")
public class MilitaryRanksController {
    private final MilitaryRankService service;
    private final MilitaryRankMapper militaryRankMapper;

    @GetMapping
    public ResponseEntity<List<MilitaryRankShortDTO>> getAllMilitaryRanks() {
        List<MilitaryRank> militaryRanks = service.getAll();
        List<MilitaryRankShortDTO> militaryRankDTOs = militaryRanks.stream()
                .map(militaryRankMapper::toShortDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(militaryRankDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MilitaryRankDTO> getById(@PathVariable("id") Long id) {
        MilitaryRank militaryRank = service.getById(id);
        return ResponseEntity.ok(militaryRankMapper.map(militaryRank));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createMilitaryRank(
            @RequestPart("militaryRank") MilitaryRankUploadDTO militaryRankUploadDTO,
            @RequestPart("image") MultipartFile image) {

        MilitaryRank militaryRank = militaryRankMapper.map(militaryRankUploadDTO);
        service.save(militaryRank, image);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilitaryRank(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> editMilitaryRank(@PathVariable("id") Long id,
                                                 @RequestBody MilitaryRankUploadDTO militaryRankEditRequestDTO
    ) {
        MilitaryRank militaryRank = militaryRankMapper.map(militaryRankEditRequestDTO);
        service.edit(id, militaryRank);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/preview")
    public ResponseEntity<Void> changePreview(@PathVariable("id") Long id,
                                              @RequestBody MultipartFile image
    ) {
        service.changePreview(id, image);
        return ResponseEntity.ok().build();
    }
}
