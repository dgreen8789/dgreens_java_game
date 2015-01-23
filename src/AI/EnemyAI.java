/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import main.init;
import unit.enemy.EnemyUnit;

/**
 *
 * @author david.green
 */
public class EnemyAI extends AI{
    private Formation formation;
    private int numberInFormation;
    private int fireChance = 20;
    public EnemyAI(EnemyUnit unit, Formation formation, int numberInFormation) {
        super(unit);
        this.formation = formation;
    }
    
    @Override
    protected void move(boolean b) {
        //move();
    }

    @Override
    protected void move() {
        //if (numberInFormation == formation.getNumUnits()){
        //    formation.updateFormation();
        //}
    }

    @Override
    protected void attack() {
        if ((int ) (Math.random() * 100)  < fireChance){
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
    
    
    
}