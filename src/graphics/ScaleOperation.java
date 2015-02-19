
package graphics;

import java.util.ArrayList;
import phyics.UnitOperation;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class ScaleOperation extends UnitOperation {
    private double xRatio;
    private double yRatio;

    public ScaleOperation(int OperationType, Unit affectedUnit, double xRatio, double yRatio) {
        super(OperationType, affectedUnit);
        this.xRatio = xRatio;
        this.yRatio = yRatio;
    }


    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        
        return true;
    }
    
    
}
