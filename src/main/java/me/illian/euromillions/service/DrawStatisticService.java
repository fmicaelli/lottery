package me.illian.euromillions.service;

import com.google.common.base.Suppliers;
import me.illian.euromillions.model.DrawInformation;
import me.illian.euromillions.repository.DrawInformationRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class DrawStatisticService {

    private final Supplier<Set<DrawInformation>> supplier;

    private final ComputeStatisticService computeStatisticService;

    @Autowired
    public DrawStatisticService(final DrawInformationRepository drawInformationRepository,
                                final ComputeStatisticService computeStatisticService) {
        this.supplier = Suppliers.memoizeWithExpiration(drawInformationRepository::getDrawInformation,
                1, TimeUnit.HOURS);
        this.computeStatisticService = computeStatisticService;
    }

    public Map<Integer, Set<LocalDate>> getBallDates() {
        return this.computeStatisticService.getBallDates(this.supplier.get());
    }

    public Map<Integer, Set<LocalDate>> getStarDates() {
        return this.computeStatisticService.getStarDates(this.supplier.get());
    }

    public Map<Integer, LocalDate> getBallLastDate() {
        return this.computeStatisticService.getBallLastDate(this.supplier.get());
    }

    public Map<Integer, LocalDate> getStarLastDate() {
        return this.computeStatisticService.getStarLastDate(this.supplier.get());
    }

    public Map<Pair<Integer, Integer>, Integer> getBallPairOccurrence() {
        return this.computeStatisticService.getBallPairOccurrence(this.supplier.get());
    }

    public Map<Pair<Integer, Integer>, Integer> getStarPairOccurrence() {
        return this.computeStatisticService.getStarPairOccurrence(this.supplier.get());
    }

    public int getTotalDrawCount() {
        return this.computeStatisticService.getTotalDrawCount(this.supplier.get());
    }

    public Map<Integer, Integer> getBallOccurrence() {
        return this.computeStatisticService.getBallOccurrence(this.supplier.get());
    }

    public Map<Integer, Integer> getStarOccurrence() {
        return this.computeStatisticService.getStarOccurrence(this.supplier.get());
    }
}
