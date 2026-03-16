package com.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

import com.example.Bullet;

public class Player {
    private int x, y;
    private final int width = 30, height = 30;
    private int speed = 5;
    boolean movingLeft, movingRight, movingUp, movingDown, shooting;
    private long lastShotTime;
    private static final long FIRE_RATE = 200;
    private int hp = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.lastShotTime = 0;
    }

    public void update(List<Bullet> bullets, int screenWidth, int screenHeight) {

        if (movingLeft && x > 0) x -= speed;
        if (movingRight && x < screenWidth - width) x += speed;

        if (movingUp && y > 0) y -= speed;
        if (movingDown && y < screenHeight - height) y += speed;

        // стрельба
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

        if (key == KeyEvent.VK_A) movingLeft = true;
        if (key == KeyEvent.VK_D) movingRight = true;

        if (key == KeyEvent.VK_W) movingUp = true;
        if (key == KeyEvent.VK_S) movingDown = true;

        if (key == KeyEvent.VK_SPACE) shooting = true;
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) movingLeft = false;
        if (key == KeyEvent.VK_D) movingRight = false;

        if (key == KeyEvent.VK_W) movingUp = false;
        if (key == KeyEvent.VK_S) movingDown = false;

        if (key == KeyEvent.VK_SPACE) shooting = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            System.out.println("GAME OVER");
        }
    }

    public int getHp() {
        return hp;
    }
}
