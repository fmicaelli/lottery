package me.illian.euromillions.service;

import com.google.common.base.Suppliers;
import me.illian.euromillions.model.Draw;
import me.illian.euromillions.model.DrawInformation;
import me.illian.euromillions.repository.DrawInformationRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public Map<LocalDate, Draw> getDraws() {
        return this.computeStatisticService.getDraws(this.supplier.get());
    }

    public Map<Integer, Set<LocalDate>> getBallDates() {
        return this.computeStatisticService.getBallDates(this.supplier.get());
    }

    public Map<Integer, Set<LocalDate>> getStarDates() {
        return this.computeStatisticService.getStarDates(this.supplier.get());
    }

    public Map<Pair<Integer, Integer>, Set<LocalDate>> getBallPairDates() {
        return this.computeStatisticService.getBallPairDates(this.supplier.get());
    }

    public Map<Pair<Integer, Integer>, Set<LocalDate>> getStarPairDates() {
        return this.computeStatisticService.getStarPairDates(this.supplier.get());
    }

    public Map<Integer, Integer> getBallGap() {
        return this.computeStatisticService.getBallGap(this.supplier.get());
    }

    public Map<Integer, Integer> getStarGap() {
        return this.computeStatisticService.getStarGap(this.supplier.get());
    }

    public int getTotalDrawCount() {
        return this.computeStatisticService.getTotalDrawCount(this.supplier.get());
    }

    public Map<LocalDate, Double> getBallDrawMean() {
        return this.computeStatisticService.getBallDrawMean(this.supplier.get());
    }

    public Map<LocalDate, Double> getStarDrawMean() {
        return this.computeStatisticService.getStarDrawMean(this.supplier.get());
    }

    public Double getBallDrawsMean() {
        return this.computeStatisticService.getBallDrawsMean(this.supplier.get());
    }

    public Double getStarDrawsMean() {
        return this.computeStatisticService.getStarDrawsMean(this.supplier.get());
    }

    public Map<Integer, Double> getBallPositionMean() {
        return this.computeStatisticService.getBallPositionMean(this.supplier.get());
    }

    public Map<Integer, Double> getStarPositionMean() {
        return this.computeStatisticService.getStarPositionMean(this.supplier.get());
    }

    public Map<String, List<Integer>> getBestDraw() {
        return this.computeStatisticService.getBestDraw(this.supplier.get());
    }
}
