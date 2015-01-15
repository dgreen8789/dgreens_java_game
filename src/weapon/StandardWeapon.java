/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weapon;

import java.awt.Color;
import java.awt.Point;
import phyics.CollisionConstants;
import unit.StandardProjectile;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class StandardWeapon implements Weapon {
    
    public static final int STANDARD_BULLET_SPEED = 20;
    private Unit attachedUnit;

    public StandardWeapon(Unit attachedUnit) {
        this.attachedUnit = attachedUnit;
    }
    
    public void fire(Point Location, Point target) {
        StandardProjectile x = new StandardProjectile(Color.RED, 3, Location.x, Location.y, target
        ,CollisionConstants.FRIENDLY_PROJECTILE);
        x.setSpeed(STANDARD_BULLET_SPEED);
        x.onCreate();
    }

    public Unit getAttachedUnit() {
        return attachedUnit;
    }

    public void setAttachedUnit(Unit attachedUnit) {
       // this.attachedUnit = attachedUnit;
    }
    
}

