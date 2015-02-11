/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_enemy;

import unit_enemy.EnemyUnit;
import graphics.GraphicsUtilities;
import java.awt.Color;
import java.awt.Graphics2D;
import main.init;
import unit.ProjectileExplosion;
import unit.ProjectileExplosion;
import unit.StandardProjectile;
import unit.StandardProjectile;
import unit.Unit;
import unit.Unit;
import unit.UnitUtilities;
import unit.UnitUtilities;

/**
 *
 * @author David
 */
public class DestroyableTarget extends Target {

    public DestroyableTarget(int health) {
        setHealth(health);
        this.maxHealth = health;
    }

    public DestroyableTarget(int health, int x, int y) {
        super(x, y);
    }

    public DestroyableTarget(int health, int x, int y, int size) {
        super(x, y, size);
    }

    public DestroyableTarget(int health, int x, int y, int size, Color color) {
        super(x, y, size, color);
        setHealth(health);
        this.maxHealth = health;
    }

    @Override
    public void onCollide(Unit u) {
        if (u instanceof StandardProjectile) {
            setHealth(getHealth() - ((StandardProjectile) u).getDamage());
            if (getHealth() <= 0) {
                init.getGameGUI().getGraphicsControl().addScore(1);
                onDeath();
            } else {
                super.onCollide(u);
            }
        }
    }

    @Override

    public void draw(Graphics2D g) {
        super.draw(g);
        UnitUtilities.drawHealth(g, this);
    }

    @Override
    public void onDeath() {
        ProjectileExplosion explosion = new ProjectileExplosion(null, 5, 20, new Color[0]);
        explosion.setLocation(getLocation());
        explosion.setProjectileMoves(5);
        explosion.onCreate();
        super.onDeath();

    }

    public static DestroyableTarget generate(int complexity) {
        if (complexity > 10) {
            return null;
        }
        DestroyableTarget t = new DestroyableTarget(complexity);
        t.setLocation(UnitUtilities.getRandomLocation(t));
        t.setSize(15);
        return t;
    }
}
