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
 * @author David
 */
public class Formation {

    public static final int NO_FORMATION = 0;
    public static final int GEOMETRIC_FORMATION = 1;
    public static final int LINEAR_FORMATION = 2;
    private static final int DISTANCE = 50;
    private int formationConstant;
    private int[][] formation;
    private boolean rotation;
    private int rotationPerFrame;
    private int angle = 0;
    private Unit center;
    private int distance;
    private Point centerPoint;
    private boolean centersOnUnit;

    public Formation(int numUnits, int formationConstant, Point center) {
        this.formationConstant = formationConstant;
        formation = new int[numUnits][2];
        this.centerPoint = center;
        this.distance = Formation.DISTANCE;
    }

    public int getFormationConstant() {
        return formationConstant;
    }

    public void setFormationConstant(int formationConstant) {
        this.formationConstant = formationConstant;
    }

    public int[][] getFormation() {
        return formation;
    }

    public void setFormation(int[][] formation) {
        this.formation = formation;
    }

    public boolean isRotating() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public int getRotationPerFrame() {
        return rotationPerFrame;
    }

    public void setRotationPerFrame(int rotationPerFrame) {
        this.rotationPerFrame = rotationPerFrame;
    }

    public boolean CentersOnUnit() {
        return centersOnUnit;
    }

    public void setCentersOnUnit(boolean centersOnUnit) {
        this.centersOnUnit = centersOnUnit;
    }

    public Point getCenter() {
        return (centersOnUnit) ? center.getLocation() : centerPoint;
    }

    public void updateFormation() {
        switch (formationConstant) {
            case GEOMETRIC_FORMATION:
                formation = graphics.RotatingShape.shape(getCenter(), distance, formation.length, angle);
                angle += this.getRotationPerFrame();
                angle %= 360;
                break;
            case LINEAR_FORMATION:
                break;
            case NO_FORMATION:
                break;
            default: throw new IndexOutOfBoundsException("Invalid Formation Code");
                
        }
    }
    public int getNumUnits(){
        return formation.length;
    }
    public Point getMove(int unitNumber) {
        return new Point(formation[unitNumber][0], formation[unitNumber][1]);
    }

}
