/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Point;
import main.init;
import unit.enemy.EnemyUnit;

/**
 *
 * @author david.green
 */
public class EnemyAI extends AI {

    private Formation formation;
    private int numberInFormation;
    private int fireChance = 20;
    private double[] segmentLengths;
    private int currentSegement = 0;

    public EnemyAI(EnemyUnit unit, Formation formation, int numberInFormation) {
        super(unit);
        this.formation = formation;
    }

    @Override
    protected void move(boolean b) {
        move();
    }

    @Override
    protected void move() {
        int lengthLeft = this.unit.getSpeed();
        if (segmentLengths != null){
           Point p =  this.getUnit().getLocation();
           
        }
    }

    @Override
    protected void attack() {
        if ((int) (Math.random() * 100) < fireChance) {
            getUnit().fire(init.getGameGUI().getGraphicsControl().getMainCharacter().getLocation());
        }
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public int getFireChance() {
        return fireChance;
    }

    public void setFireChance(int fireChance) {
        this.fireChance = fireChance;
    }

    @Override
    public void setMovePoints(int[][] movePoints) {
        super.setMovePoints(movePoints);
        this.segmentLengths = new double[movePoints[0].length];
        for (int i = 0; i < movePoints.length; i++) {
            Point one = new Point(movePoints[0][i], movePoints[1][i]);
            int newIndex =  (i + 1) % segmentLengths.length;
            Point two = new Point(movePoints[0][newIndex], movePoints[1][newIndex]);
            segmentLengths[i] = one.distance(two);
        }
    }

}
