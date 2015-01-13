/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.awt.Point;
import main.init;
import unit.Unit;

/**
 *
 * @author David
 */
public class packEnemyAI implements AI {

    private Unit[] units;

    /*
     * List of formation types
     */
    public static final int GEOMETRIC_FORMATION = 1;
    public static final int RANDOM_FORMATION = 2;
    public static final int LINEAR_FORMATION = 3;
    public static final int GEOMETRIC_CLUSTERED_FORMATION = 4;
    public static final int RANDOM_CLUSTERED_FORMATION = 5;
    public static final int LINEAR_DELTA_FORMATION = 6;
    private int formationCode = 1;
    public packEnemyAI(Unit[] units) {
        this.units = units;
    }
    public Point getNextMove(int unit){
        if (this.getFormationCode() % 3 == 1){
            Point p;
            //p = getFormation(init.getGameGUI().getGraphicsControl().getPlayerLocation(), this.getUnits().length).rotate(1).getPoint(unit);
            if(this.getFormationCode() > 1){
                
            }else{
                return p;
            }
        }
        return null;
    }

    public Unit[] getUnits() {
        return units;
    }

    public void setUnits(Unit[] units) {
        this.units = units;
    }

    public int getFormationCode() {
        return formationCode;
    }

    public void setFormationCode(int formationCode) {
        this.formationCode = formationCode;
    }

    private Object getFormation(Point playerLocation, int length) {
        return null;
    }
    
}
