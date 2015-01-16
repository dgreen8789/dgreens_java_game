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
public class CollisionHandler {

    AtomicInteger[] indexes;

    public CollisionHandler() {
        this.indexes = new AtomicInteger[CollisionConstants.CODE_LIST.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new AtomicInteger();
        }
    }

    public void ComputeAndHandle(ArrayList<Unit> u) {

        //TODO
        //compare neutral and friendly units against enemy projectiles 
        int start1 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        int end1 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT);
        int start2 = getBeginningIndex(CollisionConstants.ENEMY_PROJECTILE);
        int end2 = getEndingIndex(CollisionConstants.ENEMY_PROJECTILE);
        computeAndHandleSubset(u, start1, end1, start2, end2, false);

        //compare enemy and neutral units against friendly projectiles  
        start1 = getBeginningIndex(CollisionConstants.ENEMY_UNIT);
        end1 = getEndingIndex(CollisionConstants.NEUTRAL_UNIT);
        start2 = getBeginningIndex(CollisionConstants.FRIENDLY_PROJECTILE);
        end2 = getEndingIndex(CollisionConstants.FRIENDLY_PROJECTILE);
        computeAndHandleSubset(u, start1, end1, start2, end2, false);

        //compare enemy units vs friendly AND neutral units
        start1 = getBeginningIndex(CollisionConstants.ENEMY_UNIT);
        end1 = getEndingIndex(CollisionConstants.ENEMY_UNIT);
        start2 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        end2 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT);
        computeAndHandleSubset(u, start1, end1, start2, end2, true);

        //compare friendly units vs neutral units
        start1 = getBeginningIndex(CollisionConstants.FRIENDLY_UNIT);
        end1 = getEndingIndex(CollisionConstants.FRIENDLY_UNIT);
        start2 = getBeginningIndex(CollisionConstants.NEUTRAL_UNIT);
        end2 = getEndingIndex(CollisionConstants.NEUTRAL_UNIT);
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
    private void computeAndHandleSubset(ArrayList<Unit> u, int start1, int end1, int start2, int end2, boolean reverse) {
        for (int i = start1; i < end1; i++) {
            for (int j = start2; j < end2; j++) {
                Unit one = u.get(i);
                Unit two = u.get(j);

                if (one.getCollisionConstant() == CollisionConstants.FRIENDLY_UNIT
                        || two.getCollisionConstant() == CollisionConstants.FRIENDLY_UNIT) {
//                    System.out.println("List1 start: " + start1);
//                    System.out.println("List1 end: " + end1);
//                    System.out.println("List2 start: " + start2);
//                    System.out.println("List2 end: " + end2);
//                    System.out.println("Reverse = " + reverse);
//                    System.out.println("Index List: " + Arrays.toString(init.getGameGUI().getCollisionHandler().getIndexes()) + "\n");

                }
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
    }

    /**
     * updates the class variables that keep track of divisions between types of
     * units.
     *
     * @param collisionCode the Collision code for the units involved
     * @param add true to add one unit, false to subtract
     */
    public synchronized void updateListLocs(int collisionCode, boolean add) {
        if (CollisionConstants.isValidCollisionCode(collisionCode)) {
            for (int i = collisionCode + 1; i < indexes.length; i++) {
                if (add) {
                    indexes[i].incrementAndGet();
                } else {
                    indexes[i].decrementAndGet();
                }
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

    public int getEndingIndex(int collisionCode) {
        if (collisionCode == CollisionConstants.CODE_LIST[CollisionConstants.CODE_LIST.length - 1]) {
            return init.getGameGUI().getGraphicsControl().getUnits().size() - 1; // OPTIMIZE THIS !!!
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

}
