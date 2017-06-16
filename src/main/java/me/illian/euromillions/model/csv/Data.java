package me.illian.euromillions.model.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Data {
    public Data() {}

    @CsvBindByPosition(position = 0)
    private String yearDrawNumber;
    @CsvBindByPosition(position = 2)
    private String drawDate;
    @CsvBindByPosition(position = 3)
    private String numberInCycle;

    @CsvBindByPosition(position = 5)
    private int ball1;
    @CsvBindByPosition(position = 6)
    private int ball2;
    @CsvBindByPosition(position = 7)
    private int ball3;
    @CsvBindByPosition(position = 8)
    private int ball4;
    @CsvBindByPosition(position = 9)
    private int ball5;

    @CsvBindByPosition(position = 10)
    private int star1;
    @CsvBindByPosition(position = 11)
    private int star2;
}
