/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package weapon;

import java.awt.Point;

/**
 *
 * @author david.green
 */
public interface Weapon {
    public void fire(Point Location, Point target, int CollisionConstant);
}
