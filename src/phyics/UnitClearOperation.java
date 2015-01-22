/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.util.ArrayList;
import main.init;
import unit.Unit;

/**
 *
 * @author David
 */
public class UnitClearOperation extends UnitOperation{

    public UnitClearOperation() {
        super(UnitOperation.SPECIAL_OPERATION, null);
    }
    
    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        u.clear();
        init.getGameGUI().getCollisionHandler().clearIndexes();
        return true;
    }

    @Override
    public String getOperationName() {
        return "CLEAR_ALL_UNITS_OPERATION";
    }
    
    
}
