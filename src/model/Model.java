package model;

import Entities.Enemy;
import Entities.Enums.Direction;

import java.io.Serial;
import java.io.Serializable;

public interface Model extends Serializable {
    void moveGun(Direction direction);
    void shootBullet();
    void generateNewEnemy();
}
