package phyics;

import java.util.ArrayList;
import main.init;
import unit.Unit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
public class MultipleUnitAdditionOperation extends UnitOperation {

    ArrayList<Unit> affectedUnits;

    public MultipleUnitAdditionOperation(ArrayList<Unit> affectedUnits) {
        super(UnitOperation.SPECIAL_OPERATION, null);
        this.affectedUnits = affectedUnits;
    }

    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        int x = init.getGameGUI().getCollisionHandler().getBeginningIndex(u.get(0).getCollisionConstant());
        u.addAll(x, affectedUnits);
        init.getGameGUI().getCollisionHandler().updateListLocs(u.get(0).getCollisionConstant(), affectedUnits.size());
        return true;
    }

    @Override
    public String getOperationName() {
        return "MULTIPLE_UNIT_ADDITION";
    }
    
}
