package Entities;

import java.io.Serial;
import java.io.Serializable;

public class GameField implements Serializable {
    @Serial
    private static final long serialVersionUID = 4003430;
    private Size size;

    public GameField(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
