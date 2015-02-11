/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unit_enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import phyics.CollisionConstants;
import unit.ColoredUnit;
import unit.StandardProjectile;
import unit.Unit;
import weapon.StandardWeapon;

/**
 *
 * @author david.green
 */
public abstract class EnemyUnit extends Unit implements ColoredUnit {
    private Color color;
    protected int score;
    public EnemyUnit(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    public EnemyUnit(int health, int x, int y) {
        super(x, y);
        this.health = health;
        this.maxHealth = health;
        this.allowFirePermission(true);
        this.setWeapon(new StandardWeapon(this, 1));
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
        if (this.health < 0) onDeath();
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
    public int getScore(){
        return score;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
    
    public static <T extends EnemyUnit> T generate(int complexity){
        throw new UnsupportedOperationException("NOPE");
    }



    
}
