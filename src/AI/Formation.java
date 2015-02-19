package AI;

import java.awt.Point;
import java.util.Arrays;
import main.init;
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
    private double[] segmentLengths;
    private boolean rotation;
    private int rotationPerFrame;
    private int angle = 0;
    private volatile Unit centerUnit;
    private int distance;
    private Point centerPoint;
    private boolean centeredOnUnit;

    public Formation(int numUnits, int formationConstant, Point center) {
        this.formationConstant = formationConstant;
        formation = new int[2][numUnits];
        this.centerPoint = center;
        this.distance = Formation.DISTANCE;
        this.updateFormation();
    }

    public int getFormationConstant() {
        return formationConstant;
    }

    public void setFormationConstant(int formationConstant) {
        this.formationConstant = formationConstant;
    }

    public int[][] getPoints() {
        return formation;
    }

    public void setPoints(int[][] formation) {
        this.formation = formation;
        this.setSegmentLengths();
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
        return centeredOnUnit;
    }

    public void setCenteredOnUnit(boolean centeredOnUnit) {
        this.centeredOnUnit = centeredOnUnit;
        updateFormation();
    }

    public Point getCenter() {
        return (centeredOnUnit) ? centerUnit.getLocation() : centerPoint;
    }

    public void updateFormation() {
//        System.out.println("FORMATION CENTER = " + getCenter());
//        System.out.println("Center unit location  = " + 
//                ((centerUnit == null) ? "null" : centerUnit.getLocation()));
//        System.out.println("Main character location = " + 
//                init.getGameGUI().getGraphicsControl().getMainCharacter().getLocation());
        switch (formationConstant) {
            case LINEAR_FORMATION:
            case GEOMETRIC_FORMATION:
                formation = graphics.RotatingShape.shape(getCenter(), distance, formation[0].length, angle);
                angle += this.getRotationPerFrame();
                angle %= 360;
                break;
            case NO_FORMATION:
                break;
            default:
                throw new IndexOutOfBoundsException("Invalid Formation Code");

        }
        setSegmentLengths();
    }

    public int getNumUnits() {
        return formation.length;
    }

    public Point getMove(int unitNumber) {
        return new Point(formation[unitNumber][0], formation[unitNumber][1]);
    }

    private void setSegmentLengths() {
        this.segmentLengths = new double[formation[0].length];
        for (int i = 0; i < formation[0].length; i++) {
            Point one = new Point(formation[0][i], formation[1][i]);
            int newIndex = (i + 1) % segmentLengths.length;
            Point two = new Point(formation[0][newIndex], formation[1][newIndex]);
            segmentLengths[i] = one.distance(two);
        }
    }

    public double[] getSegmentLengths() {
        return segmentLengths;
    }

    @Override
    public String toString() {
        return "Formation{" + "formationConstant=" + formationConstant + ", \nformation=" 
                + Arrays.deepToString(formation)
                + ", \nsegmentLengths=" + Arrays.toString(segmentLengths) 
                + ", rotation=" + rotation + ", rotationPerFrame=" 
                + rotationPerFrame + ", angle=" + angle + ", center="
                + centerUnit + ", distance=" + distance + ", centerPoint=" 
                + centerPoint + ", centersOnUnit=" + centeredOnUnit + '}';
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        updateFormation();
    }

    public Unit getCenterUnit() {
        return centerUnit;
    }

    public void setCenterUnit(Unit centerUnit) {
        this.centerUnit = centerUnit;
    }
    
}
