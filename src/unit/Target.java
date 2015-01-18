/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.AI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import main.init;
import phyics.CollisionConstants;

/**
 *
 * @author david.green
 */
public class Target extends Unit implements ColoredUnit {

    private Color color;
    private int size;

    public Target() {
        this(0, 0, 100);
        this.setLocation(this.getRandomLocation());
    }

    public Target(int x, int y) {
        this(x, y, 100);
    }

    public Target(int x, int y, int size) {
        this(x, y, size, Color.RED);
    }

    public Target(int x, int y, int size, Color color) {
        super(x, y);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(this.getX() - size / 2, this.getY() - size / 2, size, size);
        if (size > 6) {
            g.setColor(Color.WHITE);
            g.fillOval(this.getX() - 2, this.getY() - 2, 4, 4);
        }

    }

    @Override
    public void onCollide(Unit u) {
        if (u instanceof StandardProjectile) {
            init.getGameGUI().getGraphicsControl().addScore(1);
            Point loc = getRandomLocation();
            this.setLocation(loc);
            //System.out.println(loc.toString());
        } 

    }

    @Override
    public void fire(Point target) {
    }

    @Override
    public Area getHitbox() {
        Ellipse2D hitbox = new Ellipse2D.Double();
        Dimension2D hitboxSize = new Dimension();
        hitboxSize.setSize(size, size);
        Point hitboxLocation = (Point) this.getLocation().clone();
        hitboxLocation.x -= size / 2;
        hitboxLocation.y -= size / 2;
        hitbox.setFrame(hitboxLocation, hitboxSize);
        return new Area(hitbox);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void executeAImove() {
    }

    @Override
    public void setAi(AI ai) {
    }

    @Override
    public AI getAi() {
        return null;
    }

    @Override
    public boolean isValidLocation(Point location) {
        location.x -= this.getSize();
        location.y -= this.getSize();
        return super.isValidLocation(location); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.NEUTRAL_UNIT;
    }

    private Point getRandomLocation() {
        Point loc = new Point(-1, -1);
        while (!this.isValidLocation(loc)) {
            loc.x = (int) (Math.random() * init.getGameGUI().getGraphicsControl().getGameWidth());
            loc.y = (int) (Math.random() * init.getGameGUI().getGraphicsControl().getGameHeight());
        }
        return loc;
    }

}
