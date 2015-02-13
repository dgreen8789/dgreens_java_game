/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_enemy;

import AI.Formation;
import graphics.RotatingShape;
import java.awt.Color;
import java.awt.Graphics2D;
import level.LevelMaker;
import main.init;
import unit.ColoredUnit;
import unit.Unit;
import unit.UnitUtilities;

/**
 *
 * @author david.green
 */
public class StealthEnemy extends BasicEnemy implements ColoredUnit {

    private boolean stealthed;
    private int stealthChance;
    private int StealthFrames;

    public StealthEnemy(int health) {
        super(health);
    }

    public StealthEnemy(int health, int x, int y, Formation f, int numinFormation) {
        super(health, x, y, f, numinFormation);
    }

    private int frame = 0;
    private int currentStealthFrames;

    @Override
    public void draw(Graphics2D g) {
        stealthed = currentStealthFrames > 0;
        currentStealthFrames --;
        if (!isStealthed()) {
            g.setColor(getColor());
            int[][] data = RotatingShape.shape(getLocation(), getSize(), 3, ++frame);
            g.drawPolygon(data[0], data[1], data[0].length);
            frame %= 360;
            UnitUtilities.drawHealth(g, this);
            stealthed = init.getGameGUI().getLevel().nextInt(100)
                < stealthChance && currentStealthFrames < -30;
            if (isStealthed()){
                currentStealthFrames = StealthFrames;
            }
        }
    }

    @Override
    public int getComplexity() {
        return 2 * super.getComplexity();
    }

    @Override
    public int getScore() {
        return 2 * super.getScore();
    }

    @Override
    public void onCollide(Unit u) {
        if (!isStealthed()) {
            super.onCollide(u);
        }
    }

    public int getSteathChance() {
        return stealthChance;
    }

    public void setStealthChance(int steathChance) {
        this.stealthChance = steathChance;
    }

    public int getStealthFrames() {
        return StealthFrames;
    }

    public void setStealthFrames(int StealthFrames) {
        this.StealthFrames = StealthFrames;
    }

    public static StealthEnemy generate(int complexity) {
        LevelMaker l = init.getGameGUI().getLevel();
        Formation f = UnitUtilities.getRandomFormation(l.nextInt(5), l.nextInt(200), l.nextInt(200));
        StealthEnemy x = new StealthEnemy(complexity / 2, 0, 0, f, 0);
        x.setLocation(UnitUtilities.getRandomLocation(x));
        x.setStealthFrames(l.nextInt(60) + 60);
        x.setStealthChance(l.nextInt(50));
        x.setColor(Color.WHITE);
        return x;
    }

    public boolean isStealthed() {
        return stealthed;
    }

}
