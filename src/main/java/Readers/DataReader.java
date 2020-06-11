package Readers;

import Entity.DataRow;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataReader {
    List<DataRow> dataRows = new ArrayList<>();


    public List<DataRow> parse(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath), StandardCharsets.UTF_16);
            while (scanner.hasNext()) {
                DataRow dataRow = new DataRow();
                String line = scanner.nextLine();
                String[] split = line.split("\t");
                dataRow.setDataList(Arrays.asList(split));
                dataRows.add(dataRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataRows;
    }

}
