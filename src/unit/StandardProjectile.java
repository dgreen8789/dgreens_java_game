/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.Formation;
import AI.projectileAI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import main.init;

/**
 *
 * @author david.green
 */
public class StandardProjectile extends Unit implements ColoredUnit {

    private Color color;
    private int size;
    private Point target;
    private int speed;
    private int initialX;
    private int initialY;
    private boolean destroyOnCollision;

    public StandardProjectile(Color color, int radius, Point target) {
        this(color, radius, 0, 0, target);
    }

    public StandardProjectile(Color color, int radius, int x, int y, Point target) {
        super(x, y);
        this.color = color;
        this.size = radius;
        this.target = target;
        this.initialX = x;
        this.initialY = y;
        this.setAi(new projectileAI(this));

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(this.getX() - size / 2, this.getY() - size / 2, size, size);
    }

    @Override
    public void onCollide(Unit u) {
    }

    @Override
    public void onCreate() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point getTarget() {
        return target;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void fire() {
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    @Override
    public void setLocation(Point location) {
        if (super.isValidLocation(location)) {
            super.setLocation(location);
        } else {
            init.getGameGUI().getGraphicsControl().removeUnit(this);
            // this.setAi(null);
        }

    }

    @Override
    public Ellipse2D getHitbox() {
        Ellipse2D hitbox = new Ellipse2D.Double();
        Dimension2D hitboxSize = new Dimension();
        hitboxSize.setSize(size, size);
        hitbox.setFrame(this.getLocation(), hitboxSize);
        return hitbox;
    }

    public boolean isDestroyedOnCollision() {
        return destroyOnCollision;
    }

    public void setDestroyedOnCollision(boolean destroyOnCollision) {
        this.destroyOnCollision = destroyOnCollision;
    }
    

}
