/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package weapon;

import graphics.RotatingShape;
import java.awt.Point;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class MultishotWeapon extends StandardWeapon {
    private int shotSpacing;
    private int numShots;
    private int[][] firePoints;
    private boolean weaponConverge = true;
    /**
     * 
     * @param attachedUnit the unit this gun belongs to;
     * @param numShots number of shots between 1 and 5, inclusive
     * @param shotSpacing distance, in pixels, between shots;
     */
    public MultishotWeapon(Unit attachedUnit, int numShots, int shotSpacing) {
        super(attachedUnit);
        this.numShots = numShots % 6;
        this.shotSpacing = shotSpacing;
       // System.out.println(super.getAttachedUnit().getLocation().toString());
    }
    
    @Override
    public void fire(Point Location, Point target) {
        updateFirePoints();
        for (int i = 0; i < firePoints[0].length; i++) {
            Point startPos = new Point(firePoints[0][i], firePoints[1][i]);
            if (!this.WeaponConverges()){
                target.x += firePoints[0][i] - Location.x;
                target.y += firePoints[1][i] - Location.y;
            }
            super.fire(startPos, target);
        }
    }
    private void updateFirePoints(){
        firePoints = RotatingShape.shape(super.getAttachedUnit().getLocation(), shotSpacing, numShots, 0);
    }
    public int getShotSpacing() {
        return shotSpacing;
        
    }

    public void setShotSpacing(int shotSpacing) {
        this.shotSpacing = shotSpacing;
    }

    public int getNumShots() {
        return numShots;
    }

    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }

    public boolean WeaponConverges() {
        return weaponConverge;
    }

    public void setWeaponConverge(boolean weaponConverge) {
        this.weaponConverge = weaponConverge;
    }
    
    
    
}
