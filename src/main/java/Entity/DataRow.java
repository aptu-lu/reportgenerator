package Entity;

import java.util.ArrayList;
import java.util.List;

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

}
