package me.illian.euromillions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Draw {
    int[] balls;
    int[] stars;
}
