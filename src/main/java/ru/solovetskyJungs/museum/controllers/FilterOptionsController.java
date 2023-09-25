package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solovetskyJungs.museum.dto.BiographiesCreationOptionsDTO;
import ru.solovetskyJungs.museum.dto.BiographiesFilterOptionsDTO;
import ru.solovetskyJungs.museum.dto.MedalShortDTO;
import ru.solovetskyJungs.museum.dto.MilitaryRankShortDTO;
import ru.solovetskyJungs.museum.mappers.MedalMapper;
import ru.solovetskyJungs.museum.mappers.MilitaryRankMapper;
import ru.solovetskyJungs.museum.services.CareerDetailsService;
import ru.solovetskyJungs.museum.services.MedalService;
import ru.solovetskyJungs.museum.services.MilitaryRankService;

import java.util.List;

@RestController
@RequestMapping("api/filter-options")
@RequiredArgsConstructor
public class FilterOptionsController {
    private final MilitaryRankService militaryRankService;
    private final MedalService medalService;
    private final CareerDetailsService careerDetailsService;
    private final MedalMapper medalMapper;
    private final MilitaryRankMapper militaryRankMapper;

    @GetMapping("/biographies")
    public ResponseEntity<BiographiesFilterOptionsDTO> getBiographiesFilterOptions() {
        List<String> militaryRankTitles = militaryRankService.getTitles();
        List<String> medalTitles = medalService.getTitles();
        List<String> placesOfService = careerDetailsService.getPlacesOfService();

        return ResponseEntity.ok(
          new BiographiesFilterOptionsDTO(
                  placesOfService,
                  medalTitles,
                  militaryRankTitles
          )
        );
    }

    @GetMapping("/biographies/creation")
    public ResponseEntity<BiographiesCreationOptionsDTO> getBiographiesCreationOptions() {
        List<MilitaryRankShortDTO> militaryRanks = militaryRankMapper.map(
                militaryRankService.getAllWithShortSelect()
        );

        List<MedalShortDTO> medals = medalMapper.map(
                medalService.getAllWithShortSelect()
        );

        return ResponseEntity.ok(
                new BiographiesCreationOptionsDTO(
                        medals,
                        militaryRanks
                )
        );
    }
}
