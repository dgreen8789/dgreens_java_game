/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.util.ArrayList;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class UnitOperationHandler implements Runnable {

    private volatile boolean lock;
    private ArrayList<UnitOperation> operations;
    private ArrayList<Unit> units;

    @Override
    public void run() {
        while (true) {
           // System.out.println("Ran List of size " + operations.size());
            while (operations.size() > 0) {
                try {
                    operations.get(0).execute(units);
                    //System.out.println(operations.get(0).getOperationName());
                    operations.remove(0);
                } catch (NullPointerException e) {
                    operations.remove(0);
                }
            }
        }
    }

    public boolean addOperation(UnitOperation e) {
        if (operations.contains(e)) {
            return false;
        }
        return operations.add(e);
    }

    public UnitOperationHandler() {
        operations = new ArrayList<>();
        units = new ArrayList<>();
    }

    public ArrayList<Unit> getUnits() {
        return (ArrayList<Unit>) units.clone();
    }

}
