package Entity;

public class PageSettings {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "PageSettings{" +
                "width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
