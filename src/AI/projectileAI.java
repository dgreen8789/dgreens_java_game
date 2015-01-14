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

    private int xValue;
    private int yValue;
    private int oldSpeed;
    private double angleValue;

    public projectileAI(StandardProjectile unit) {
        super(unit);
        StandardProjectile projectile = (StandardProjectile) getUnit();
        Point target = projectile.getTarget();
        int xDiff = target.x - projectile.getInitialX();
        int yDiff = target.y - projectile.getInitialY();
        angleValue = Math.atan2(yDiff, xDiff);
        oldSpeed = unit.getSpeed();
        updateXY();
    }

    @Override
    protected void move() {
        if (((StandardProjectile) this.getUnit()).getSpeed() != oldSpeed){
            updateXY();
            oldSpeed = ((StandardProjectile) this.getUnit()).getSpeed();
        }
        this.getUnit().moveX(xValue);
        this.getUnit().moveY(yValue);
    }

    @Override
    protected void attack() {

    }

    private void updateXY() {
        xValue = (int) (Math.cos(angleValue) * ((StandardProjectile) this.getUnit()).getSpeed());
        yValue = (int) (Math.sin(angleValue) * ((StandardProjectile) this.getUnit()).getSpeed());
    }

}
