package Entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Size implements Serializable {
    @Serial
    private static final long serialVersionUID = 1010201;
    private int width;
    private int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

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
}
