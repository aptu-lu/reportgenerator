import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;
import Builder.ReportBuilder;
import Readers.DataReader;
import Readers.SettingsReader;
import Writers.TxtWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generator {
    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        SettingsReader settingsReader = new SettingsReader();
        settingsReader.parse(arguments.get(0));
        List<ColumnSettings> columnsSettings = settingsReader.getListColumnSettings();
        PageSettings pageSettings = settingsReader.getPageSettings();
        DataReader dataReader = new DataReader();
        List<DataRow> dataRows = dataReader.parse(arguments.get(1));
        ReportBuilder builder = new ReportBuilder(columnsSettings,pageSettings,dataRows);
        String result = builder.build();
        TxtWriter txtWriter = new TxtWriter(arguments.get(2));
        txtWriter.write(result);
        System.out.println(result);
    }
}
