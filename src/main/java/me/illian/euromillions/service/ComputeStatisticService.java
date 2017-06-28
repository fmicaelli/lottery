package me.illian.euromillions.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public Map<Integer, Integer> getBallGap(final Set<DrawInformation> information) {
        // Sort the draw information in descending date order then find the gap
        final List<DrawInformation> sortedInformation =
                information.stream()
                        .sorted((o1, o2) -> o2.getDrawDate().compareTo(o1.getDrawDate()))
                        .collect(Collectors.toList());
        final Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < sortedInformation.size(); ++i) {
            // Fuck java
            final int gap = i;
            Arrays.stream(sortedInformation.get(i).getDraw().getBalls())
                    .forEach(
                            ball -> result.merge(ball, gap, Math::min)
                    );
        }

        return result;
    }

    public Map<Integer, Integer> getStarGap(final Set<DrawInformation> information) {
        // Sort the draw information in descending date order then find the gap
        final List<DrawInformation> sortedInformation =
                information.stream()
                        .sorted((o1, o2) -> o2.getDrawDate().compareTo(o1.getDrawDate()))
                        .collect(Collectors.toList());
        final Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < sortedInformation.size(); ++i) {
            // Fuck java
            final int gap = i;
            Arrays.stream(sortedInformation.get(i).getDraw().getStars())
                    .forEach(
                            star -> result.merge(star, gap, Math::min)
                    );
        }

        return result;
    }

    public int getTotalDrawCount(final Set<DrawInformation> information) {
        return information.size();
    }

    public Map<LocalDate, Double> getBallDrawMean(final Set<DrawInformation> information) {
        final Map<LocalDate, Double> result = new HashMap<>();
        information.forEach(
                drawInformation -> {
                    final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
                    Arrays.stream(drawInformation.getDraw().getBalls()).forEach(
                            descriptiveStatistics::addValue
                    );
                    result.put(drawInformation.getDrawDate(), descriptiveStatistics.getMean());
                }
        );
        return result;
    }

    public Map<LocalDate, Double> getStarDrawMean(final Set<DrawInformation> information) {
        final Map<LocalDate, Double> result = new HashMap<>();
        information.forEach(
                drawInformation -> {
                    final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
                    Arrays.stream(drawInformation.getDraw().getStars()).forEach(
                            descriptiveStatistics::addValue
                    );
                    result.put(drawInformation.getDrawDate(), descriptiveStatistics.getMean());
                }
        );
        return result;
    }

    public Double getBallDrawsMean(final Set<DrawInformation> information) {
        return getDrawsMean(getBallDrawMean(information).values());
    }

    public Double getStarDrawsMean(final Set<DrawInformation> information) {
        return getDrawsMean(getStarDrawMean(information).values());
    }

    private static Double getDrawsMean(final Collection<Double> means) {
        final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        means.forEach(descriptiveStatistics::addValue);
        return descriptiveStatistics.getMean();
    }

    public Map<Integer, Double> getBallPositionMean(final Set<DrawInformation> information) {
        return getPositionMean(information.stream()
                .map(DrawInformation::getDraw)
                .map(Draw::getBalls)
                .collect(Collectors.toList()));
    }

    public Map<Integer, Double> getStarPositionMean(final Set<DrawInformation> information) {
        return getPositionMean(information.stream()
                .map(DrawInformation::getDraw)
                .map(Draw::getStars)
                .collect(Collectors.toList()));
    }

    private static Map<Integer, Double> getPositionMean(final List<int[]> draws) {
        assert(!draws.isEmpty());
        final Map<Integer, Double> result = new HashMap<>();
        for (int i = 0; i < draws.get(0).length; ++i) {
            // Fuck java
            final int position = i;
            final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
            draws.stream().map(draw -> draw[position])
                    .forEach(descriptiveStatistics::addValue);
            result.put(i + 1, descriptiveStatistics.getMean());
        }
        return result;
    }
}

