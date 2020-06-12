import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;
import Builder.ReportBuilder;
import Readers.DataReader;
import Readers.SettingsReader;
import Writers.TxtWriter;

import java.util.List;

public class Generator {
    public static void main(String[] args) {
        SettingsReader settingsReader = new SettingsReader();
        settingsReader.parse(args[0]);
        List<ColumnSettings> columnsSettings = settingsReader.getListColumnSettings();
        PageSettings pageSettings = settingsReader.getPageSettings();
        DataReader dataReader = new DataReader();
        List<DataRow> dataRows = dataReader.parse(args[1]);
        ReportBuilder builder = new ReportBuilder(columnsSettings, pageSettings, dataRows);
        String result = builder.build();
        TxtWriter txtWriter = new TxtWriter(args[2]);
        txtWriter.write(result);
    }
}
