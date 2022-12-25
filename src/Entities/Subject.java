package Entities;

import model.GameModel;

import java.io.Serial;
import java.io.Serializable;

public abstract class Subject implements Runnable, Serializable {
    @Serial
    private static final long serialVersionUID = 9303920;
    private Point point;
    private GameModel gameModel;
    private Size size;
    private boolean isStopped;

    public Subject(Point point, GameModel gameModel, Size size) {
        this.point = point;
        this.gameModel = gameModel;
        this.size = size;
    }

    public Point getPoint() {
        return point;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public Size getSize() {
        return size;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    protected void removeSubjectFromModel() {
        this.getGameModel().getSubjectsDispatcher().removeElement(this);
        this.getGameModel().notifyListeners();

        this.setStopped(true);
    }

    protected void setNewPosition(int newPosition) {
        this.getPoint().setY(newPosition);
        this.getGameModel().getSubjectsDispatcher().removeCollisionElements(this, this.getGameModel());
        this.getGameModel().notifyListeners();
    }
}
