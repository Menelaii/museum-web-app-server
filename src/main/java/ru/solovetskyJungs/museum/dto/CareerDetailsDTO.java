package ru.solovetskyJungs.museum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CareerDetailsDTO{
    private String placeOfService;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
}
