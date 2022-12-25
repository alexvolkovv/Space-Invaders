package Entities;

import java.io.Serial;
import java.io.Serializable;

public class Gun implements Serializable {
    @Serial
    private static final long serialVersionUID = 30130103;
    private Point point;
    private Size size;
    private int health;

    public Gun(Point point, Size size, int health) {
        this.point = point;
        this.size = size;
        this.health = health;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
