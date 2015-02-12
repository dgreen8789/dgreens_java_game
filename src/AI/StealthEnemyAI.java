/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import unit_enemy.StealthEnemy;

/**
 *
 * @author david.green
 */
public class StealthEnemyAI extends EnemyAI{
    public StealthEnemyAI(StealthEnemy unit, Formation formation, int numberInFormation) {
        super(unit, formation, numberInFormation);
    }
    
    @Override
    protected void attack() {
        if (!((StealthEnemy)unit).isStealthed())
        super.attack(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
