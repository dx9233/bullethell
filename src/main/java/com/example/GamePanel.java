package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private final Player player;

    private final List<Bullet> bullets;
    private final List<Enemy> enemies;
    private final List<Bullet> enemyBullets;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyHandler());

        player = new Player(WIDTH / 2, HEIGHT - 60);

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyBullets = new ArrayList<>();
    }

    public void startGame() {

        Thread gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        while (true) {

            update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void update() {

        player.update(bullets, WIDTH, HEIGHT);

        // обновление пуль игрока
        for (int i = bullets.size() - 1; i >= 0; i--) {

            Bullet bullet = bullets.get(i);
            bullet.update();

            if (!bullet.isAlive()) {
                bullets.remove(i);
            }

        }

        // обновление врагов
        for (int i = enemies.size() - 1; i >= 0; i--) {

            Enemy enemy = enemies.get(i);
            enemy.update(enemyBullets);

            if (!enemy.isAlive()) {
                enemies.remove(i);
            }

        }

        // обновление пуль врагов
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {

            Bullet bullet = enemyBullets.get(i);
            bullet.update();

            if (!bullet.isAlive()) {
                enemyBullets.remove(i);
            }

        }

        spawnEnemies();
        checkCollisions();
    }

    private void spawnEnemies() {

        if (Math.random() < 0.02) {

            enemies.add(new Enemy((int) (Math.random() * (WIDTH - 50)), 0));

        }

    }

    private void checkCollisions() {

        // попадание пуль игрока по врагам
        List<Integer> bulletsToRemove = new ArrayList<>();
        List<Integer> enemiesToRemove = new ArrayList<>();

        for (int i = 0; i < bullets.size(); i++) {

            Bullet bullet = bullets.get(i);

            for (int j = 0; j < enemies.size(); j++) {

                Enemy enemy = enemies.get(j);

                if (isColliding(bullet, enemy)) {

                    if (!bulletsToRemove.contains(i)) bulletsToRemove.add(i);
                    if (!enemiesToRemove.contains(j)) enemiesToRemove.add(j);

                }

            }

        }

        for (int i = bulletsToRemove.size() - 1; i >= 0; i--) {
            bullets.remove((int) bulletsToRemove.get(i));
        }

        for (int i = enemiesToRemove.size() - 1; i >= 0; i--) {
            enemies.remove((int) enemiesToRemove.get(i));
        }

        // попадание пуль врагов по игроку
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {

            Bullet bullet = enemyBullets.get(i);

            if (isCollidingWithPlayer(bullet)) {

                player.takeDamage(1);
                enemyBullets.remove(i);

            }

        }

        // столкновение игрока с врагом
        for (int i = enemies.size() - 1; i >= 0; i--) {

            Enemy enemy = enemies.get(i);

            if (isCollidingPlayerEnemy(enemy)) {

                player.takeDamage(2);
                enemies.remove(i);

            }

        }

    }

    private boolean isColliding(Bullet bullet, Enemy enemy) {

        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), 5, 5);
        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 20, 20);

        return bulletRect.intersects(enemyRect);

    }

    private boolean isCollidingWithPlayer(Bullet bullet) {

        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), 5, 5);

        Rectangle playerRect = new Rectangle(
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight()
        );

        return bulletRect.intersects(playerRect);
    }

    private boolean isCollidingPlayerEnemy(Enemy enemy) {

        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 20, 20);

        Rectangle playerRect = new Rectangle(
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight()
        );

        return enemyRect.intersects(playerRect);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        player.draw(g);

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (Bullet bullet : enemyBullets) {
            bullet.draw(g);
        }

        // UI
        g.setColor(Color.WHITE);
        g.drawString("HP: " + player.getHp(), 10, 20);

    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

    }

}