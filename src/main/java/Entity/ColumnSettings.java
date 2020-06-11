package Entity;

public class ColumnSettings {
    private String title;
    private int width;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Column{" +
                "title='" + title + '\'' +
                ", width='" + width + '\'' +
                '}';
    }
}
