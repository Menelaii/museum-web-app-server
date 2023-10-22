package ru.solovetskyJungs.museum.models.dto.biographies;

import ru.solovetskyJungs.museum.models.dto.medals.MedalShortDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankShortDTO;

import java.util.List;

public record BiographiesCreationOptionsDTO(
        List<MedalShortDTO> medals,
        List<MilitaryRankShortDTO> militaryRanks
) { }
