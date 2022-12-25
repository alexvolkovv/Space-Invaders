package Entities;

import Entities.Enums.Direction;
import model.GameModel;
import utils.Settings;

import java.io.Serial;
import java.io.Serializable;

public class Bullet extends Subject implements Serializable {
    @Serial
    private static final long serialVersionUID = 4040404;
    private Direction direction = Direction.UP;

    public Bullet(Point point, GameModel gameModel, Size size) {
        super(point, gameModel, size);
    }

    @Override
    public void run() {
        while(!this.isStopped()) {
            Size gameFieldSize = this.getGameModel().getGameField().getSize();
            int newPosition = this.getPoint().getY() - Settings.BULLET_SHIFT;

            if (newPosition >= gameFieldSize.getHeight()) {
                this.removeSubjectFromModel();
            }

            this.setNewPosition(newPosition);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
