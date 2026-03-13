package com.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

import com.example.Bullet;

public class Player {
    private int x, y;
    private final int width = 30, height = 30;
    private int speed = 5;
    private boolean movingLeft, movingRight, shooting;
    private long lastShotTime;
    private static final long FIRE_RATE = 200; // 200 мс между выстрелами

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.lastShotTime = 0;
    }

    public void update(List<Bullet> bullets, int screenWidth) {
        if (movingLeft && x > 0) x -= speed;
        if (movingRight && x < screenWidth - width) x += speed;

        // Стрельба
        if (shooting) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= FIRE_RATE) {
                bullets.add(new Bullet(x + width / 2 - 2, y, 7));
                lastShotTime = currentTime;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) movingLeft = true;
        if (key == KeyEvent.VK_RIGHT) movingRight = true;
        if (key == KeyEvent.VK_SPACE) shooting = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) movingLeft = false;
        if (key == KeyEvent.VK_RIGHT) movingRight = false;
        if (key == KeyEvent.VK_SPACE) shooting = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
