package com.example;

import java.awt.*;

public class Bullet {
    private int x, y;
    private int speed;
    private boolean alive;

    public Bullet(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alive = true;
    }

    public void update() {
        y -= speed;
        if (y < 0) alive = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 5, 5);
    }

    public boolean isAlive() { return alive; }
    
    public int getX() { return x; }
    public int getY() { return y; }
}
