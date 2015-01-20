/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.util.ArrayList;
import java.util.Objects;
import main.init;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class UnitOperation {

    public static final int ADD_UNIT = 1;
    public static final int DELETE_UNIT = -1;
    public static final int SPECIAL_OPERATION = 0;
    private int OperationType;
    private Unit affectedUnit;

    public int getOperationType() {
        return OperationType;
    }

    public Unit getAffectedUnit() {
        return affectedUnit;
    }

    public UnitOperation(int OperationType, Unit affectedUnit) {
        this.OperationType = OperationType;
        this.affectedUnit = affectedUnit;
    }

    /**
     * Defines method code for special operations, should be overridden by
     * implementing class.
     *
     * @param u the ArrayList<
     */
    public boolean specialOperation(ArrayList<Unit> u) {
        return true;
    }

    public void execute(ArrayList<Unit> u) {
        switch (getOperationType()) {
            case ADD_UNIT:
                u.add(getAffectedUnit());
                init.getGameGUI().getCollisionHandler().updateListLocs(getAffectedUnit().getCollisionConstant(), true);
                break;
            case DELETE_UNIT:
                u.remove(getAffectedUnit());
                init.getGameGUI().getCollisionHandler().updateListLocs(getAffectedUnit().getCollisionConstant(), false);
                break;
            case SPECIAL_OPERATION:
                boolean b = specialOperation(u);
                break;

        }
    }

    public String getOperationName() {
        switch (getOperationType()) {
            case ADD_UNIT:
                return "ADD_UNIT";
            case DELETE_UNIT:
                return "DELETE_UNIT";

            case SPECIAL_OPERATION:
                return "SPECIAL_OPERATION";

        }
        return "NULL";

    }

    private void setOperationType(int OperationType) {
        this.OperationType = OperationType;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UnitOperation) {
            UnitOperation other = (UnitOperation) o;
            boolean namesMatch = this.getOperationName().equals(other.getOperationName());
            boolean specialOperation = this.getOperationType() == UnitOperation.SPECIAL_OPERATION
                    && this.getOperationType() == UnitOperation.SPECIAL_OPERATION;
            boolean unitsMatch = false;
            try {
                unitsMatch = this.affectedUnit.equals(other.getAffectedUnit());
            } catch (NullPointerException e) {

            }
            return (namesMatch && (specialOperation || unitsMatch));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.OperationType;
        hash = 37 * hash + Objects.hashCode(this.affectedUnit);
        return hash;
    }

}
