package me.illian.euromillions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class Draw {
    int[] balls;
    int[] stars;

    @Override
    public String toString() {
        return Arrays.toString(balls) + "," + Arrays.toString(stars);
    }
}
