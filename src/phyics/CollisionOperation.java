/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import main.init;
import unit.StandardProjectile;
import unit.Unit;

/**
 *
 * @author David
 */
public class CollisionOperation extends UnitOperation {

    AtomicInteger[] indexes;

    public CollisionOperation() {
        super(UnitOperation.SPECIAL_OPERATION, null);
        this.indexes = new AtomicInteger[CollisionConstants.CODE_LIST.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new AtomicInteger();
        }
    }

    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        ComputeAndHandle(u);
        return true;
    }
    
    public void ComputeAndHandle(ArrayList<Unit> u) {

        //TODO
        //compare neutral and friendly units against enemy projectiles 
        int start1 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        int end1 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT, u);
        int start2 = getBeginningIndex(CollisionConstants.ENEMY_PROJECTILE);
        int end2 = getEndingIndex(CollisionConstants.ENEMY_PROJECTILE, u);
        computeAndHandleSubset(u, start1, end1, start2, end2, false);

        //compare enemy and neutral units against friendly projectiles  
        start1 = getBeginningIndex(CollisionConstants.ENEMY_UNIT);
        end1 = getEndingIndex(CollisionConstants.NEUTRAL_UNIT, u);
        start2 = getBeginningIndex(CollisionConstants.FRIENDLY_PROJECTILE);
        end2 = getEndingIndex(CollisionConstants.FRIENDLY_PROJECTILE, u);
        computeAndHandleSubset(u, start1, end1, start2, end2, false);

        //compare enemy units vs friendly AND neutral units
        start1 = getBeginningIndex(CollisionConstants.ENEMY_UNIT);
        end1 = getEndingIndex(CollisionConstants.ENEMY_UNIT, u);
        start2 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        end2 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT, u);
        computeAndHandleSubset(u, start1, end1, start2, end2, true);

        //compare friendly units vs neutral units
        start1 = getBeginningIndex(CollisionConstants.FRIENDLY_UNIT);
        end1 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT, u);
        start2 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        end2 = getEndingIndex(CollisionConstants.NEUTRAL_UNIT, u);
        computeAndHandleSubset(u, start1, end1, start2, end2, true);

    }

    /**
     * Compares collisions of one against two
     *
     * @param one ArrayList to compare against two.
     * @param two ArrayList to be compared against.
     * @param reverse boolean signal to reverse comparisons in the case of a
     * collision, one.get(x).onHit(y) is called if reverse is true,
     * two.get(y).onHit(one.get(x) is called
     *
     */
    private void computeAndHandleSubset(ArrayList<Unit> units, int start1, int end1, int start2, int end2, boolean reverse) {
        try {
            for (int listOne = start1; listOne < end1; listOne++) {
                for (int listTwo = start2; listTwo < end2; listTwo++) {
                    Unit one = units.get(listOne);
                    Unit two = units.get(listTwo);
                    Area a = (Area) (one.getHitbox().clone());
                    a.intersect(two.getHitbox());
                    if (!a.isEmpty()) {
                        one.onCollide(two);
                        if (two instanceof StandardProjectile) {
                            if (((StandardProjectile) (two)).isDestroyedOnCollision()) {
                                two.onDeath();
                                end2--;
                            } else {
                                two.onCollide(one);
                            }
                        } else if (reverse) {
                            two.onCollide(one);
                        }

                    }
                }

            }
        } catch (IndexOutOfBoundsException e) {
            for (int i = 0; i < indexes.length; i++) {
                System.out.println(CollisionConstants.getCodeName(i) + " begins at index " +
                        indexes[i].get() + " and ends at index " + getEndingIndex(i, units) );
                
            }
            System.out.println("# of units = " + units.size() + "\n\n");
            
        }
    }

    /**
     * updates the class variables that keep track of divisions between types of
     * units.
     *
     * @param collisionCode the Collision code for the units involved
     * @param delta the amount to add
     */
    public void updateListLocs(int collisionCode, int delta) {
        if (CollisionConstants.isValidCollisionCode(collisionCode)) {
            for (int i = collisionCode + 1; i < indexes.length; i++) {
                indexes[i].addAndGet(delta);
            }
        }
    }

    /**
     *
     * @param collisionCode the Collision code to check for;
     * @return returns the index of the first occurrence of a unit with the
     * specified code in the arrayList of units, or -1 upon invalid code;
     */
    public int getBeginningIndex(int collisionCode) {
        return indexes[collisionCode].get();
    }

    public int getEndingIndex(int collisionCode, ArrayList<Unit> u) {
        if (collisionCode == CollisionConstants.CODE_LIST[CollisionConstants.CODE_LIST.length - 1]) {
            return u.size() - 1; // OPTIMIZE THIS !!!
        }
        return indexes[collisionCode + 1].get();
    }

    /**
     *
     * @return a clone of the AtomicInteger Array that holds the index values
     */
    public AtomicInteger[] getIndexes() {
        return indexes.clone();
    }

    @Override
    public String getOperationName() {
        return "COLLISION";
    }
    public void clearIndexes(){
        for (int i = 0; i < indexes.length; i++) {
           indexes[i].set(0);        
        }
    }
    

}
