/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_enemy;

import AI.EnemyAI;
import AI.Formation;
import graphics.RotatingShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import level.LevelMaker;
import main.init;
import unit.ProjectileExplosion;
import unit.UnitUtilities;
import weapon.MultishotWeapon;

/**
 *
 * @author david.green
 */
public class BossEnemy extends EnemyUnit {

    private static final int SHOT_SPACING = 5;
    private static final int SHOT_DAMAGE = 100;
    private static final int NUM_SHOTS = 2;
   
    public static BossEnemy generate(int complexity) {
        LevelMaker l = init.getGameGUI().getLevel();
        if (complexity < 100) {
            return null;
        }
        BossEnemy x = new BossEnemy(10 * complexity);
        Formation f = UnitUtilities.getRandomFormation(l.nextInt(4), 500, 500);
        f.setRotationPerFrame(1);
        EnemyAI ai = new EnemyAI(x, f, 0);
        x.allowFirePermission(true);
        ai.setFireChance(75);
        x.setAi(ai);
        x.setColor(Color.RED);
        x.setSize(30);
        x.setWeapon(new MultishotWeapon(x, NUM_SHOTS, SHOT_SPACING, SHOT_DAMAGE));
        x.setLocation(UnitUtilities.getRandomLocation(x));
        return x;
    }

    public BossEnemy(int health) {
        super(health);
        
    }
    private int frame;

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        int[][] data = RotatingShape.shape(getLocation(), getSize(), 8, ++frame);
        g.drawPolygon(data[0], data[1], data[0].length);
        frame %= 360;
        UnitUtilities.drawHealth(g, this);
    }

    @Override
    public Area getHitbox() {
        return new Area(new Rectangle(getX() - (getSize() / 2),
                getY() - (getSize() / 2), getSize(), getSize()));
    }

    @Override
    public int getComplexity() {
        return maxHealth * 10;
    }

    @Override
    public void specialAbility() {
    }

    @Override
    public int getScore() {
        return (maxHealth / 10) * 1000; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDeath() {
        ProjectileExplosion explosion = new ProjectileExplosion(null, 20, 40, new Color[0]);
        explosion.setLocation(getLocation());
        explosion.setProjectileMoves(5);
        explosion.onCreate();
        init.getGameGUI().getGraphicsControl().addScore(getScore());
        System.out.println("BOSS KILLED");
        super.onDeath();
    }

}
