package com.example;

import java.awt.*;
import java.util.List;

public class Enemy {

    private int x, y;
    private int speed = 2;

    private boolean alive = true;

    private long lastShotTime;
    private static final long ENEMY_FIRE_RATE = 1000;

    public Enemy(int x, int y) {

        this.x = x;
        this.y = y;
        this.lastShotTime = 0;

    }

    public void update(List<Bullet> enemyBullets) {

        y += speed;

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime >= ENEMY_FIRE_RATE) {

            enemyBullets.add(new Bullet(x + 10, y + 20, -5));
            lastShotTime = currentTime;

        }

        if (y > 600) alive = false;

    }

    public void draw(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillOval(x, y, 20, 20);

    }

    public boolean isAlive() {
        return alive;
    }

    public int getX() { return x; }
    public int getY() { return y; }

}