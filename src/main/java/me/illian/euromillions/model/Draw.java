package me.illian.euromillions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Builder
@Getter
public class Draw {
    int[] balls;
    int[] stars;

    public boolean containsBall(final int n) {
        return Arrays.stream(balls).anyMatch(ball -> ball == n);
    }

    public boolean containsStar(final int n) {
        return Arrays.stream(stars).anyMatch(star -> star == n);
    }
}
