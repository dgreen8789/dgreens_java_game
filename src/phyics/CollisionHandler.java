/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.util.ArrayList;
import java.util.Collections;
import unit.Unit;

/**
 *
 * @author David
 */
public class CollisionHandler {

    public static void ComputeAndHandle(ArrayList<Unit> u) {
        //Collections.sort(u);
        //System.out.println(u);
        //TODO

        //compare enemy projectiles against neutral and friendly units
        //UPDATE THE LIST
        //compare friendly projectiles against enemy and neutral units
        //UPDATE THE LIST
        //compare enemy units vs friendly AND neutral units
        //UPDATE THE LIST
        //compare friendly units vs neutral units
        //UPDATE THE LIST
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
    private void computeAndHandleSubset(ArrayList<Unit> one, ArrayList<Unit> two, boolean reverse) {
        updateListLocs();
    }

    /**
     * updates the class variables that keep track of divisions between types of
     * units.
     */
    private void updateListLocs() {

    }

    private int getIndexOfUnitType(int type) {
        int index = 0;
        
        return index;

    }
}
