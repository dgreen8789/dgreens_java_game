/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import unit.Unit;

/**
 *
 * @author David
 */
public abstract class AI {
    private Unit unit;

    public AI(Unit unit) {
        this.unit = unit;
    }
    
    public void executeMove(){
        move();
        attack();
    }

    protected abstract void move();

    protected abstract void attack();
}
