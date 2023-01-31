package views;

import Entities.Enums.Direction;
import controllers.Controller;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    private Integer testInt;
    private Controller controller;

    public KeyListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.controller.moveGun(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                this.controller.moveGun(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                this.controller.shootBullet();
                break;
            case KeyEvent.VK_ENTER:
                this.controller.saveGame();
                break;
            case KeyEvent.VK_ESCAPE:
                this.controller.exitGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
