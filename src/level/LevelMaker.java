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
import java.util.Arrays;
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
    public static double LEVEL_SCALING = .9;
    private boolean completed;
    private short[] seed;
    int levelNum;
    ArrayList<Unit> units;

    public LevelMaker(int difficulty) {
        this.levelNum = difficulty;
    }

    public boolean onVictory(Graphics2D g, Rectangle bounds) {
        //System.out.println("VICTORY METHOD CALLED");
        afterLevel();
        String message = "Level " + levelNum + " Success";
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
        ArrayList<Integer> unitComplexities = generateUnitNumbers(levelNum);
        units = generateUnits(unitComplexities);
        for (Unit u : units) {
            u.onCreate();
        }
        seed = generateSeed();
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
            u = new BasicEnemy(i, 1, 1);
            u.getWeapon().setDamage(i / 2 + 1);
        } else {
            u = new DestroyableTarget(i);
        }
        u.setLocation(UnitUtilities.getRandomLocation(u));
        u.setSize(15);
        return u;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void clearLevel(boolean reAddMainCharacter) {
        init.getUnitOperationHandler().addOperation(new UnitClearOperation());
        if (reAddMainCharacter) {
            init.getUnitOperationHandler().addOperation(new UnitOperation(UnitOperation.ADD_UNIT,
                    init.getGameGUI().getGraphicsControl().getMainCharacter()));
        }
    }

    private short[] generateSeed() {
        long l = (long) (Math.random() * Long.MAX_VALUE / 2) + Long.MAX_VALUE / 2;
        int len = (int) Math.log10(l);
        short[] data = new short[len / 3];
        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (l % 1000);
            l /= 1000;
        }
        System.out.println(Arrays.toString(data));
        return data;

    }

    private int getDifficulty() {
        return (int) (Math.pow(LEVEL_SCALING, levelNum));
    }

}
