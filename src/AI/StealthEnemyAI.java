package AI;

import unit_enemy.StealthEnemy;

/**
 *
 * @author david.green
 */
public class StealthEnemyAI extends EnemyAI {

    public StealthEnemyAI(StealthEnemy unit, Formation formation, int numberInFormation) {
        super(unit, formation, numberInFormation);
    }

    @Override
    protected void attack() {
        if (!((StealthEnemy) unit).isStealthed()) {
            super.attack(); 
        }
    }

}
