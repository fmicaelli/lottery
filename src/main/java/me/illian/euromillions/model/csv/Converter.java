package me.illian.euromillions.model.csv;

import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static DrawInformation convertFromDataToDrawInformation(final Data data) {
        final DrawInformation.DrawInformationBuilder builder = DrawInformation.builder();
        builder.drawNumberInTheYear(data.getYearDrawNumber());

        builder.drawDate(LocalDate.parse(data.getDrawDate(), dateTimeFormatter));

        builder.numberInTheCycle(NumberUtils.toInt(data.getNumberInCycle(), -1));

        final Draw.DrawBuilder drawBuilder = Draw.builder();
        drawBuilder.balls(
                new int[] {
                        data.getBall1(),
                        data.getBall2(),
                        data.getBall3(),
                        data.getBall4(),
                        data.getBall5(),
                }
        );
        drawBuilder.stars(
                new int[] {
                        data.getStar1(),
                        data.getStar2()
                }
        );
        builder.draw(drawBuilder.build());

        return builder.build();
    }
}
