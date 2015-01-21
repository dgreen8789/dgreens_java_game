/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.awt.Color;

/**
 *
 * @author David
 */
public class DestroyableTarget extends Target {

    int health;

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
            if (health == 0) {
                super.onDeath();
            } else {
                super.onCollide(u);
            }
        }
    }
}
