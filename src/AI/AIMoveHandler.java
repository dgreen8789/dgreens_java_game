package AI;

import java.util.ArrayList;
import main.init;
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
            if ((!init.getUnitOperationHandler().AIMovesPaused()) || unit.overridesAIPause())
            unit.executeAImove();
        }
        init.getUnitOperationHandler().decrementFramesPaused();
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
