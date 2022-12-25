package controllers;

import Entities.Enums.Direction;
import Listeners.Listener;
import model.GameModel;

public class Controller {
    private GameModel gameModel;
    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void moveGun(Direction direction) {
        if (direction == Direction.DOWN || direction == Direction.UP) return;

        this.gameModel.moveGun(direction);
    }

    public void shootBullet() {
        this.gameModel.shootBullet();
    }

    public void addListener(Listener listener) {
        this.gameModel.addListener(listener);
    }

    public void generateNewEnemies(int count) {
        this.gameModel.generateNewEnemies(count);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void startGame(Boolean value) {
        this.gameModel.setGameStarting(value);
    }
    public void newGame() {
        this.gameModel.startGame();
    }
    public void saveGame() {
        if (this.getGameModel().isGameStarting()) {
            this.gameModel.setGameSaved(true);
            this.gameModel.saveCurrentGameState();
        }
    }
    public void loadGame() {
        this.gameModel.loadGame();
    }
    public void exitGame() {
        this.gameModel.exitGame();
    }
    public void setSavedGame(boolean value) {
        this.gameModel.setGameSaved(value);
    }
}
