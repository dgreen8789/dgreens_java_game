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
import java.awt.geom.Area;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
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
    private int affiliation;
    private boolean destroyOnCollision;
    

    public StandardProjectile(Color color, int radius, Point target, int affiliation) {
        this(color, radius, 0, 0, target, affiliation);
    }
    /**
     * 
     * @param color
     * @param radius
     * @param x
     * @param y
     * @param target
     * @param affiliation The collision constant for the team that this projectile is on
     */
    public StandardProjectile(Color color, int radius, int x, int y, Point target, int affiliation) {
        super(x, y);
        this.color = color;
        this.size = radius;
        this.target = target;
        this.initialX = x;
        this.initialY = y;
        this.affiliation = affiliation;
        this.destroyOnCollision = true;
        this.setAi(new projectileAI(this));

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(this.getX() - size / 2, this.getY() - size / 2, size, size);
        g.setColor(Color.WHITE);
//        Area a = this.getHitbox();
//        PathIterator x = a.getPathIterator(null);
//        ArrayList<Point> z =  new ArrayList<>();
//        double[] data = new double[6];
//        while(!x.isDone()){
//            x.currentSegment(data);
//            z.add(new Point((int)data[0], (int)data[1]));
//        }
//        for (int i = 0; i < z.size() - 1; i++) {
//            g.drawLine(z.get(i).x, z.get(i).y, z.get(i + 1).x, z.get(i + 1).y);
//        }
//        g.drawLine(z.get(0).x, z.get(0).y, z.get(z.size() - 1).x, z.get(z.size() - 1).y);
    }

    @Override
    public void onCollide(Unit u) {
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
    public void fire(Point target) {
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
    public Area getHitbox() {
        Ellipse2D hitbox = new Ellipse2D.Double();
        Dimension2D hitboxSize = new Dimension();
        hitboxSize.setSize(size, size);
        hitbox.setFrame(this.getLocation(), hitboxSize);
        return new Area(hitbox);
    }

    public boolean isDestroyedOnCollision() {
        return destroyOnCollision;
    }

    public void setDestroyedOnCollision(boolean destroyOnCollision) {
        this.destroyOnCollision = destroyOnCollision;
    }

    @Override
    public int getCollisionConstant() {
        return affiliation;
    }
    

}
