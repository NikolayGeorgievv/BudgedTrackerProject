package com.burdettracker.budgedtrackerproject.service.csvWriter;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {


    @Test
    void writeDataToCSVWritesCorrectData() throws Exception {
        // Given
        StringWriter writer = new StringWriter();
        List<String[]> data = Arrays.asList(new String[]{"A", "B", "C"}, new String[]{"D", "E", "F"});

        // When
        CSVHelper.writeDataToCSV(writer, data);

        // Then
        String expected = "\"A\",\"B\",\"C\"\n\"D\",\"E\",\"F\"\n";
        assertEquals(expected, writer.toString());
    }

    @Test
    void writeDataToCSVHandlesEmptyData() throws Exception {
        // Given
        StringWriter writer = new StringWriter();
        List<String[]> data = Arrays.asList(new String[]{}, new String[]{});

        // When
        CSVHelper.writeDataToCSV(writer, data);

        // Then
        String expected = "\n\n";
        assertEquals(expected, writer.toString());
    }
}