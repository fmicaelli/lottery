package me.illian.euromillions.service;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import me.illian.euromillions.model.DrawInformation;
import me.illian.euromillions.repository.DrawInformationRepository;
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

    @Autowired
    public DrawStatisticService(final DrawInformationRepository drawInformationRepository) {
        this.supplier = Suppliers.memoizeWithExpiration(drawInformationRepository::getDrawInformation,
                1, TimeUnit.HOURS);
    }

    public Map<Integer, Set<LocalDate>> getBallDates() {
        final Set<DrawInformation> information = this.supplier.get();
        SetMultimap<Integer, LocalDate> result = HashMultimap.create();

        information.forEach(drawInformation -> {
            for (int ball : drawInformation.getDraw().getBalls()) {
                result.put(ball, drawInformation.getDrawDate());
            }
        });

        return Multimaps.asMap(result);
    }
}
