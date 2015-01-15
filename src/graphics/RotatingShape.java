/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Point;

/**
 *
 * @author David
 */
public class RotatingShape {
        /**
     * 
     * @param startPos
     * @param radius
     * @param shapeNum
     * @param initialAngle
     * @return A two dimensional array of length shapeNum representing the x and y coordinates of the shape.
     * int[0][i] = List of x-Coordinates;
     * int[1][i] = List of y-Coordinates;
     */
    public static int[][] shape(Point startPos, int radius, int shapeNum, double initialAngle){
        int locs[][] = new int[2][shapeNum];
        initialAngle = Math.toRadians(initialAngle);
        double angleDelta = Math.PI * 2 / shapeNum;
        for (int i = 0; i < locs[0].length; i++) {
            locs[0][i] = (int)(Math.cos(angleDelta * i + initialAngle) * radius) + startPos.x;
            locs[1][i] = (int)(Math.sin(angleDelta * i + initialAngle) * radius) + startPos.y; 
        }    
        return locs;
    }
}
