package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataRow {
    private List<String> dataList;

    public DataRow() {
        this.dataList = new ArrayList<>();
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "DataRow{" +
                "dataList=" + dataList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataRow)) return false;
        DataRow dataRow = (DataRow) o;
        return getDataList().equals(dataRow.getDataList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataList());
    }
}
