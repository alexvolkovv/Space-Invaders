package Entities;

import model.GameModel;

import java.io.Serial;
import java.io.Serializable;

public class Enemy extends Subject implements Serializable {
    @Serial
    private static final long serialVersionUID = 6666666;
    private int speed;

    public Enemy(Size size, Point point, GameModel gameModel) {
        super(point, gameModel, size);

        this.speed = 1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!this.isStopped()) {
            if (this.getPoint().getY() >= this.getGameModel().getGameField().getSize().getHeight()) {
                this.removeSubjectFromModel();
                this.getGameModel().decreaseHealth();
            }

            int newPosition = this.getPoint().getY() + this.speed;

            this.setNewPosition(newPosition);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
