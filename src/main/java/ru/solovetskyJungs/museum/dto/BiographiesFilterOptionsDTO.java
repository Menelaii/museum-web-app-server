package ru.solovetskyJungs.museum.dto;

import java.util.List;

public record BiographiesFilterOptionsDTO(List<String> placesOfService,
                                          List<String> medals,
                                          List<String> militaryRanks) { }
