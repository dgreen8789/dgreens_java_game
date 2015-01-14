/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.projectileAI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import main.init;

/**
 *
 * @author david.green
 */
public class StandardProjectile extends Unit implements ColoredUnit {

    private Color color;
    private int radius;
    private Point target;
    private int speed;
    private int initialX;
    private int initialY;

    public StandardProjectile(Color color, int radius, Point target) {
        this(color, radius, 0, 0, target);
    }

    public StandardProjectile(Color color, int radius, int x, int y, Point target) {
        super(x, y);
        this.color = color;
        this.radius = radius;
        this.target = target;
        this.initialX = x;
        this.initialY = y;
        this.setAi(new projectileAI(this));

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(this.getX() - radius / 2, this.getY() - radius / 2, radius, radius);
    }

    @Override
    public void onHit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCollide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCreate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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
            //this.setAi(null);
        }

    }

}
