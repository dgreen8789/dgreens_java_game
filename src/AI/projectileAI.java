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

    private double xValue;
    private double yValue;
    private double angleValue;
    private int xDiff;
    private int yDiff;

    public projectileAI(StandardProjectile unit) {
        super(unit);
        StandardProjectile projectile = (StandardProjectile) getUnit();
        Point target = projectile.getTarget();
        xDiff = target.x - projectile.getInitialX();
        yDiff = target.y - projectile.getInitialY();

        updateXY();
    }

    @Override
    protected void move() {
        updateXY();
        this.getUnit().moveX((int) xValue);
        this.getUnit().moveY((int) yValue);
    }

    @Override
    protected void attack() {

    }

    private void updateXY() {
        angleValue = Math.atan2(yDiff, xDiff);
        xValue = Math.cos(angleValue) * ((StandardProjectile) this.getUnit()).getSpeed();
        yValue = Math.sin(angleValue) * ((StandardProjectile) this.getUnit()).getSpeed();
    }

}
