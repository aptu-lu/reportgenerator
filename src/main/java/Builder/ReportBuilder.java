package Builder;

import Entity.ColumnSettings;
import Entity.DataRow;
import Entity.PageSettings;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportBuilder {
    private List<ColumnSettings> columnSettingsList;
    private List<DataRow> dataRows;
    private String header;
    private String delimiter;
    private String pageDelimiter = "~" + System.lineSeparator();
    private int headerHeight;
    private int pageWidth;
    private int pageHeight;

    public ReportBuilder(List<ColumnSettings> list, PageSettings pageSettings, List<DataRow> dataRows) {
        columnSettingsList = list;
        this.dataRows = dataRows;
        pageWidth = pageSettings.getWidth();
        pageHeight = pageSettings.getHeight();
    }

    public String build() {
        createHeader();
        createDelimiter();
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(delimiter);
        int pageCount = pageHeight - headerHeight - 1;
        for (int i = 0; i < dataRows.size(); i++) {
            int countIsOk = columnSettingsList.size();
            while (countIsOk > 0) {
                if (pageCount == 0) {
                    sb.append(pageDelimiter).append(header).append(delimiter);
                    pageCount = pageHeight - headerHeight - 1;
                }
                for (int j = 0; j < columnSettingsList.size(); j++) {
                    int remainingSpace = columnSettingsList.get(j).getWidth();
                    int maxWidthColumn = columnSettingsList.get(j).getWidth();
                    sb.append("| ");
                    if (dataRows.get(i).getDataList().get(j).isEmpty()) {
                        for (int k = 0; k < maxWidthColumn; k++) {
                            sb.append(" ");
                        }
                        sb.append(" ");
                        continue;
                    }
                    if (dataRows.get(i).getDataList().get(j).length() <= remainingSpace) {
                        if (dataRows.get(i).getDataList().get(j).startsWith(" ") && remainingSpace == maxWidthColumn) {
                            dataRows.get(i).getDataList().set(j, dataRows.get(i).getDataList().get(j).substring(1));
                        }
                        sb.append(dataRows.get(i).getDataList().get(j));
                        for (int k = 0; k < remainingSpace - dataRows.get(i).getDataList().get(j).length(); k++) {
                            sb.append(" ");
                        }
                        dataRows.get(i).getDataList().set(j, "");
                        countIsOk--;
                    } else {
                        while (remainingSpace != 0) {
                            Pattern pattern = Pattern.compile("[^0-9а-яА-Яa-zA-Z]");
                            if (dataRows.get(i).getDataList().get(j).startsWith(" ") && remainingSpace == maxWidthColumn) {
                                dataRows.get(i).getDataList().set(j, dataRows.get(i).getDataList().get(j).substring(1));
                            }
                            Matcher matcher = pattern.matcher(dataRows.get(i).getDataList().get(j));
                            if (matcher.find()) {

                                String startSubstring = dataRows.get(i).getDataList().get(j).substring(0, matcher.end());
                                String endSubstring = dataRows.get(i).getDataList().get(j).substring(matcher.end());
                                if (startSubstring.length() <= remainingSpace) {
                                    sb.append(startSubstring);
                                    remainingSpace -= startSubstring.length();
                                    dataRows.get(i).getDataList().set(j, endSubstring);
                                    continue;
                                }

                                if (startSubstring.endsWith(" ") && startSubstring.length() - remainingSpace == 1) {
                                    startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                    if (startSubstring.length() <= remainingSpace) {
                                        sb.append(startSubstring);
                                        remainingSpace -= startSubstring.length();
                                        dataRows.get(i).getDataList().set(j, endSubstring);
                                        continue;
                                    }
                                } else if (startSubstring.length() - remainingSpace == 1) {
                                    endSubstring = startSubstring.substring(startSubstring.length() - 1) + endSubstring;
                                    startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                    if (startSubstring.length() <= remainingSpace) {
                                        sb.append(startSubstring);
                                        remainingSpace -= startSubstring.length();
                                        dataRows.get(i).getDataList().set(j, endSubstring);
                                        continue;
                                    }
                                }
                            }
                            if (remainingSpace == maxWidthColumn) {
                                String firstHalf = dataRows.get(i).getDataList().get(j).substring(0, remainingSpace);
                                String secondHalf = dataRows.get(i).getDataList().get(j).substring(remainingSpace);
                                sb.append(firstHalf);
                                dataRows.get(i).getDataList().set(j, secondHalf);
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
            if (pageCount > 0) {
                sb.append(delimiter);
                pageCount--;
            }
        }
        return sb.toString();
    }


    private int createHeader() {
        StringBuilder sb = new StringBuilder();
        int countIsOk = columnSettingsList.size();
        while (countIsOk > 0) {
            headerHeight++;
            for (int j = 0; j < columnSettingsList.size(); j++) {
                int remainingSpace = columnSettingsList.get(j).getWidth();
                int maxWidthColumn = columnSettingsList.get(j).getWidth();
                sb.append("| ");
                if (columnSettingsList.get(j).getTitle().isEmpty()) {
                    for (int k = 0; k < maxWidthColumn; k++) {
                        sb.append(" ");
                    }
                    sb.append(" ");
                    continue;
                }
                if (columnSettingsList.get(j).getTitle().length() <= remainingSpace) {
                    if (columnSettingsList.get(j).getTitle().startsWith(" ") && remainingSpace == maxWidthColumn) {
                        columnSettingsList.get(j).setTitle(columnSettingsList.get(j).getTitle().substring(1));
                    }
                    sb.append(columnSettingsList.get(j).getTitle());
                    for (int k = 0; k < remainingSpace - columnSettingsList.get(j).getTitle().length(); k++) {
                        sb.append(" ");
                    }
                    columnSettingsList.get(j).setTitle("");
                    countIsOk--;
                } else {
                    while (remainingSpace != 0) {
                        Pattern pattern = Pattern.compile("[^0-9а-яА-Яa-zA-Z]");
                        if (columnSettingsList.get(j).getTitle().startsWith(" ") && remainingSpace == maxWidthColumn) {
                            columnSettingsList.get(j).setTitle(columnSettingsList.get(j).getTitle().substring(1));
                        }
                        Matcher matcher = pattern.matcher(columnSettingsList.get(j).getTitle());
                        if (matcher.find()) {
                            String startSubstring = columnSettingsList.get(j).getTitle().substring(0, matcher.end());
                            String endSubstring = columnSettingsList.get(j).getTitle().substring(matcher.end());
                            if (startSubstring.length() <= remainingSpace) {
                                sb.append(startSubstring);
                                remainingSpace -= startSubstring.length();
                                columnSettingsList.get(j).setTitle(endSubstring);
                                continue;
                            }
                            if (startSubstring.endsWith(" ") && startSubstring.length() - remainingSpace == 1) {
                                startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                if (startSubstring.length() <= remainingSpace) {
                                    sb.append(startSubstring);
                                    remainingSpace -= startSubstring.length();
                                    columnSettingsList.get(j).setTitle(endSubstring);
                                    continue;
                                }
                            } else if (startSubstring.length() - remainingSpace == 1) {
                                endSubstring = startSubstring.substring(startSubstring.length() - 1) + endSubstring;
                                startSubstring = startSubstring.substring(0, startSubstring.length() - 1);
                                if (startSubstring.length() <= remainingSpace) {
                                    sb.append(startSubstring);
                                    remainingSpace -= startSubstring.length();
                                    columnSettingsList.get(j).setTitle(endSubstring);
                                    continue;
                                }
                            }
                        }
                        if (remainingSpace == maxWidthColumn) {
                            String firstHalf = columnSettingsList.get(j).getTitle().substring(0, remainingSpace);
                            String secondHalf = columnSettingsList.get(j).getTitle().substring(remainingSpace);
                            sb.append(firstHalf);
                            columnSettingsList.get(j).setTitle(secondHalf);
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
        }
        header = sb.toString();
        return headerHeight;
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
