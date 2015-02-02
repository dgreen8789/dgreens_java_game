/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Point;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class AIUtilities {

    /**
     *
     * @param distance
     * @param destination
     * @param u
     * @return
     */
    public static int moveTo(int distance, Point destination, Unit u) {
        double angle = Math.atan2(destination.y - u.getY(), destination.x - u.getX());
        double y = distance * Math.sin(angle);
        int sign = (Math.signum(destination.x - u.getX()) < 1) ? -1 : 1;
        double x = Math.sqrt((distance * distance)
                - (y * y)) * sign;
       return 0;      
    }
}
