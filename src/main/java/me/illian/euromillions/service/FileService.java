package me.illian.euromillions.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import me.illian.euromillions.model.DrawInformation;
import me.illian.euromillions.model.csv.Converter;
import me.illian.euromillions.model.csv.Data;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {

    File downloadAndExtract(final String url, final String filename) throws IOException {
        final File tempDirectory = Files.createTempDirectory("draw").toFile();
        final File temporaryFile = File.createTempFile("draw", ".zip", tempDirectory);
        FileUtils.copyURLToFile(new URL(url), temporaryFile);

        try {
            ZipFile zipFile = new ZipFile(temporaryFile);
            zipFile.extractAll(tempDirectory.getPath());
            return new File(tempDirectory.getPath() + File.separator + filename);
        } catch (ZipException e) {
            throw new IOException(e);
        }
    }

    Set<DrawInformation> extractData(final File file) throws IOException {
        final CSVReader reader = new CSVReader(new FileReader(file), ';', '"', 1);
        final CsvToBean<Data> csvToBean = new CsvToBean<>();

        final ColumnPositionMappingStrategy<Data> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Data.class);

        final List<Data> dataList = csvToBean.parse(strategy, reader);

        return dataList.stream().map(Converter::convertFromDataToDrawInformation)
                .collect(Collectors.toSet());
    }
}
