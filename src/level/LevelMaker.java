/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import AI.EnemyAI;
import AI.Formation;
import graphics.tasks.LevelCompleteTextTask;
import graphics.tasks.LevelStartDelayer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.init;
import phyics.UnitClearOperation;
import phyics.UnitOperation;
import unit.MainCharacter;
import unit.ProjectileExplosion;
import unit_enemy.DestroyableTarget;
import unit.Unit;
import unit.UnitUtilities;
import unit_enemy.BasicEnemy;
import unit_enemy.EnemyUnit;

/**
 *
 * @author David
 */
public class LevelMaker {

    public static int MINIMUM_UNIT_COMPLEXITY = 1;
    public static int MAXIMUM_UNIT_COMPLEXITY = 1024;
    public static double BASE_LEVEL_SCALING = 1.1;
    public static final double EASY = .2;
    public static final double MEDIUM = .4;
    public static final double HARD = .6;
    public static final double NIGHTMARE = 1.0;
    private static final double defaultDifficulty = MEDIUM;
    private final int RANDOMIZATION_FACTOR = 4;
    private final ArrayList<Class> unitClasses;
    private boolean completed = false;
    private int numUnits;
    private short[] seed;
    private boolean isSetup = true;
    private Random random;
    int levelNum;
    private int initialLevel;
    private double gameDifficulty = defaultDifficulty;
    ArrayList<Unit> units;

    public LevelMaker(int levelNum, double difficulty) {
        unitClasses = getValidEnemyClasses();
        System.out.println("Loaded classes: " + unitClasses);
        random = new Random();
        this.gameDifficulty = difficulty;
        this.levelNum = this.initialLevel = levelNum;
    }

    public static double getDifficultyfromString(String s) {
        if (s.toUpperCase().equals("EASY")) {
            return EASY;
        }
        if (s.toUpperCase().equals("MEDIUM")) {
            return MEDIUM;
        }
        if (s.toUpperCase().equals("HARD")) {
            return HARD;
        }
        if (s.toUpperCase().equals("NIGHTMARE")) {
            return NIGHTMARE;
        }
        return defaultDifficulty;
    }
    private boolean victoryMethodCalled = false;

    public boolean onVictory(Graphics2D g, Rectangle bounds) {
        if (!victoryMethodCalled) {
            //System.out.println("VICTORY METHOD CALLED");
            afterLevel();
            String message = "Level " + (levelNum - 1) + " Success";
            Point p = new Point(0, 3 * bounds.height / 4);

            LevelCompleteTextTask task = new LevelCompleteTextTask(message,
                    bounds.width, bounds.height / 2, p, 60);
            task.setColor(gameDifficulty >= NIGHTMARE ? Color.RED : Color.GREEN);
            init.getGameGUI().getGraphicsControl().addTask(task);
            victoryMethodCalled = true;
            return true;
        }
        return false;
    }

    private ArrayList<Unit> generateUnits(ArrayList<Integer> complexities) {
        ArrayList<Unit> levelUnits = new ArrayList<>();
        for (Integer i : complexities) {
            levelUnits.add(generateEnemy(i));
        }
        return levelUnits;
    }

    public void setSeed(long l) {
        random.setSeed(l);
    }

    private ArrayList<Integer> generateUnitNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        //Generate the number of units in the level
        int cap = ((int) Math.log10(levelNum) + 1) * levelNum / 10;

        cap += levelNum;
        //make the cap scale with the level;
        //random 4 digit number
        int numUnits = (seed[seed.length - 1]) * (seed[0] + 1);
        numUnits %= cap;
        //make sure there is at least 1 unit and multiply by scalingddd ;
        numUnits = (int) (++numUnits * this.getScaling());
        this.numUnits = numUnits;
        int levelComplexity = getDifficulty();

