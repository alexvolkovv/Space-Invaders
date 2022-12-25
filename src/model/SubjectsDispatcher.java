package model;

import Entities.Bullet;
import Entities.Subject;
import Entities.Enemy;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectsDispatcher implements Serializable {
    @Serial
    private static final long serialVersionUID = 12828128;
    private final ArrayList<Subject> allElements;

    public SubjectsDispatcher(ArrayList<Subject> elements) {
        this.allElements = elements;
    }

    public synchronized void addElement(Subject subject) {
        this.allElements.add(subject);
    }

    public synchronized void removeElement(Subject element) {
        this.allElements.remove(element);
    }

    public synchronized List<Subject> getElements() {
        return this.allElements;
    }

    public synchronized void clear() {
        for (Subject sub :
                this.allElements) {
            sub.setStopped(true);
        }

        this.allElements.clear();
    }

    public synchronized List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<>();

        for (Subject element: this.allElements) {
            if (element.getClass() == Enemy.class) {
                enemies.add((Enemy) element);
            }
        }

        return enemies;
    }

    public synchronized List<Bullet> getBullets() {
        List<Bullet> enemies = new ArrayList<>();

        for (Subject element: this.allElements) {
            if (element.getClass() == Bullet.class) {
                enemies.add((Bullet) element);
            }
        }

        return enemies;
    }

    public synchronized boolean isCollision(Subject firstElement, Subject secondElement) {
        int enemyStartX = firstElement.getPoint().getX();
        int enemyEndX = firstElement.getPoint().getX() + firstElement.getSize().getWidth();
        int enemyStartY = firstElement.getPoint().getY();
        int enemyEndY = firstElement.getPoint().getY() + firstElement.getSize().getHeight();

        boolean isX = enemyStartX <= secondElement.getPoint().getX()
                && enemyEndX >= secondElement.getPoint().getX();
        boolean isY = enemyStartY <= secondElement.getPoint().getY()
                && enemyEndY >= secondElement.getPoint().getY();

        return isX && isY;
    }

    public synchronized void removeCollisionElements(Subject element, GameModel gameModel) {
        Iterator<Subject> iterator = this.allElements.iterator();

        while(iterator.hasNext()) {
            Subject currentItem = iterator.next();

            if (element.getClass() != currentItem.getClass() && this.isCollision(element, currentItem)) {
                currentItem.setStopped(true);
                iterator.remove();

                element.setStopped(true);
                this.allElements.remove(element);

                gameModel.setScore(gameModel.getScore() + 1);

                return;
            }
        }
    }

}
