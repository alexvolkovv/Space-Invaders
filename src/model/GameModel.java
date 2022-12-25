package model;

import Entities.*;
import Entities.Enums.Direction;
import Listeners.Listener;
import utils.Settings;

import java.io.*;
import java.util.*;

public class GameModel implements Model, Serializable {
    private SubjectsDispatcher subjectsDispatcher;
    @Serial
    private static final long serialVersionUID = 12828128;
    private Gun gun;
    private GameField gameField;
    private final transient ArrayList<Listener> listeners = new ArrayList<>();
    private volatile int score = 0;
    private boolean isGameOver = false;
    private boolean isGameStarting = false;
    private boolean isGameSaved = false;

    public GameModel() {}

    public void startGame() {
        int paddingForHealth = 20;
        int startGunPositionX = (Settings.GAME_FIELD_WIDTH / 2) - Settings.GUN_WIDTH / 2;
        int startGunPositionY = Settings.GAME_FIELD_HEIGHT - Settings.GUN_HEIGHT * 2 - paddingForHealth;
        Point gunPoint = new Point(startGunPositionX, startGunPositionY);
        Size size = new Size(Settings.GUN_WIDTH, Settings.GUN_HEIGHT);

        this.subjectsDispatcher = new SubjectsDispatcher(new ArrayList<>());
        this.gun = new Gun(gunPoint, size, Settings.GUN_HEALTH);
        this.gameField = new GameField(new Size(Settings.GAME_FIELD_WIDTH, Settings.GAME_FIELD_HEIGHT));
        this.generateNewEnemies(7);
        this.isGameOver = false;
        this.isGameStarting = true;
        this.score = 0;

        this.notifyListeners();
    }

    public boolean isGameSaved() {
        return isGameSaved;
    }

    public void setGameSaved(boolean gameSaved) {
        isGameSaved = gameSaved;
    }

    @Override
    public void moveGun(Direction direction) {
        if (direction == Direction.DOWN || direction == Direction.UP) return;

        Size gunSize = this.gun.getSize();
        Size gameFieldSize = this.gameField.getSize();
        Point gunPoint = this.gun.getPoint();
        int shift = gunSize.getWidth() / 2;
        int newCoordinateX;

        if (direction == Direction.LEFT) {
            newCoordinateX = gunPoint.getX() - shift;

            gunPoint.setX(Math.max(newCoordinateX, 0));
        } else {
            newCoordinateX = gunPoint.getX() + shift;
            gunPoint.setX(Math.min(newCoordinateX, gameFieldSize.getWidth() - gun.getSize().getWidth() * 2));
        }

        this.notifyListeners();
    }

    @Override
    public void shootBullet() {
        int coordinateBulletX = this.gun.getPoint().getX() + this.gun.getSize().getWidth() / 2;
        int coordinateBulletY = this.gun.getPoint().getY() - this.gun.getSize().getHeight() / 2;
        Point bulletPoint = new Point(coordinateBulletX, coordinateBulletY);
        Bullet newBullet = new Bullet(bulletPoint, this, new Size(Settings.BULLET_WIDTH, Settings.BULLET_HEIGHT));
        Thread bulletThread = new Thread(newBullet);

        bulletThread.start();

        this.subjectsDispatcher.addElement(newBullet);
    }

    @Override
    public void generateNewEnemy() {
        Random random = new Random();
        int coordinateEnemyX = random.nextInt(0, this.gameField.getSize().getWidth() - Settings.ENEMY_WIDTH);

        while (coordinateEnemyX % Settings.ENEMY_WIDTH != 0) {
            coordinateEnemyX = random.nextInt(0, this.gameField.getSize().getWidth() - Settings.ENEMY_WIDTH);
        }

        int coordinateEnemyY = -Settings.ENEMY_HEIGHT;
        Size enemySize = new Size(Settings.ENEMY_WIDTH, Settings.ENEMY_HEIGHT);
        Point enemyPoint = new Point(coordinateEnemyX, coordinateEnemyY);
        Enemy enemy = new Enemy(enemySize, enemyPoint, this);
        enemy.setSpeed(random.nextInt(1, 4));
        Thread enemyThread = new Thread(enemy);

        enemyThread.start();
        this.subjectsDispatcher.addElement(enemy);

        this.notifyListeners();
    }

    public void copyModel(GameModel gameModel) {
        this.isGameStarting = gameModel.isGameStarting;
        this.isGameOver = gameModel.isGameOver;
        this.score = gameModel.getScore();
        this.subjectsDispatcher = gameModel.getSubjectsDispatcher();
        this.gun = gameModel.getGun();
        this.gameField = gameModel.getGameField();
    }

    public void loadGame() {
        try {
            FileInputStream fis = new FileInputStream("./text.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            GameModel newModel = (GameModel) ois.readObject();

            this.copyModel(newModel);

            List<Subject> allSubjects = this.subjectsDispatcher.getElements();

            for (Subject sub : allSubjects) {
                sub.setGameModel(this);

                Thread newThread = new Thread(sub);

                newThread.start();
            }

            fis.close();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveCurrentGameState() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./text.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);

            fileOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopSubjects() {
        List<Subject> elements = this.subjectsDispatcher.getElements();

        for (Subject el : elements) {
            el.setStopped(true);
        }
    }

    public void exitGame() {
        this.subjectsDispatcher.clear();
        this.isGameStarting = false;
        this.isGameSaved = false;

        this.notifyListeners();
    }

    public void decreaseHealth() {
        if (this.gun.getHealth() == 1) {
            this.isGameOver = true;
            this.stopSubjects();
        }

        this.gun.setHealth(this.gun.getHealth() - 1);
    }

    public void generateNewEnemies(int enemyCount) {
        for (int i = 0; i < enemyCount; i++) {
            this.generateNewEnemy();
        }
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void notifyListeners() {
        for (Listener listener : listeners) {
            listener.handle();
        }
    }
    public Gun getGun() {
        return gun;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameField getGameField() {
        return gameField;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public SubjectsDispatcher getSubjectsDispatcher() {
        return subjectsDispatcher;
    }

    public boolean isGameStarting() {
        return isGameStarting;
    }

    public void setGameStarting(boolean gameStarting) {
        isGameStarting = gameStarting;
    }
}
