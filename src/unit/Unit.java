/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.AI;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import main.init;
import weapon.Weapon;

/**
 *
 * @author David
 */
public abstract class Unit implements Comparable {
    
    private Point location;
    private AI ai;
    private boolean hittable;
    private boolean collidable;
    private boolean canFire;
    private Weapon weapon;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        if (this.isValidLocation(location)) {
            this.location = location;
        }
    }

    public void setLocation(int x, int y) {
        setLocation(new Point(x, y));
        
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
    }

    public void moveX(int xDelta) {
        this.setLocation(new Point(this.getLocation().x + xDelta, this.getLocation().y));
    }

    public void moveY(int yDelta) {
        this.setLocation(new Point(this.getLocation().x, this.getLocation().y + yDelta));
    }

    public int getX() {
        return this.getLocation().x;
    }

    public int getY() {
        return this.getLocation().y;
    }

    public  abstract void draw(Graphics g);

    public  abstract void onCollide(Unit u);

    public  void onCreate(){
       init.getGameGUI().getGraphicsControl().addUnit(this);
    }
    
    public void fire(Point target){
        if (canFire){
            this.getWeapon().fire(this.getLocation(), target);
        }
    }
    
    public abstract Shape getHitbox();

    public void onDeath() {
        init.getGameGUI().getGraphicsControl().removeUnit(this);
    }
    public void executeAImove(){
        ai.executeMove();
    }
    public boolean isValidLocation(Point location){
        return init.getGameGUI().getBounds().contains(location);
    }

    @Override
    public int compareTo(Object t) {
        return this.getCollisionConstant() - ((Unit)t).getCollisionConstant();
    }

    public boolean canFire() {
        return canFire;
    }

    public void allowFirePermission(boolean canFire) {
        this.canFire = canFire;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public abstract int getCollisionConstant();
    
    
    

}
