/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.AI;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import main.init;
import phyics.UnitOperation;
import unit.enemy.EnemyUnit;
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
    private int size;
    protected int health;
    protected int maxHealth;
    protected int speed;

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

    public abstract void draw(Graphics2D g);

    public abstract void onCollide(Unit u);

    public void onCreate() {
        init.getUnitOperationHandler().addOperation(
                new UnitOperation(UnitOperation.ADD_UNIT, this));

    }

    public void fire(Point target) {
        if (canFire && target != null) {
            try {
                this.getWeapon().fire(this.getLocation(), target, this.getCollisionConstant());
            } catch (NullPointerException e) {
                //System.out.println("Target " + target + "\tLocation " + this.getLocation());
            }
        }
    }

    public abstract Area getHitbox();
    private static final double PERCENT_HEALTH_GAIN_ON_ENEMY_KILL = .05;
    private static final double PERCENT_HEALTH_GAIN_ON_TARGET_KILL = .05;

    public void onDeath() {
        init.getUnitOperationHandler().addOperation(
                new UnitOperation(UnitOperation.DELETE_UNIT, this));
        if (this instanceof EnemyUnit) {
            Unit c = init.getGameGUI().getGraphicsControl().getMainCharacter();
            int AdditionalHealth = (int) (c.getHealth() + c.getMaxHealth() * PERCENT_HEALTH_GAIN_ON_ENEMY_KILL);
            c.setHealth(Math.min(c.getMaxHealth(), c.getHealth() + AdditionalHealth));
        }
        if (this instanceof Target) {
            Unit c = init.getGameGUI().getGraphicsControl().getMainCharacter();
            int AdditionalHealth = (int) (c.getHealth() + c.getMaxHealth() * PERCENT_HEALTH_GAIN_ON_TARGET_KILL);
            c.setHealth(Math.min(c.getMaxHealth(), c.getHealth() + AdditionalHealth));
        }

    }

    public void executeAImove() {
        ai.executeMove();
    }

    public boolean isValidLocation(Point location) {
        return init.getGameGUI().getBounds().contains(location);
    }

    @Override
    public int compareTo(Object o) {
        return this.getComplexity() - ((Unit) o).getComplexity();
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

    public abstract int getComplexity();

    public abstract int getScore();

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    protected int specialAbilityCooldown;

    public void setSpecialAbilityCooldown(int specialAbilityCooldown) {
        this.specialAbilityCooldown = specialAbilityCooldown;
    }

    public int getSpecialAbilityCooldown() {
        return specialAbilityCooldown;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract void specialAbility();

}
