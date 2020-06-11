package Generator;

import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {
    List<List<Character>> result = new ArrayList<>();
    int pageWidth;
    int pageHeight;
    List<ColumnSettings> columnSettingsList;
    List<DataRow> dataRows;
    String header;
    String delimeter;
    String pageDelimeter = "~" + System.lineSeparator();

    public Generator(List<ColumnSettings> list, PageSettings pageSettings, List<DataRow> dataRows) {
        columnSettingsList = list;
        this.dataRows = dataRows;
        pageWidth = pageSettings.getWidth();
        pageHeight = pageSettings.getHeight();

    }
//todo delimeter

    public String generate() {
        createHeader();
        createDelimeter();
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(delimeter);
        int pageCount = pageHeight - 2;
        for (int i = 0; i < dataRows.size(); i++) {
            int countIsOk = columnSettingsList.size();
            while (countIsOk > 0) {
                if (pageCount == 0) {
                    sb.append(pageDelimeter).append(header).append(delimeter);
                    pageCount = pageHeight - 2;
                }
                List<Integer> remainingSpaceList = new ArrayList<>();
                for (int g = 0; g < columnSettingsList.size(); g++) {
                    remainingSpaceList.add(columnSettingsList.get(g).getWidth());
                }
                for (int j = 0; j < columnSettingsList.size(); j++) {
                    sb.append("| ");
                    if (dataRows.get(i).getDataList().get(j).isEmpty()) {
                        for (int k = 0; k < columnSettingsList.get(j).getWidth(); k++) {
                            sb.append(" ");
                        }
                        sb.append(" ");
                        continue;
                    }
                    if (dataRows.get(i).getDataList().get(j).length() <= remainingSpaceList.get(j)) {
                        sb.append(dataRows.get(i).getDataList().get(j));
                        for (int k = 0; k < remainingSpaceList.get(j) - dataRows.get(i).getDataList().get(j).length(); k++) {
                            sb.append(" ");
                        }
                        dataRows.get(i).getDataList().set(j, "");
                        countIsOk--;
                    } else {
                        while (remainingSpaceList.get(j) != 0) {
                            Pattern pattern = Pattern.compile("[^a-z0-9а-яА-ЯA-Z]");
                            System.out.println("text:" + dataRows.get(i).getDataList().get(j));
                            if (dataRows.get(i).getDataList().get(j).startsWith(" ") && remainingSpaceList.get(j) == columnSettingsList.get(j).getWidth()) {
                                dataRows.get(i).getDataList().set(j, dataRows.get(i).getDataList().get(j).substring(1));
                            }
                            Matcher matcher = pattern.matcher(dataRows.get(i).getDataList().get(j));
                            if (matcher.find()) {
                                System.out.println("start sub:" + dataRows.get(i).getDataList().get(j).substring(0, matcher.end()));
                                String startSubstring = dataRows.get(i).getDataList().get(j).substring(0, matcher.end());
                                String endSubstring = dataRows.get(i).getDataList().get(j).substring(matcher.end());
                                System.out.println("end sub:" + dataRows.get(i).getDataList().get(j).substring(matcher.end()));
                                if (startSubstring.length() <= remainingSpaceList.get(j)) {
                                    sb.append(startSubstring);
                                    int remaining = remainingSpaceList.get(j) - startSubstring.length();
                                    remainingSpaceList.set(j, remaining);
                                    dataRows.get(i).getDataList().set(j, endSubstring);
                                    continue;
                                } else {
                                    if (startSubstring.endsWith(" ")) {
                                        startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                        if (startSubstring.length() <= remainingSpaceList.get(j)) {
                                            sb.append(startSubstring);
                                            int remaining = remainingSpaceList.get(j) - startSubstring.length();
                                            remainingSpaceList.set(j, remaining);
                                            dataRows.get(i).getDataList().set(j, endSubstring);
                                            continue;
                                        }
                                    }
                                }
                            }
                            if (remainingSpaceList.get(j) == columnSettingsList.get(j).getWidth()) {
                                String firstHalf = dataRows.get(i).getDataList().get(j).substring(0, remainingSpaceList.get(j));
                                String secondHalf = dataRows.get(i).getDataList().get(j).substring(remainingSpaceList.get(j));
                                sb.append(firstHalf);
                                dataRows.get(i).getDataList().set(j, secondHalf);
                                remainingSpaceList.set(j, 0);
                            } else {
                                for (int k = 0; k < remainingSpaceList.get(j); k++) {
                                    sb.append(" ");
                                }
                                remainingSpaceList.set(j, 0);
                            }

                        }
                    }
                    sb.append(" ");
                }
                sb.append("|").append(System.lineSeparator());
                pageCount--;
            }
            pageCount--;
            if (pageCount > 0) {
                sb.append(delimeter);
            }
        }
        return sb.toString();
    }


    private void createHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        int countIsOk = columnSettingsList.size();
        while (countIsOk > 0) {
            for (int i = 0; i < columnSettingsList.size(); i++) {
                stringBuilder.append("| ");
                if (columnSettingsList.get(i).getTitle().length() <= columnSettingsList.get(i).getWidth()) {
                    stringBuilder.append(columnSettingsList.get(i).getTitle());
                    for (int j = 0; j < columnSettingsList.get(i).getWidth() - columnSettingsList.get(i).getTitle().length(); j++) {
                        stringBuilder.append(" ");
                    }
                } else {



                }
                stringBuilder.append(" ");
            }
        }
        stringBuilder.append("|").append(System.lineSeparator());
        header = stringBuilder.toString();
    }

    private void createDelimeter() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pageWidth; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(System.lineSeparator());
        delimeter = stringBuilder.toString();
    }
}
