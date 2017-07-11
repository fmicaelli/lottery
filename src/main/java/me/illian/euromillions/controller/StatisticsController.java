package me.illian.euromillions.controller;

import me.illian.euromillions.model.Draw;
import me.illian.euromillions.service.DrawStatisticService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final DrawStatisticService drawStatisticService;

    @Autowired
    public StatisticsController(final DrawStatisticService service) {
        this.drawStatisticService = service;
    }

    @RequestMapping("/draws")
    public Map<LocalDate, Draw> getDraws() {
        return this.drawStatisticService.getDraws();
    }

    @RequestMapping("/ballDates")
    public Map<Integer, Set<LocalDate>> getBallDates() {
        return this.drawStatisticService.getBallDates();
    }

    @RequestMapping("/starDates")
    public Map<Integer, Set<LocalDate>> getStarDates() {
        return this.drawStatisticService.getStarDates();
    }

    @RequestMapping("/ballPairDates")
    public Map<Pair<Integer, Integer>, Set<LocalDate>> getBallPairDates() {
        return this.drawStatisticService.getBallPairDates();
    }

    @RequestMapping("/starPairDates")
    public Map<Pair<Integer, Integer>, Set<LocalDate>> getStarPairDates() {
        return this.drawStatisticService.getStarPairDates();
    }

    @RequestMapping("/ballGap")
    public Map<Integer, Integer> getBallGap() {
        return this.drawStatisticService.getBallGap();
    }

    @RequestMapping("/starGap")
    public Map<Integer, Integer> getStarGap() {
        return this.drawStatisticService.getStarGap();
    }

    @RequestMapping("/count")
    public Integer getCount() {
        return this.drawStatisticService.getTotalDrawCount();
    }

    @RequestMapping("/ballDrawMean")
    public Map<LocalDate, Double> getBallDrawMean() {
        return this.drawStatisticService.getBallDrawMean();
    }

    @RequestMapping("/starDrawMean")
    public Map<LocalDate, Double> getStarDrawMean() {
        return this.drawStatisticService.getStarDrawMean();
    }

    @RequestMapping("/ballDrawsMean")
    public Double getBallDrawsMean() {
        return this.drawStatisticService.getBallDrawsMean();
    }

    @RequestMapping("/starDrawsMean")
    public Double getStarDrawsMean() {
        return this.drawStatisticService.getStarDrawsMean();
    }

    @RequestMapping("/ballPositionMean")
    public Map<Integer, Double>  getBallPositionMean() {
        return this.drawStatisticService.getBallPositionMean();
    }

    @RequestMapping("/starPositionMean")
    public Map<Integer, Double>  getStarPositionMean() {
        return this.drawStatisticService.getStarPositionMean();
    }

    @RequestMapping("/bestDraw")
    public Map<String, List<Integer>> getBestDraw() {
        return this.drawStatisticService.getBestDraw();
    }
}
