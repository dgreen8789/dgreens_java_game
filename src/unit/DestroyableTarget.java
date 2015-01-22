/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import graphics.GraphicsUtilities;
import java.awt.Color;
import java.awt.Graphics2D;
import main.init;

/**
 *
 * @author David
 */
public class DestroyableTarget extends Target {

    public DestroyableTarget(int health) {
        this.health = health;
    }

    public DestroyableTarget(int health, int x, int y) {
        super(x, y);
        this.health = health;
    }

    public DestroyableTarget(int health, int x, int y, int size) {
        super(x, y, size);
        this.health = health;
    }

    public DestroyableTarget(int health, int x, int y, int size, Color color) {
        super(x, y, size, color);
        this.health = health;
    }

    @Override
    public void onCollide(Unit u) {
        if (u instanceof StandardProjectile) {
            health--;
            if (health <= 0) {
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
        int characterAllowance = (getSize() / 10 + 1);
        if (characterAllowance == 0) {
            characterAllowance++;
        }
        characterAllowance *= (getSize() / 4);
        g.setFont(GraphicsUtilities.fillRect(Integer.toString(health), g,
                (getSize() / 2) + characterAllowance, getSize()));
        g.drawString(Integer.toString(health),
                getX() - characterAllowance * Integer.signum(getX()),
                getY() - (getSize() * Integer.signum(getY())));
    }

    @Override
    public void onDeath() {
        ProjectileExplosion explosion = new ProjectileExplosion(null, 5, 20, new Color[0]);
        explosion.setLocation(getLocation());
        explosion.setProjectileMoves(5);
        explosion.onCreate();
        super.onDeath();

    }

}
