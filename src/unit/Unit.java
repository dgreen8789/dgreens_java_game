/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.AI;
import java.awt.Graphics;
import java.awt.Point;
import main.init;

/**
 *
 * @author David
 */
public abstract class Unit {

    private Point location;
    private AI ai;
    private boolean hittable;
    private boolean collidable;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public boolean isHittable() {
        return hittable;
    }

    public void setHittable(boolean hittable) {
        this.hittable = hittable;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public Unit() {
        this(0, 0);
    }

    public Unit(int x, int y) {
        this.location = new Point(x, y);
        init.getGameGUI().getGraphicsControl().addUnit(this);
    }

    public abstract void draw(Graphics g);

    public abstract void onHit();

    public abstract void onCollide();

    public abstract void onCreate();
    
    public abstract void onDeath();

}
