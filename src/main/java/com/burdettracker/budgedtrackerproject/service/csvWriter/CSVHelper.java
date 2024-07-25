package com.burdettracker.budgedtrackerproject.service.csvWriter;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CSVHelper {

    public static void writeDataToCSV(Writer writer, List<String[]> data) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(data);
        }
    }

}
