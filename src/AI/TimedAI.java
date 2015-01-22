/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import main.init;
import phyics.UnitOperation;
import unit.Unit;

/**
 *
 * @author David
 */
public class TimedAI extends AI {

    private int moves;
    private AI ai;

    public TimedAI(AI ai, int moves) {
        super(null);
        this.ai = ai;
        this.moves = moves;
    }

    @Override
    public void executeMove(boolean b) {
        ai.executeMove(b);
        moves--;
        if (moves <= 0) {
            init.getUnitOperationHandler().addOperation(new UnitOperation(UnitOperation.DELETE_UNIT, getUnit()));
        }
    }

    @Override
    public void executeMove() {
        ai.executeMove();
        moves--;
        if (moves <= 0) {
            init.getUnitOperationHandler().addOperation(new UnitOperation(UnitOperation.DELETE_UNIT, getUnit()));
        }
    }

    @Override
    public Unit getUnit() {
        return ai.getUnit();
    }

    @Override
    protected void move(boolean b) {
        ai.move(b);
    }

    @Override
    protected void move() {
        ai.move();
    }

    @Override
    protected void attack() {
        ai.attack();
    }

}
