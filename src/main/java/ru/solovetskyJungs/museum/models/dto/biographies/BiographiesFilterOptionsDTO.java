package ru.solovetskyJungs.museum.models.dto.biographies;

import java.util.List;

public record BiographiesFilterOptionsDTO(List<String> placesOfService,
                                          List<String> medals,
                                          List<String> militaryRanks) { }
