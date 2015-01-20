/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AI;

import java.util.ArrayList;
import phyics.UnitOperation;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class AIMoveHandler extends UnitOperation{

    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        for (Unit unit : u) {
            unit.executeAImove();
        }
        return true;

    }

    public AIMoveHandler() {
                super(UnitOperation.SPECIAL_OPERATION, null);
    }

    @Override
    public String getOperationName() {
        return "AI_MOVE"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
