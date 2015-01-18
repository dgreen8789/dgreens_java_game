/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Point;
import unit.StandardProjectile;

/**
 *
 * @author david.green
 */
public class ProjectileAI extends AI {

    private int count = 1;
    private double angle;

    public ProjectileAI(StandardProjectile unit) {
        super(unit);
        StandardProjectile projectile = (StandardProjectile) getUnit();
        Point target = projectile.getTarget();
        angle = Math.atan2(target.y - projectile.getInitialY(), target.x - projectile.getInitialX());
    }

    @Override
    protected void move() {
        StandardProjectile projectile = (StandardProjectile) getUnit();
        double y = projectile.getSpeed() * Math.sin(angle);
        int sign = (Math.signum(projectile.getTarget().x - projectile.getInitialX()) < 1) ? -1 : 1;
        double x = Math.sqrt((projectile.getSpeed() * projectile.getSpeed()) - (y * y)) * sign;
        projectile.setHitboxDeltas( x,  y);
        x*= count;
        y*= count++;
        projectile.setLocation((int) x + projectile.getInitialX(), (int) y + projectile.getInitialY());
        projectile.updateHitbox();

    }

    protected void move(boolean b) {
        if (b) {
            move();
        } else {
            StandardProjectile projectile = (StandardProjectile) getUnit();
            double y = projectile.getSpeed() * Math.sin(angle);
            int sign = (Math.signum(projectile.getTarget().x - projectile.getInitialX()) < 1) ? -1 : 1;
            double x = Math.sqrt((projectile.getSpeed() * projectile.getSpeed()) - (y * y)) * (sign * count);
            y *= count++;
            projectile.setHitboxDeltas(x, y);
        }
    }

    @Override
    protected void attack() {

    }

    public double getAngleInRadians() {
        return angle;
    }

    public double getAngleInDegrees() {
        return Math.toDegrees(angle);
    }

}
