package views;

import Entities.Bullet;
import Entities.Enemy;
import controllers.Controller;
import utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ContentPanel extends JPanel {
    private final String GUN_IMAGE_PATH = "images/Gun.png";
    private final String ENEMY_IMAGE_PATH = "images/Enemy.png";
    private final String BULLET_IMAGE_PATH = "images/Bullet.png";
    private final String HEART_IMAGE_PATH = "images/Heart.png";

    private Image gunImage;
    private Image enemyImage;
    private Image bulletImage;
    private Image heartImage;
    private Timer newEnemiesTimer;
    private Timer showingSavedTextTimer;
    private final JPanel loosePanel = new JPanel();
    private final JPanel mainMenuPanel = new JPanel();
    private final JLabel savedText = new JLabel("Сохранено");
    private final Controller controller;
    private KeyListener keyListener;

    public ContentPanel(Controller controller) {
        this.controller = controller;
        this.keyListener = new KeyListener(controller);

        this.addKeyListener(this.keyListener);
        this.initializePanel();
        this.initImages();
        this.initTimers();
        this.initSavedText();
        this.initLoosePanel();
        this.initMainMenu();

        setFocusable(true);
        requestFocus();
    }

    public void initSavedText() {
        this.savedText.setBounds(Settings.GAME_FIELD_WIDTH - 100, Settings.GAME_FIELD_HEIGHT - 100, 75, 50);
        this.savedText.setForeground(Color.WHITE);
        this.savedText.setVisible(true);
    }

    public void initTimers() {
        this.newEnemiesTimer = new Timer(6000, e -> {
            Random random = new Random();

            controller.generateNewEnemies(random.nextInt(6,8));
        });

        this.showingSavedTextTimer = new Timer(1000, e -> {
            this.remove(this.savedText);
            savedText.setVisible(false);
            this.controller.setSavedGame(false);

            this.showingSavedTextTimer.stop();
        });
    }

    public void initImages() {
        this.gunImage = new ImageIcon(this.GUN_IMAGE_PATH).getImage().getScaledInstance(Settings.GUN_WIDTH, Settings.GUN_HEIGHT, Image.SCALE_DEFAULT);
        this.enemyImage = new ImageIcon(this.ENEMY_IMAGE_PATH).getImage().getScaledInstance(Settings.ENEMY_WIDTH, Settings.ENEMY_HEIGHT, Image.SCALE_DEFAULT);
        this.bulletImage = new ImageIcon(this.BULLET_IMAGE_PATH).getImage().getScaledInstance(Settings.BULLET_WIDTH, Settings.BULLET_HEIGHT, Image.SCALE_DEFAULT);
        this.heartImage = new ImageIcon(this.HEART_IMAGE_PATH).getImage().getScaledInstance(Settings.HEART_WIDTH, Settings.HEART_HEIGHT, Image.SCALE_DEFAULT);
    }

    private void paintGun(Graphics g) {
        g.drawImage(this.gunImage,
                this.controller.getGameModel().getGun().getPoint().getX(),
                this.controller.getGameModel().getGun().getPoint().getY(), this);
    }

    private void paintEnemies(Graphics g) {
        for (Enemy enemy : this.controller.getGameModel().getSubjectsDispatcher().getEnemies()) {
            g.drawImage(this.enemyImage, enemy.getPoint().getX(), enemy.getPoint().getY(), this);
        }
    }

    private void paintBullets(Graphics g) {
        for (Bullet bullet : this.controller.getGameModel().getSubjectsDispatcher().getBullets()) {
            g.drawImage(this.bulletImage, bullet.getPoint().getX(), bullet.getPoint().getY(), this);
        }
    }

    private void paintScore(Graphics g) {
        StringBuilder scoreStringBuilder = new StringBuilder("Счет: ");
        scoreStringBuilder.append(this.controller.getGameModel().getScore());

        g.setColor(Color.WHITE);
        g.drawString(scoreStringBuilder.toString(), 50,  Settings.GAME_FIELD_HEIGHT - 50);
    }

    private void paintHeart(Graphics g) {
        int paddingBottom = 60;
        int paddingRight = 100;

        for (int i = 0; i < this.controller.getGameModel().getGun().getHealth(); i++) {
            g.drawImage(this.heartImage, Settings.GAME_FIELD_WIDTH - paddingRight + (i * Settings.HEART_WIDTH), Settings.GAME_FIELD_HEIGHT - paddingBottom, this);
        }
    }

    private void initializePanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    private void initLoosePanel() {
        JLabel label = new JLabel("ТЫ ПРОИГРАЛ :(");
        int labelWidth = 200;
        int labelHeight = 100;
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        JButton button = new JButton("В главное меню");

        label.setForeground(Color.WHITE);

        label.setBackground(Color.BLACK);
        label.setFont(labelFont);

        button.setBackground(Color.BLACK);
        button.setFont(labelFont);

        label.setBounds(Settings.GAME_FIELD_WIDTH / 2 - (labelWidth / 2), Settings.GAME_FIELD_HEIGHT / 2 - (labelHeight / 2), labelWidth,labelHeight);
        button.setBounds(Settings.GAME_FIELD_WIDTH / 2 - 150, Settings.GAME_FIELD_HEIGHT / 2 - (labelHeight / 2) + 100, 250,50);

        button.addActionListener(e -> {
            controller.startGame(false);

            repaint();
        });

        this.loosePanel.add(label);
        this.loosePanel.add(button);

        this.loosePanel.setBounds(0, 0, Settings.GAME_FIELD_WIDTH, Settings.GAME_FIELD_HEIGHT);
        this.loosePanel.setBackground(Color.BLACK);
    }


    private void paintGame(Graphics g) {
        if (!this.newEnemiesTimer.isRunning()) {
            this.newEnemiesTimer.start();
        }

        this.paintGun(g);
        this.paintEnemies(g);
        this.paintBullets(g);
        this.paintHeart(g);
        this.paintScore(g);

        if (this.controller.getGameModel().isGameSaved()) {
            this.add(this.savedText);
            this.savedText.setVisible(true);
            this.showingSavedTextTimer.start();
        }
    }

    private void initMainMenu() {
        JButton loadGameButton = new JButton("Загрузить игру");
        JLabel gameName = new JLabel("SUPER SPACE INVADERS GAME 2022");
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        JButton newGameButton = new JButton("Новая игра");

        this.mainMenuPanel.setBounds(0, 0, Settings.GAME_FIELD_WIDTH, Settings.GAME_FIELD_HEIGHT);
        this.mainMenuPanel.setBackground(Color.black);
        this.mainMenuPanel.setLayout(null);

        gameName.setForeground(Color.WHITE);

        gameName.setBounds(Settings.GAME_FIELD_WIDTH / 2 - 250, 50, 600, 200);
        gameName.setBackground(Color.white);
        gameName.setFont(labelFont);

        newGameButton.setBounds(Settings.GAME_FIELD_WIDTH / 2 - 200, 200, 150, 50);
        loadGameButton.setBounds(Settings.GAME_FIELD_WIDTH / 2 - 200, 300, 150, 50);

        newGameButton.addActionListener(e -> {
            removeAll();
            controller.newGame();
            repaint();
        });

        loadGameButton.addActionListener(e -> {
            removeAll();
            revalidate();
            controller.loadGame();
            newEnemiesTimer.start();
            repaint();

        });

        this.mainMenuPanel.add(newGameButton);
        this.mainMenuPanel.add(loadGameButton);
        this.mainMenuPanel.add(gameName);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!this.controller.getGameModel().isGameStarting()) {
            this.remove(this.loosePanel);
            this.add(this.mainMenuPanel);
            this.newEnemiesTimer.stop();

            return;
        }

        if (this.controller.getGameModel().isGameOver() && this.controller.getGameModel().isGameStarting()) {
            this.newEnemiesTimer.stop();

            this.add(this.loosePanel);
        } else {
            this.paintGame(g);
        }
    }
}
