package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.MilitaryRankShortDTO;
import ru.solovetskyJungs.museum.dto.MilitaryRankUploadDTO;
import ru.solovetskyJungs.museum.entities.MilitaryRank;
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
}
