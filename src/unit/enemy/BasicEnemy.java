/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit.enemy;

import AI.EnemyAI;
import AI.Formation;
import graphics.RotatingShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import main.init;
import unit.ProjectileExplosion;
import unit.UnitUtilities;

/**
 *
 * @author david.green
 */
public class BasicEnemy extends EnemyUnit {
    private static final int ENEMY_SPEED = 10;
    private int angle = 0;
    public BasicEnemy(int health) {
        super(health);
    }

    public BasicEnemy(int health, int x, int y, Formation f, int numinFormation) {
        super(health, x, y);
        this.setSize(10);
        this.setAi(new EnemyAI(this, f, numinFormation));
        score = health;
        setColor(Color.ORANGE);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        int[][] data = RotatingShape.shape(getLocation(), getSize(), 4, angle);
        g.drawPolygon(data[0], data[1], data[0].length);
        angle++;
        angle %= 360;
        g.setColor(Color.MAGENTA);
        UnitUtilities.drawHealth(g, this);
    }

    @Override
    public Area getHitbox() {
        return new Area(new Rectangle(getX() - (getSize() / 2),
                getY() - (getSize() / 2), getSize(), getSize()));
    }

    @Override
    public int getComplexity() {
        return health;
    }

    @Override
    
    public void onDeath(){
        ProjectileExplosion explosion = new ProjectileExplosion(null, 5, 20, new Color[0]);
        explosion.setLocation(getLocation());
        explosion.setProjectileMoves(5);
        explosion.onCreate();
        init.getGameGUI().getGraphicsControl().addScore(getScore());
        super.onDeath();
    }

    @Override
    public void specialAbility() {
    }

    @Override
    public int getSpeed() {
        return ENEMY_SPEED;
    }
    
    
    
}
