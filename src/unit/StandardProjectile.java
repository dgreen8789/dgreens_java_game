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

/**
 *
 * @author david.green
 */
public class StandardProjectile extends Unit implements ColoredUnit {

    Color color;
    int radius;
    Point target;

    public StandardProjectile(Color color, int radius, Point target) {
        super();
        this.color = color;
        this.radius = radius;
        this.target = target;
    }

    public StandardProjectile(Color color, int radius, int x, int y, Point target) {
        super(x, y);
        this.color = color;
        this.radius = radius;
        this.target = target;
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
}
