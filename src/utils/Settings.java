package utils;

import java.io.Serializable;

public final class Settings implements Serializable {
    static public final int ENEMY_WIDTH = 40;
    static public final int ENEMY_HEIGHT = 40;

    static public final int GUN_WIDTH = 40;
    static public final int GUN_HEIGHT = 50;
    static public final int GUN_HEALTH = 3;

    static public final int BULLET_WIDTH = 15;
    static public final int BULLET_HEIGHT = 15;
    static public final int BULLET_SHIFT = 5;

    static public final int GAME_FIELD_WIDTH = 900;
    static public final int GAME_FIELD_HEIGHT = 700;

    static public final int HEART_WIDTH = 20;
    static public final int HEART_HEIGHT = 20;

    private Settings() {}
}
