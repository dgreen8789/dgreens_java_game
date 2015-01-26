/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phyics;

import java.awt.Point;
import java.util.ArrayList;
import main.init;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class MouseOverHandler extends UnitOperation {

    public MouseOverHandler() {
        super(UnitOperation.SPECIAL_OPERATION, null);
    }
    
    public Unit update(Point mousePosition){
        return null;
    }

    @Override
    public String getOperationName() {
        return "UNIT_MOUSEOVER_CHECK";
    }

    @Override
    public boolean specialOperation(ArrayList<Unit> u) {
        return super.specialOperation(u); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOperationType() {
        return UnitOperation.SPECIAL_OPERATION;
    }
    
}
