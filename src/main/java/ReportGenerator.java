import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;
import Generator.Generator;
import Readers.DataReader;
import Readers.SettingsReader;

import java.util.List;

public class ReportGenerator {
    public static void main(String[] args) {
        SettingsReader settingsReader = new SettingsReader();
        settingsReader.parse();
        List<ColumnSettings> columnsSettings = settingsReader.getListColumnSettings();
//        for (ColumnSettings columnSettings :
//                columnsSettings) {
//            System.out.println(columnSettings);
//        }
        PageSettings pageSettings = settingsReader.getPageSettings();
//        System.out.println(pageSettings);
        DataReader dataReader = new DataReader();
        List<DataRow> dataRows = dataReader.parse();
//        for (DataRow dataRow :
//                dataRows) {
//            System.out.println(dataRow);
//        }

        Generator generator = new Generator(columnsSettings,pageSettings,dataRows);
        String result = generator.generate();
        System.out.println(result);
    }
}
