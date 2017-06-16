package me.illian.euromillions.service;

import me.illian.euromillions.model.DrawInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Service
public class GetDrawsService {

    private final FileService fileService;

    @Autowired
    public GetDrawsService(final FileService service) {
        fileService = service;
    }

    public Set<DrawInformation> getDraws(final String url, final String filename) throws IOException {
        final File file = this.fileService.downloadAndExtract(url, filename);
        return this.fileService.extractData(file);
    }

}
