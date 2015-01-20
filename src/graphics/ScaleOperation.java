/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
