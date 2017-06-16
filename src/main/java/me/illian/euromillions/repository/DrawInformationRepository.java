package me.illian.euromillions.repository;

import me.illian.euromillions.jooq.Tables;
import me.illian.euromillions.jooq.tables.Euromillions;
import me.illian.euromillions.jooq.tables.records.EuromillionsRecord;
import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DrawInformationRepository {

    private final DSLContext context;

    @Autowired
    public DrawInformationRepository(final DSLContext context) {
        this.context = context;
    }

    public void insertDraw(final Set<DrawInformation> drawInformationList) {
        final Set<String> keys = this.context.select(Euromillions.EUROMILLIONS.YEAR_DRAW_NUMBER)
                .from(Tables.EUROMILLIONS)
                .fetchSet(Euromillions.EUROMILLIONS.YEAR_DRAW_NUMBER);

        this.context.batchInsert(drawInformationList.stream()
                .filter(key -> !keys.contains(key.getDrawNumberInTheYear()))
                .map(DrawInformationRepository::mapToRecord)
                .collect(Collectors.toSet()))
                .execute();
    }

    public Set<DrawInformation> getDrawInformation() {
        return this.context.selectFrom(Tables.EUROMILLIONS)
                .fetchStream()
                .map(DrawInformationRepository::mapToDrawInformation)
                .collect(Collectors.toSet());
    }

    private static EuromillionsRecord mapToRecord(final DrawInformation drawInformation) {
        return new EuromillionsRecord(
                drawInformation.getDrawNumberInTheYear(),
                Date.valueOf(drawInformation.getDrawDate()),
                drawInformation.getNumberInTheCycle(),
                drawInformation.getDraw().getBalls()[0],
                drawInformation.getDraw().getBalls()[1],
                drawInformation.getDraw().getBalls()[2],
                drawInformation.getDraw().getBalls()[3],
                drawInformation.getDraw().getBalls()[4],
                drawInformation.getDraw().getStars()[0],
                drawInformation.getDraw().getStars()[1]
        );
    }

    private static DrawInformation mapToDrawInformation(final EuromillionsRecord euromillionsRecord) {
        return DrawInformation.builder()
                .drawNumberInTheYear(euromillionsRecord.getYearDrawNumber())
                .drawDate(euromillionsRecord.getDrawDate().toLocalDate())
                .numberInTheCycle(euromillionsRecord.getNumberInCycle())
                .draw(
                        Draw.builder()
                                .balls(new int[]{
                                        euromillionsRecord.getBall_1(),
                                        euromillionsRecord.getBall_2(),
                                        euromillionsRecord.getBall_3(),
                                        euromillionsRecord.getBall_4(),
                                        euromillionsRecord.getBall_5(),
                                })
                                .stars(new int[]{
                                        euromillionsRecord.getStar_1(),
                                        euromillionsRecord.getStar_2()
                                })
                                .build()
                ).build();
    }
}
