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

    private volatile ArrayList<UnitOperation> operations;
    private ArrayList<Unit> units;
    private volatile boolean lock;
    private volatile boolean isActuallyLocked;
    private boolean AImovesPaused;
    private int numFramesPaused;

    @Override
    public void run() {
        //System.out.println("");
        while (true) {
            while (operations.size() > 0 && !isActuallyLocked) {
                try {
                    long timeStart = System.currentTimeMillis();
                    operations.get(0).execute(units);
                    long timeStop = System.currentTimeMillis();
                   //System.out.println(operations.get(0).getOperationName() + " took " + 
                    //         + (timeStop - timeStart) + " ms");
                    operations.remove(0);
                } catch (NullPointerException e) {
                    operations.remove(0);
                }
                isActuallyLocked = lock;
            }
            isActuallyLocked = lock;
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

    public boolean lock() {
        lock = true;
        while (isActuallyLocked != lock);
        return lock;
    }

    public boolean unlock() {
        lock = false;
        while (isActuallyLocked != lock);
        return lock;
    }

    public void pauseAllAI(int frames) {
        this.numFramesPaused = frames;
        this.AImovesPaused = true;
    }
    public void decrementFramesPaused(){
        numFramesPaused--;
    }
    public boolean AIMovesPaused(){
        return numFramesPaused > 0;
    }

}
