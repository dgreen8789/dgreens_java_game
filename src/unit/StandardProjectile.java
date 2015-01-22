/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.ProjectileAI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import phyics.CollisionConstants;

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
    private Area hitbox;
    private double xDelta;
    private double yDelta;
    private int damage;

    public StandardProjectile(Color color, int radius, Point target, int affiliation) {
        this(color, radius, 0, 0, target, affiliation, 10);
    }

    /**
     *
     * @param color
     * @param radius
     * @param x
     * @param y
     * @param target
     * @param affiliation The collision constant for the team that this
     * projectile is on
     */
    public StandardProjectile(Color color, int radius, int x, int y, Point target, int affiliation, int speed) {
        super(x, y);
        this.color = color;
        this.size = radius;
        this.target = target;
        this.initialX = x;
        this.initialY = y;
        this.affiliation = assignAffiliation(affiliation);
        this.speed = speed;
        this.destroyOnCollision = true;
        this.setAi(new ProjectileAI(this));
        this.getAi().executeMove(false);
        hitbox = buildHitbox();
        updateHitbox();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        //DEBUG CODE
//        Color c;
//        switch (getCollisionConstant()) {
//            case CollisionConstants.FRIENDLY_PROJECTILE:
//                c = Color.GREEN;
//                break;
//            case CollisionConstants.ENEMY_PROJECTILE:
//                c = Color.RED;
//                break;
//            case CollisionConstants.NEUTRAL_PROJECTILE:
//                c = Color.YELLOW;
//                break;
//            default:
//                c = Color.MAGENTA;
//
//        }
//        System.out.println(CollisionConstants.getCodeName(affiliation));
//        g.setColor(c);

        g.drawOval(this.getX() - size / 2, this.getY() - size / 2, size, size);
        g.setColor(Color.GREEN);

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
        this.buildHitbox();
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
            super.onDeath();
            // this.setAi(null);
        }

    }

    @Override
    public Area getHitbox() {
        return this.hitbox;
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

    public void setHitboxDeltas(double xDelta, double yDelta) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    private Area buildHitbox() {
        int h = this.speed + this.size * 2;
        int w = this.size;
        Area a = new Area(new Rectangle(this.getX(), this.getY(), w, h));
        AffineTransform at = new AffineTransform();
        double angle = ((ProjectileAI) this.getAi()).getAngleInRadians() + Math.PI / 2;
        at.rotate(angle,
                this.getX(), this.getY());
        a = a.createTransformedArea(at);
        return a;

    }

    public Area updateHitbox() {
        AffineTransform at = new AffineTransform();
        at.translate(xDelta, yDelta);
        hitbox = hitbox.createTransformedArea(at);
        return hitbox;

    }

    private int assignAffiliation(int affiliation) {
        switch (affiliation) {
            case CollisionConstants.FRIENDLY_UNIT:
                return CollisionConstants.FRIENDLY_PROJECTILE;
            case CollisionConstants.ENEMY_UNIT:
                return CollisionConstants.ENEMY_PROJECTILE;
            case CollisionConstants.NEUTRAL_UNIT:
                return CollisionConstants.NEUTRAL_PROJECTILE;
            default:
                return affiliation;
        }
    }
        @Override
    public int getComplexity() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}
