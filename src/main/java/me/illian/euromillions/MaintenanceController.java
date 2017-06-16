package me.illian.euromillions;

import me.illian.euromillions.scheduler.UpdateDatabaseScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MaintenanceController {

    private final UpdateDatabaseScheduler updateDatabaseScheduler;

    @Autowired
    public MaintenanceController(final UpdateDatabaseScheduler scheduler) {
        this.updateDatabaseScheduler = scheduler;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update() throws IOException {
        this.updateDatabaseScheduler.execute();
        return "done";
    }
}
