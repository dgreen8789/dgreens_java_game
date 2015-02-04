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
     * Moves a unit towards a given location
     *
     * @param distance The distance to move
     * @param destination Where the unit is going;
     * @param u The unit to move
     * @return The left over distance, if any;
     */
    public static int moveTowards(int distance, Point destination, Unit u) {

        //Calculate the angle
        double angle = Math.atan2(destination.y - u.getY(), destination.x - u.getX());
        //Y difference - Unit circle multiplied by distance;
        double y = distance * Math.sin(angle);
        //Which way is x coordinate?
        int sign = (Math.signum(destination.x - u.getX()) < 1) ? -1 : 1;
        //Calculate the X coordinate - Pythagorean theorem
        double x = Math.sqrt((distance * distance)
                - (y * y)) * sign;
        //Get the distance from the destination to where the unit is now
        double destinationDistance = u.getLocation().distance(destination);
        //Turn the new coordinates into a Point object
        Point moveLoc = new Point((int) x, (int) y);
        //Figure out which is closer: the destination, or the new Point 
        boolean destinationCloser
                = destinationDistance
                < u.getLocation().distance(moveLoc);
        //Set the location to the closest of the two points
        Point p = (Point)(destinationCloser ? destination : moveLoc).clone();
        u.setLocation(p);
        System.out.println("Moved from " + u.getLocation()
                + "\nto " + (destinationCloser ? destination : moveLoc));
        //return the leftover distance, if any
        return destinationCloser ? distance - (int) destinationDistance : 0;
    }
}
