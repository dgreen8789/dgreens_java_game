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
    public static double LEVEL_SCALING = 1.1;
    private static final int RANDOMIZATION_FACTOR = 4;
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

    private ArrayList<Integer> generateUnitNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        //Generate the number of units in the level
        int cap = (int) Math.pow(10, (int) Math.log10(levelNum) + 1);
        /**
         * 1 - 10 = 10 11 - 100 = 100 101 - 1000 = 100
         */
        cap += levelNum;
        //make the cap scale with the level;
        //random 4 digit number
        int numUnits = (seed[seed.length - 1]) * (seed[0] + 1);
        numUnits %= cap;
        //make sure there is at least 1 unit ;
        ++numUnits;

        int levelComplexity = getDifficulty();

        double[] vals = new double[numUnits];
        Arrays.fill(vals, levelComplexity / (double) numUnits);

        for (int i = 0; i < RANDOMIZATION_FACTOR; i++) {
            double percentage = Math.random();
            for (int j = 0; j < vals.length / 2 + vals.length % 2; j++) {
                int removeIndex = (int) (Math.random() * vals.length);
                int addIndex = (int) (Math.random() * vals.length);
                double value = vals[removeIndex] * percentage;
                vals[addIndex] += value;
                vals[removeIndex] -= value;
            }
        }
        for (int i = 0; i < vals.length; i++) {
            vals[i] *= numUnits;
            if (vals[i] < 1) {
                vals[i]++;
            }
            numbers.add((int) vals[i]);

        }
        System.out.println("Cap = " + cap);
        System.out.println("Total Complexity = " + levelComplexity);
        System.out.println("# units = " + numUnits);
        System.out.println("Unit Complexities : " + numbers);
        return numbers;
    }

    public void setup() {
        clearLevel(true);
        seed = generateSeed();
        ArrayList<Integer> unitComplexities = generateUnitNumbers();
        units = generateUnits(unitComplexities);
        for (Unit u : units) {
            u.onCreate();
        }
        System.out.println("Setup for level " + levelNum + " complete");
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
