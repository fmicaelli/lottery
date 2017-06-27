package me.illian.euromillions.service;

import com.google.common.collect.Sets;
import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

@RunWith(SpringJUnit4ClassRunner.class)
public class ComputeStatisticServiceTest {

    private final static LocalDate DRAW_DATE_1 = LocalDate.now().minusDays(10);
    private final static LocalDate DRAW_DATE_2 = LocalDate.now().minusDays(5);

    @Autowired
    private ComputeStatisticService computeStatisticService;

    @Test
    public void getDatesTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<Integer, Set<LocalDate>> ballDates = this.computeStatisticService.getBallDates(drawInformationSet);
        final Map<Integer, Set<LocalDate>> starDates = this.computeStatisticService.getStarDates(drawInformationSet);
        assertThat(ballDates).containsOnlyKeys(1, 2, 3, 4, 5, 6, 7, 8);
        assertThat(starDates).containsOnlyKeys(1, 2, 3);

        assertThat(ballDates)
                .containsEntry(1, Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(2, Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(3, Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(4, Sets.newHashSet(DRAW_DATE_1, DRAW_DATE_2))
                .containsEntry(5, Sets.newHashSet(DRAW_DATE_1, DRAW_DATE_2))
                .containsEntry(6, Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(7, Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(8, Sets.newHashSet(DRAW_DATE_2));

        assertThat(starDates)
                .containsEntry(1, Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(2, Sets.newHashSet(DRAW_DATE_1, DRAW_DATE_2))
                .containsEntry(3, Sets.newHashSet(DRAW_DATE_2));
    }

    @Test
    public void getPairDatesTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<Pair<Integer, Integer>, Set<LocalDate>> ballPairDates =
                this.computeStatisticService.getBallPairDates(drawInformationSet);
        final Map<Pair<Integer, Integer>, Set<LocalDate>> starPairDates =
                this.computeStatisticService.getStarPairDates(drawInformationSet);
        assertThat(ballPairDates)
                .hasSize(28)
                .containsEntry(new ImmutablePair<>(1, 2), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(1, 3), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(1, 4), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(1, 5), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(1, 6), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(1, 7), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(1, 8), Collections.emptySet())

                .containsEntry(new ImmutablePair<>(2, 3), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(2, 4), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(2, 5), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(2, 6), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(2, 7), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(2, 8), Collections.emptySet())

                .containsEntry(new ImmutablePair<>(3, 4), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(3, 5), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(3, 6), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(3, 7), Collections.emptySet())
                .containsEntry(new ImmutablePair<>(3, 8), Collections.emptySet())

                .containsEntry(new ImmutablePair<>(4, 5), Sets.newHashSet(DRAW_DATE_1, DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(4, 6), Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(4, 7), Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(4, 8), Sets.newHashSet(DRAW_DATE_2))

                .containsEntry(new ImmutablePair<>(5, 6), Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(5, 7), Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(5, 8), Sets.newHashSet(DRAW_DATE_2))

                .containsEntry(new ImmutablePair<>(6, 7), Sets.newHashSet(DRAW_DATE_2))
                .containsEntry(new ImmutablePair<>(6, 8), Sets.newHashSet(DRAW_DATE_2))

                .containsEntry(new ImmutablePair<>(7, 8), Sets.newHashSet(DRAW_DATE_2));
        assertThat(starPairDates)
                .hasSize(3)
                .containsEntry(new ImmutablePair<>(1, 2), Sets.newHashSet(DRAW_DATE_1))
                .containsEntry(new ImmutablePair<>(1, 3), Collections.emptySet())

                .containsEntry(new ImmutablePair<>(2, 3), Sets.newHashSet(DRAW_DATE_2));
    }

    @Test
    public void getGapTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<Integer, Integer> ballGap = this.computeStatisticService.getBallGap(drawInformationSet);
        final Map<Integer, Integer> starGap = this.computeStatisticService.getStarGap(drawInformationSet);
        assertThat(ballGap)
                .containsOnlyKeys(1, 2, 3, 4, 5, 6, 7, 8);
        assertThat(starGap)
                .containsOnlyKeys(1, 2, 3);

        assertThat(ballGap)
                .containsEntry(1, 1)
                .containsEntry(2, 1)
                .containsEntry(3, 1)
                .containsEntry(4, 0)
                .containsEntry(5, 0)
                .containsEntry(6, 0)
                .containsEntry(7, 0)
                .containsEntry(8, 0);

        assertThat(starGap)
                .containsEntry(1, 1)
                .containsEntry(2, 0)
                .containsEntry(3, 0);
    }

    @Test
    public void getDrawCountTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final int count = this.computeStatisticService.getTotalDrawCount(drawInformationSet);
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void getBallDrawMeanTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<LocalDate, Double> ballMean = this.computeStatisticService.getBallDrawMean(drawInformationSet);
        final Map<LocalDate, Double> starMean = this.computeStatisticService.getStarDrawMean(drawInformationSet);
        assertThat(ballMean)
                .containsOnlyKeys(DRAW_DATE_1, DRAW_DATE_2);

        assertThat(starMean)
                .containsOnlyKeys(DRAW_DATE_1, DRAW_DATE_2);

        assertThat(ballMean)
                .containsEntry(DRAW_DATE_1, 3.)
                .containsEntry(DRAW_DATE_2, 6.);

        assertThat(starMean)
                .containsEntry(DRAW_DATE_1, 1.5)
                .containsEntry(DRAW_DATE_2, 2.5);
    }

    @Test
    public void getBallDrawsMean() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Double ballMean = this.computeStatisticService.getBallDrawsMean(drawInformationSet);
        final Double starMean = this.computeStatisticService.getStarDrawsMean(drawInformationSet);
        assertThat(ballMean).isEqualTo(4.5);
        assertThat(starMean).isEqualTo(2.0);
    }

    private static Set<DrawInformation> createDrawInformation() {
        final Set<DrawInformation> drawInformationSet = new HashSet<>();
        drawInformationSet.add(createDrawInformation(
                DRAW_DATE_1,
                new int[]{1, 2, 3, 4, 5},
                new int[]{1, 2},
                "0000001",
                1));
        drawInformationSet.add(createDrawInformation(
                DRAW_DATE_2,
                new int[]{4, 5, 6, 7, 8},
                new int[]{2, 3},
                "0000002",
                2));
        return drawInformationSet;
    }

    private static DrawInformation createDrawInformation(final LocalDate drawDate,
                                                         final int[] balls,
                                                         final int[] stars,
                                                         final String drawNumberInTheYear,
                                                         final int numberInTheCycle) {
        return DrawInformation.builder()
                .drawDate(drawDate)
                .drawNumberInTheYear(drawNumberInTheYear)
                .numberInTheCycle(numberInTheCycle)
                .draw(Draw.builder()
                        .balls(balls)
                        .stars(stars)
                        .build()
                )
                .build();
    }
}