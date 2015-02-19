package AI;

import java.awt.Point;
import java.util.Arrays;
import main.init;
import unit_enemy.EnemyUnit;

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
            this.getFormation().updateFormation();
            double[] segmentLengths = this.getFormation().getSegmentLengths();
            if (segmentLengths.length > 1) {
                double lengthLeft = this.unit.getSpeed();
                boolean movedSegments = false;
                while (lengthLeft > segmentLengths[currentSegment]) {
                    movedSegments = true;
                    lengthLeft -= segmentLengths[currentSegment++];
                    //System.out.println(lengthLeft + " dist left after seg " + currentSegment);
                    //System.out.println(Arrays.toString(segmentLengths));
                    currentSegment %= segmentLengths.length;
                }
                if (movedSegments) {
                    ++currentSegment;
                    currentSegment %= segmentLengths.length;
                }
                Point p = new Point(getFormation().getPoints()[0][currentSegment],
                        getFormation().getPoints()[1][currentSegment]);
                if (AIUtilities.moveTowards((int) lengthLeft, p, unit) == unit.getSpeed()) {
                    currentSegment++;
                    currentSegment %= segmentLengths.length;
                    move();
                }
            }
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
