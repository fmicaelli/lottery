package me.illian.euromillions.scheduler;

import me.illian.euromillions.model.DrawInformation;
import me.illian.euromillions.repository.DrawInformationRepository;
import me.illian.euromillions.service.GetDrawsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class UpdateDatabaseScheduler {

    private final GetDrawsService getDrawsService;
    private final DrawInformationRepository drawInformationRepository;

    @Value(value = "${euromillions.data.url}")
    private String url;

    @Value(value = "${euromillions.data.filename}")
    private String filename;

    @Autowired
    public UpdateDatabaseScheduler(final GetDrawsService getDrawsService,
                                   final DrawInformationRepository drawInformationRepository) {
        this.getDrawsService = getDrawsService;
        this.drawInformationRepository = drawInformationRepository;
    }

    @Scheduled(cron = "0 0 23 * * TUE,FRI")
    public void execute() throws IOException {
        final Set<DrawInformation> drawInformationList = this.getDrawsService.getDraws(this.url, this.filename);
        this.drawInformationRepository.insertDraw(drawInformationList);
    }
}
