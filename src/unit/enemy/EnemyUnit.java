/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unit.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import phyics.CollisionConstants;
import unit.ColoredUnit;
import unit.StandardProjectile;
import unit.Unit;

/**
 *
 * @author david.green
 */
public abstract class EnemyUnit extends Unit implements ColoredUnit {
    private Color color;
    public EnemyUnit(int health) {
        this.health = health;
    }

    public EnemyUnit(int health, int x, int y) {
        super(x, y);
        this.health = health;
    }
    
    @Override
    public abstract void draw(Graphics2D g);
    

    @Override
    public void onCollide(Unit u){
        if (u.getCollisionConstant() == CollisionConstants.FRIENDLY_UNIT){
            this.health--;
        }
        if (u.getCollisionConstant() == CollisionConstants.FRIENDLY_PROJECTILE || 
                u.getCollisionConstant() == CollisionConstants.NEUTRAL_PROJECTILE){
            this.health -= ((StandardProjectile) u).getDamage();
        }
    }

    @Override
    public abstract Area getHitbox();

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.ENEMY_UNIT;
    }

    @Override
    public abstract int getComplexity();
    @Override
    public abstract int getScore();

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    
}
