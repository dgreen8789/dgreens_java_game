/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import graphics.RotatingShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import phyics.CollisionConstants;
import weapon.MultishotWeapon;

/**
 *
 * @author david.green
 */
public class MainCharacter extends Unit {

    int shapeCount = 4;
    int rotationAngle = 0;
    int finalSize;

    public MainCharacter() {
        this(0, 0, 100);
    }

    public MainCharacter(int x, int y) {
        this(x, y, 100);
    }

    public MainCharacter(int x, int y, int size) {
        super(x, y);
        this.finalSize = size;
        setHealth(1000000);
    }

    @Override
    public void draw(Graphics2D g) {
        if (getSize() < finalSize) {
            setSize(getSize() + 1);
        }
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        int[][] data = RotatingShape.shape(getLocation(), getSize(), shapeCount, rotationAngle);
        g.drawPolygon(data[0], data[1], data[0].length);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < data[0].length; i++) {
            int[][] shape2 = RotatingShape.shape(new Point(data[0][i], data[1][i]), getSize() / 10, shapeCount, (360 / (i + 1)) * i);
            g.drawPolygon(shape2[0], shape2[1], shape2[0].length);
        }
        shapeCount = (shapeCount > 15) ? 3 : shapeCount;
        rotationAngle++;
        if (rotationAngle == 360) {
            rotationAngle %= 360;
            shapeCount++;
        }
        g.setColor(Color.WHITE);
        g.drawOval(getX() - 5, getY() - 5, 10, 10);
        g.setColor(c);
        UnitUtilities.drawHealth(g, this);
    }

    @Override
    public void onCollide(Unit u) {
        System.out.println("COLLISION");
        if (u.getCollisionConstant() == CollisionConstants.ENEMY_UNIT) {
            this.health--;
        }
        if (u.getCollisionConstant() == CollisionConstants.ENEMY_PROJECTILE
                || u.getCollisionConstant() == CollisionConstants.NEUTRAL_PROJECTILE) {
            System.out.println("CALLED");
            this.health -= ((StandardProjectile) u).getDamage();
        }
        if (this.health < 0) {
            onDeath();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setSize(1);
        this.allowFirePermission(true);
    }

    @Override
    public void fire(Point target) {
        //for (int i = 0; i < 100; i++) { //STRESS TESTING FOR BULLETS
        super.fire(target);
        //}  
    }

    @Override
    public void executeAImove() {
    }

    @Override
    public Area getHitbox() {
        Rectangle hitbox = new Rectangle(this.getLocation());
        hitbox.x -= getSize() / 2;
        hitbox.y -= getSize() / 2;
        hitbox.setBounds(hitbox.x, hitbox.y, getSize(), getSize());
        return new Area(hitbox);

    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public void allowFirePermission(boolean canFire) {
        super.allowFirePermission(canFire);
        if (canFire && this.getWeapon() == null) {
            //this.setWeapon(new StandardWeapon(this));
            this.setWeapon(new MultishotWeapon(this, 5, 10, 1));
        }
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.FRIENDLY_UNIT;
    }

    @Override
    public int getComplexity() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

}
