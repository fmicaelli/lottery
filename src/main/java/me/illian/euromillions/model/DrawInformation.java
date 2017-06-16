package me.illian.euromillions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class DrawInformation {
    private final String drawNumberInTheYear;
    private final LocalDate drawDate;
    private final int numberInTheCycle;
    private final Draw draw;
}
