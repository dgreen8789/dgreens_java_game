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

    public projectileAI(StandardProjectile unit) {
        super(unit);
    }
    
    @Override
    protected void move() {
        StandardProjectile projectile = (StandardProjectile)getUnit();
        Point target = projectile.getTarget();
        int xDiff = target.x - projectile.getInitialX();
        int yDiff = target.y - projectile.getInitialY();
        double scale =   Math.sqrt(Math.pow(yDiff, 2) + Math.pow(xDiff, 2)) / Math.pow(projectile.getSpeed(), 2);
        xDiff*=scale;
        yDiff*=scale;
        this.getUnit().moveX(xDiff);
        this.getUnit().moveY(yDiff);
        System.out.println(unit.getX() + "\t" + xDiff + "\n" + unit.getY() + "\t" + yDiff + "\nScale " + scale);
        
    }

    @Override
    protected void attack() {
        
    }
    
}
