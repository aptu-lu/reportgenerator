package Builder;

import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportBuilder {
    private List<ColumnSettings> columnSettingsList;
    private List<DataRow> dataRows;
    private String header;
    private String delimiter;
    private String pageDelimiter = "~" + System.lineSeparator();
    private int pageWidth;
    private int pageHeight;
    private int pageCount;
    private int headerHeight;
    StringBuilder sb;

    public ReportBuilder(List<ColumnSettings> list, PageSettings pageSettings, List<DataRow> dataRows) {
        columnSettingsList = list;
        this.dataRows = dataRows;
        pageWidth = pageSettings.getWidth();
        pageHeight = pageSettings.getHeight();
    }

    public String build() {
        sb = new StringBuilder();
        createHeader();
        createDelimiter();
        sb.append(delimiter);
        pageCount--;
        for (int i = 0; i < dataRows.size(); i++) {
            createRow(dataRows.get(i).getDataList());
            if (pageCount > 0) {
                sb.append(delimiter);
                pageCount--;
            }
        }
        return sb.toString();
    }

    private int createHeader() {
        pageCount = pageHeight;
        List<String> titleList = new ArrayList<>();
        for (ColumnSettings cs :
                columnSettingsList) {
            titleList.add(cs.getTitle());
        }
        headerHeight = createRow(titleList);
        header = sb.toString();
        return headerHeight;
    }

    private int createRow(List<String> dataCell) {
        int heightRow = 0;
        int countIsOk = columnSettingsList.size();
        while (countIsOk > 0) {
            heightRow++;
            if (pageCount == 0) {
                sb.append(pageDelimiter).append(header).append(delimiter);
                pageCount = pageHeight-headerHeight -1;
            }
            for (int j = 0; j < columnSettingsList.size(); j++) {
                int remainingSpace = columnSettingsList.get(j).getWidth();
                int maxWidthColumn = columnSettingsList.get(j).getWidth();
                sb.append("| ");
                if (dataCell.get(j).isEmpty()) {
                    for (int k = 0; k < maxWidthColumn; k++) {
                        sb.append(" ");
                    }
                    sb.append(" ");
                    continue;
                }
                if (dataCell.get(j).length() <= remainingSpace) {
                    if (dataCell.get(j).startsWith(" ") && remainingSpace == maxWidthColumn) {
                        dataCell.set(j, dataCell.get(j).substring(1));
                    }
                    sb.append(dataCell.get(j));
                    for (int k = 0; k < remainingSpace - dataCell.get(j).length(); k++) {
                        sb.append(" ");
                    }
                    dataCell.set(j, "");
                    countIsOk--;
                } else {
                    while (remainingSpace != 0) {
                        Pattern pattern = Pattern.compile("[^0-9а-яА-Яa-zA-Z]");
                        if (dataCell.get(j).startsWith(" ") && remainingSpace == maxWidthColumn) {
                            dataCell.set(j, dataCell.get(j).substring(1));
                        }
                        Matcher matcher = pattern.matcher(dataCell.get(j));
                        if (matcher.find()) {

                            String startSubstring = dataCell.get(j).substring(0, matcher.end());
                            String endSubstring = dataCell.get(j).substring(matcher.end());
                            if (startSubstring.length() <= remainingSpace) {
                                sb.append(startSubstring);
                                remainingSpace -= startSubstring.length();
                                dataCell.set(j, endSubstring);
                                continue;
                            }

                            if (startSubstring.endsWith(" ") && startSubstring.length() - remainingSpace == 1) {
                                startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                if (startSubstring.length() <= remainingSpace) {
                                    sb.append(startSubstring);
                                    remainingSpace -= startSubstring.length();
                                    dataCell.set(j, endSubstring);
                                    continue;
                                }
                            } else if (startSubstring.length() - remainingSpace == 1) {
                                endSubstring = startSubstring.substring(startSubstring.length() - 1) + endSubstring;
                                startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                if (startSubstring.length() <= remainingSpace) {
                                    sb.append(startSubstring);
                                    remainingSpace -= startSubstring.length();
                                    dataCell.set(j, endSubstring);
                                    continue;
                                }
                            }
                        }
                        if (remainingSpace == maxWidthColumn) {
                            String firstHalf = dataCell.get(j).substring(0, remainingSpace);
                            String secondHalf = dataCell.get(j).substring(remainingSpace);
                            sb.append(firstHalf);
                            dataCell.set(j, secondHalf);
                            remainingSpace = 0;
                        } else {
                            for (int k = 0; k < remainingSpace; k++) {
                                sb.append(" ");
                            }
                            remainingSpace = 0;
                        }
                    }
                }
                sb.append(" ");
            }
            sb.append("|").append(System.lineSeparator());
            pageCount--;
        }
        return heightRow;
    }


    private void createDelimiter() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pageWidth; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(System.lineSeparator());
        delimiter = stringBuilder.toString();
    }
}
