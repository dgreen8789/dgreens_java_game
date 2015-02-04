/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Point;
import java.util.Arrays;
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
    private int currentSegment = 0;

    public EnemyAI(EnemyUnit unit, Formation formation, int numberInFormation) {
        super(unit);
        this.formation = formation;
        this.numberInFormation = numberInFormation;
    }

    @Override
    protected void move(boolean b) {
        move();
    }

    @Override
    protected void move() {
        if (getFormation() != null) {
            double[] segmentLengths = this.getFormation().getSegmentLengths();
            int lengthLeft = this.unit.getSpeed();
            while (lengthLeft > segmentLengths[currentSegment]) {
                lengthLeft -= segmentLengths[currentSegment++];
                currentSegment %= segmentLengths.length - 1;
            }
            ++currentSegment;
            currentSegment %= segmentLengths.length - 1;
            Point p = new Point(getFormation().getPoints()[0][currentSegment],
                    getFormation().getPoints()[1][currentSegment]);
            lengthLeft+= AIUtilities.moveTowards(lengthLeft, p, unit);
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

    public void setNumberInFormation(int numberInFormation) {
        this.numberInFormation = numberInFormation;
    }

}
