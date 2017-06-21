package me.illian.euromillions.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import me.illian.euromillions.model.DrawInformation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ComputeStatisticService {

    public Map<Integer, Set<LocalDate>> getBallDates(final Set<DrawInformation> information) {
        final SetMultimap<Integer, LocalDate> result = HashMultimap.create();

        information.forEach(drawInformation ->
                Arrays.stream(drawInformation.getDraw().getBalls()).forEach(
                        ball -> result.put(ball, drawInformation.getDrawDate())
                )
        );

        return Multimaps.asMap(result);
    }

    public Map<Integer, Set<LocalDate>> getStarDates(final Set<DrawInformation> information) {
        final SetMultimap<Integer, LocalDate> result = HashMultimap.create();

        information.forEach(drawInformation ->
                Arrays.stream(drawInformation.getDraw().getStars()).forEach(
                        star -> result.put(star, drawInformation.getDrawDate())
                )
        );

        return Multimaps.asMap(result);
    }

    public Map<Integer, LocalDate> getBallLastDate(final Set<DrawInformation> information) {
        final Map<Integer, LocalDate> result = new HashMap<>();
        this.getBallDates(information).forEach((key, value) -> result.put(key, Collections.max(value)));
        return result;
    }

    public Map<Integer, LocalDate> getStarLastDate(final Set<DrawInformation> information) {
        final Map<Integer, LocalDate> result = new HashMap<>();
        this.getStarDates(information).forEach((key, value) -> result.put(key, Collections.max(value)));
        return result;
    }

    public Map<Pair<Integer, Integer>, Set<LocalDate>> getBallPairDates(final Set<DrawInformation> information) {
        return getPairDates(this.getBallDates(information));
    }

    public Map<Pair<Integer, Integer>, Set<LocalDate>> getStarPairDates(final Set<DrawInformation> information) {
        return getPairDates(this.getStarDates(information));
    }

    private static Map<Pair<Integer, Integer>, Set<LocalDate>> getPairDates(final Map<Integer, Set<LocalDate>> datesMap) {
        final Map<Pair<Integer, Integer>, Set<LocalDate>> result = new HashMap<>();
        for (int i = 1; i < datesMap.size(); ++i) {
            for (int j = i + 1; j <= datesMap.size(); ++j) {
                result.put(new ImmutablePair<>(i, j),
                        Sets.intersection(datesMap.getOrDefault(i, Collections.emptySet()),
                                datesMap.getOrDefault(j, Collections.emptySet())));
            }
        }
        return result;
    }

    public Map<Pair<Integer, Integer>, Integer> getBallPairOccurrence(final Set<DrawInformation> information) {
        final Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
        this.getBallPairDates(information).forEach(
                (key, value) -> result.put(key, value.size())
        );
        return result;
    }

    public Map<Pair<Integer, Integer>, Integer> getStarPairOccurrence(final Set<DrawInformation> information) {
        final Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
        this.getStarPairDates(information).forEach(
                (key, value) -> result.put(key, value.size())
        );
        return result;
    }

    public int getTotalDrawCount(final Set<DrawInformation> information) {
        return information.size();
    }

    public Map<Integer, Integer> getBallOccurrence(final Set<DrawInformation> information) {
        final Map<Integer, Integer> result = new HashMap<>();
        this.getBallDates(information).forEach((key, value) -> result.put(key, value.size()));
        return result;
    }

    public Map<Integer, Integer> getStarOccurrence(final Set<DrawInformation> information) {
        final Map<Integer, Integer> result = new HashMap<>();
        this.getStarDates(information).forEach((key, value) -> result.put(key, value.size()));
        return result;
    }
}