        double[] vals = new double[numUnits];
        Arrays.fill(vals, levelComplexity / (double) numUnits);
        randomizeArray(vals);
        for (int i = 0; i < vals.length; i++) {
            //vals[i] *= numUnits;
            if (vals[i] < 1) {
                vals[i]++;
            }
            numbers.add((int) vals[i]);

        }
//        System.out.println("Cap = " + cap);
//        System.out.println("Total Complexity = " + levelComplexity);
//        System.out.println("# units = " + numUnits);
//        System.out.println("Unit Complexities : " + numbers);
        return numbers;
    }

    public void setup() {
        isSetup = false;
        this.setCompleted(false);
        seed = generateSeed();
        this.victoryMethodCalled = false;
        random.setSeed(seed[0] * seed[1] * seed[2]);
        ArrayList<Integer> unitComplexities = generateUnitNumbers();
        units = generateUnits(unitComplexities);
        makeFormations(units);
        if (levelNum == initialLevel) {
 
        } else {
            clearLevel(true);
        }
        for (Unit u : units) {
            u.onCreate();
        }
        isSetup = true;
        System.out.println("Setup for level " + levelNum + " complete\n\tLevel Difficulty Number: "
                + this.getDifficulty());
    }

    public void checkForVictory() {

        // boolean b = init.getUnitOperationHandler().lock();
        completed = isSetup && (units == null) ? false
                : Collections.disjoint(init.getUnitOperationHandler().getUnits(), units);
        // b = init.getUnitOperationHandler().unlock();
    }

    public void afterLevel() {
        //blah blah
    }

    private Unit generateEnemy(Integer i) {

        // System.out.println(this.unitClasses.size());
        int index = random.nextInt(this.unitClasses.size());
        Class unitClass = unitClasses.get(index);
        unitClass = unitClass.asSubclass(EnemyUnit.class);
        Method[] methods = unitClass.getDeclaredMethods();
        for (int j = 0; j < methods.length; j++) {
            if (methods[j].getName().equals("generate")) {
                Unit x = null;
                try {
                    x = (Unit) methods[j].invoke(null, i);
                } catch (IllegalAccessException z) {
                    z.printStackTrace();
                } catch (InvocationTargetException z) {
                    z.printStackTrace();
                }
                return (x == null) ? generateEnemy(i) : x;
            }
        }

        //System.out.println("NO GENERATE METHOD FOUND for " + unitClass + " , LOOPING AGAIN");
        return generateEnemy(i);
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

        long l = random.nextInt(Integer.MAX_VALUE) * random.nextInt(Integer.MAX_VALUE / 2) + Long.MAX_VALUE / 2;
        int len = (int) Math.log10(l);
        short[] data = new short[len / 3];
        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (l % 1000);
            l /= 1000;
        }
        return data;

    }

    private int getDifficulty() {
        return (int) (Math.pow(getScaling(), levelNum));
    }

    private double getScaling() {
        return BASE_LEVEL_SCALING + getGameDifficulty();
    }

    public double getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(double gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public double getScoreMultiplier() {
        return 1 + this.getGameDifficulty();
    }

    private void makeFormations(ArrayList<Unit> units) {
        ArrayList<BasicEnemy> enemies = new ArrayList<>();
        for (Unit unit : units) {
            if (unit instanceof BasicEnemy) {
                enemies.add((BasicEnemy) unit);
            }
        }
        int next = random.nextInt(numUnits) + 1;
        Formation f = GenerateFormation(next);
        int x = 0;
        while (x < enemies.size()) {
            if (next == 0) {
                next = random.nextInt(10) + 1;
                f = GenerateFormation(next);
            }
            BasicEnemy enemy = enemies.get(x);
            EnemyAI enemyAI = ((EnemyAI) enemy.getAi());
            enemyAI.setFormation(f);
            enemyAI.setNumberInFormation(next);
            Point loc = new Point(f.getPoints()[0][next - 1], f.getPoints()[1][next - 1]);
            enemy.setLocation(loc);
            next--;
            x++;
        }
    }

    public void incrementandSetup() {
        if (isSetup) {
            if (levelNum == initialLevel) {
                Rectangle bounds = init.getGameGUI().getBounds();
                MainCharacter mainCharacter = new MainCharacter(bounds.width / 2, bounds.height / 2, 20);
                ProjectileExplosion explosion = new ProjectileExplosion(mainCharacter);
                explosion.setLocation(mainCharacter.getLocation());
                explosion.onCreate();
                //init.getUnitOperationHandler().pauseAllAI(60);
                mainCharacter.allowFirePermission(true);
                init.getGameGUI().getGraphicsControl().setMainCharacter(mainCharacter);
                setup();
            }
            if (completed) {
                //System.out.println("BLOCK 2");
                this.isSetup = false;
                setup();
            }
        }
        levelNum++;
    }
    private static final int FORMATION_X_SPREAD = 400;
    private static final int FORMATION_Y_SPREAD = 200;

    private Formation GenerateFormation(int num) {
        Formation f = UnitUtilities.getRandomFormation(num, FORMATION_X_SPREAD, FORMATION_Y_SPREAD);
        if (random.nextInt(10) > 4) {
            f.setCenterUnit(init.getGameGUI().getGraphicsControl().getMainCharacter());
            f.setCenteredOnUnit(true);
        }
        return f;

    }

    private void randomizeArray(double[] vals) {
        for (int i = 0; i < RANDOMIZATION_FACTOR; i++) {
            double percentage = random.nextDouble() % 1;
            for (int j = 0; j < vals.length / 2 + vals.length % 2; j++) {
                int removeIndex = (int) (random.nextDouble() % 1 * vals.length);
                int addIndex = (int) (random.nextDouble() % 1 * vals.length);
                double value = vals[removeIndex] * percentage;
                vals[addIndex] += value;
                vals[removeIndex] -= value;
            }
        }
    }

    public static boolean isValidEnemyClass(Class e) {
        boolean val = (e.asSubclass(EnemyUnit.class) == e && e != EnemyUnit.class);
        //System.out.println(e + " " + val);
        return val;
    }

    public static ArrayList<Class> getValidEnemyClasses() {
        ArrayList<Class> returnList = new ArrayList<>();
        String pkg = EnemyUnit.class.getPackage().getName();
        String relPath = pkg.replace('.', '/');

        URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
        if (resource == null) {
            throw new RuntimeException("Unexpected problem: No resource for "
                    + relPath);
        }
        //System.out.println(resource);
        File f = new File(resource.getPath());

        String[] files = f.list();
        //System.out.println(Arrays.toString(files));
        for (int i = 0; i < files.length; i++) {

            String fileName = files[i];
            String className = null;
            String fileNm = null;

            if (fileName.endsWith(".class")) {

                fileNm = fileName.substring(0, fileName.length() - 6);
                className = pkg + '.' + fileNm;
                try {
                    Class c = ClassLoader.getSystemClassLoader().loadClass(className);
                    if (c != null) {
                        if (isValidEnemyClass(c)) {
                            returnList.add(c);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int i) {
        return random.nextInt(i);
    }

}
