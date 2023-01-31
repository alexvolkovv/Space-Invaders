package views;

import Listeners.Listener;
import controllers.Controller;
import utils.Settings;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements Listener {
    private Controller controller;
    private ContentPanel contentPanel;
    private KeyListener keyListener;

    public View(Controller controller) {
        this.controller = controller;
        this.controller.addListener(this);
        this.contentPanel = new ContentPanel(controller);
        this.keyListener = new KeyListener(controller);

//        this.initializeFrame();
    }

    private void initializeFrame() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Settings.GAME_FIELD_WIDTH, Settings.GAME_FIELD_HEIGHT);
        this.setLocation(350, 75);
        this.setTitle("Space Invaders");
        this.setResizable(false);
        this.addKeyListener(this.keyListener);
        this.setContentPane(this.contentPanel);
        this.setBackground(Color.BLACK);
    }

    @Override
    public void handle() {
        this.repaint();
    }
}
