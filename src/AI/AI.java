package AI;

import unit.Unit;

/**
 *
 * @author David
 */
public abstract class AI {

    protected volatile Unit unit;

    public AI(Unit unit) {
        this.unit = unit;
    }

    public void executeMove(boolean b) {
        if (b) {
            move();
            attack();
        } else {
            move(b);
        }
    }

    public void executeMove() {
        move();
        attack();
    }

    public Unit getUnit() {
        return unit;
    }

    protected abstract void move(boolean b);

    protected abstract void move();

    protected abstract void attack();
    
}
