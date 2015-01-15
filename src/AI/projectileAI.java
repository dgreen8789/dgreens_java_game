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

public class projectileAI extends AI {


    private int count = 1;
    double angle;
    public projectileAI(StandardProjectile unit) {
        super(unit);
        StandardProjectile projectile = (StandardProjectile) getUnit();
        Point target = projectile.getTarget();
        angle = Math.atan2(target.y - projectile.getInitialY(), target.x - projectile.getInitialX());
    }

    @Override
    protected void move() {
        StandardProjectile projectile = (StandardProjectile) getUnit();
        double y = projectile.getSpeed() * Math.sin(angle)  ;
        int sign = (Math.signum(projectile.getTarget().x - projectile.getInitialX()) < 1) ? -1 : 1;
        double x = Math.sqrt((projectile.getSpeed() * projectile.getSpeed()) - (y * y)) * (sign * count);
        y*= count++;
        projectile.setLocation((int)x + projectile.getInitialX(), (int)y + projectile.getInitialY());
        
    }

    @Override
    protected void attack() {

    }


}
