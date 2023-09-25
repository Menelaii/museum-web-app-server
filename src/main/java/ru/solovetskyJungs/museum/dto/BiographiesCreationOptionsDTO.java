package ru.solovetskyJungs.museum.dto;

import java.util.List;

public record BiographiesCreationOptionsDTO(
        List<MedalShortDTO> medals,
        List<MilitaryRankShortDTO> militaryRanks
) { }
