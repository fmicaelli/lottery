package me.illian.euromillions.service;

import com.google.common.collect.Sets;
import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

@RunWith(SpringJUnit4ClassRunner.class)
public class ComputeStatisticServiceTest {

    @Autowired
    private ComputeStatisticService computeStatisticService;

    @Test
    public void getDatesTest() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<Integer, Set<LocalDate>> ballDates = this.computeStatisticService.getBallDates(drawInformationSet);
        final Map<Integer, Set<LocalDate>> starDates = this.computeStatisticService.getStarDates(drawInformationSet);
        assertThat(ballDates).containsOnlyKeys(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(starDates).containsOnlyKeys(1, 2, 3);

        assertThat(ballDates)
                .containsEntry(1, Sets.newHashSet(LocalDate.now().minusDays(10)))
                .containsEntry(2, Sets.newHashSet(LocalDate.now().minusDays(10)))
                .containsEntry(3, Sets.newHashSet(LocalDate.now().minusDays(10)))
                .containsEntry(4, Sets.newHashSet(LocalDate.now().minusDays(10)))
                .containsEntry(5, Sets.newHashSet(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5)))
                .containsEntry(6, Sets.newHashSet(LocalDate.now().minusDays(5)))
                .containsEntry(7, Sets.newHashSet(LocalDate.now().minusDays(5)))
                .containsEntry(8, Sets.newHashSet(LocalDate.now().minusDays(5)))
                .containsEntry(9, Sets.newHashSet(LocalDate.now().minusDays(5)));

        assertThat(starDates)
                .containsEntry(1, Sets.newHashSet(LocalDate.now().minusDays(10)))
                .containsEntry(2, Sets.newHashSet(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5)))
                .containsEntry(3, Sets.newHashSet(LocalDate.now().minusDays(5)));
    }

    @Test
    public void getLastDate() {
        final Set<DrawInformation> drawInformationSet = createDrawInformation();
        final Map<Integer, LocalDate> ballLastDate = this.computeStatisticService.getBallLastDate(drawInformationSet);
        final Map<Integer, LocalDate> starLastDate = this.computeStatisticService.getStarLastDate(drawInformationSet);
        assertThat(ballLastDate)
                .containsOnlyKeys(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(starLastDate)
                .containsOnlyKeys(1, 2, 3);

        assertThat(ballLastDate)
                .containsEntry(1, LocalDate.now().minusDays(10))
                .containsEntry(2, LocalDate.now().minusDays(10))
                .containsEntry(3, LocalDate.now().minusDays(10))
                .containsEntry(4, LocalDate.now().minusDays(10))
                .containsEntry(5, LocalDate.now().minusDays(5))
                .containsEntry(6, LocalDate.now().minusDays(5))
                .containsEntry(7, LocalDate.now().minusDays(5))
                .containsEntry(8, LocalDate.now().minusDays(5))
                .containsEntry(9, LocalDate.now().minusDays(5));

        assertThat(starLastDate)
                .containsEntry(1, LocalDate.now().minusDays(10))
                .containsEntry(2, LocalDate.now().minusDays(5))
                .containsEntry(3, LocalDate.now().minusDays(5));
    }

    private static Set<DrawInformation> createDrawInformation() {
        final Set<DrawInformation> drawInformationSet = new HashSet<>();
        drawInformationSet.add(createDrawInformation(
                LocalDate.now().minusDays(10),
                new int[]{1, 2, 3, 4, 5},
                new int[]{1, 2},
                "0000001",
                1));
        drawInformationSet.add(createDrawInformation(
                LocalDate.now().minusDays(5),
                new int[]{5, 6, 7, 8, 9},
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