package Entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnSettings)) return false;
        ColumnSettings that = (ColumnSettings) o;
        return getWidth() == that.getWidth() &&
                getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getWidth());
    }
}
