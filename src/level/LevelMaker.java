/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import graphics.tasks.LevelCompleteTextTask;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import main.init;
import phyics.UnitClearOperation;
import phyics.UnitOperation;
import unit.DestroyableTarget;
import unit.Unit;
import unit.UnitUtilities;
import unit.enemy.BasicEnemy;

/**
 *
 * @author David
 */
public class LevelMaker {

    public static int MINIMUM_UNIT_COMPLEXITY = 1;
    public static int MAXIMUM_UNIT_COMPLEXITY = 1024;
    private boolean completed;
    int difficulty;
    ArrayList<Unit> units;

    public LevelMaker(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean onVictory(Graphics2D g, Rectangle bounds) {
        //System.out.println("VICTORY METHOD CALLED");
        afterLevel();
        String message = "Level " + difficulty + " Success";
        Point p = new Point(0, 3 * bounds.height / 4);

        LevelCompleteTextTask task = new LevelCompleteTextTask(message,
                bounds.width, bounds.height / 2, p, 60);
        init.getGameGUI().getGraphicsControl().addTask(task);
        return true;
    }

    private ArrayList<Unit> generateUnits(ArrayList<Integer> complexities) {
        ArrayList<Unit> levelUnits = new ArrayList<>();
        for (Integer i : complexities) {
            levelUnits.add(generateEnemy(i));
        }
        return levelUnits;
    }

    private ArrayList<Integer> generateUnitNumbers(int difficulty) {
        ArrayList<Integer> vals = new ArrayList<>();
        while (difficulty > 0) {
            int max = Math.min(difficulty, MAXIMUM_UNIT_COMPLEXITY);
            int complexity = (int) (Math.random() * (max - MINIMUM_UNIT_COMPLEXITY) + MINIMUM_UNIT_COMPLEXITY);
            vals.add(complexity);
            difficulty -= complexity;
        }
        return vals;
    }

    public void setup() {
        clearLevel(true);
        ArrayList<Integer> unitComplexities = generateUnitNumbers(difficulty);
        units = generateUnits(unitComplexities);
        for (Unit u : units) {
            u.onCreate();
        }
        //System.out.println("Setup for level " + difficulty + " complete");
    }

    public boolean checkForVictory() {
        // boolean b = init.getUnitOperationHandler().lock();
        boolean returnValue = Collections.disjoint(init.getUnitOperationHandler().getUnits(), units);
        // b = init.getUnitOperationHandler().unlock();
        return returnValue;
    }

    public void afterLevel() {
        //blah blah
    }
    Unit[] availableUnits = {new DestroyableTarget(1), new BasicEnemy(1, 1, 1)};

    private Unit generateEnemy(Integer i) {
        Unit u;
        if (i > 5) {
            u = new BasicEnemy(i - 5, 1, 1);
            u.setLocation(UnitUtilities.getRandomLocation(u));
            u.setSize(15);
        } else {
            u = new DestroyableTarget(i);
            u.setSize(15);

        }
        return u;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void clearLevel(boolean reAddMainCharacter) {
        init.getUnitOperationHandler().addOperation(new UnitClearOperation());
        if (reAddMainCharacter) {
            init.getUnitOperationHandler().addOperation(new UnitOperation(UnitOperation.ADD_UNIT,
                    init.getGameGUI().getGraphicsControl().getMainCharacter()));
        }
    }

}
