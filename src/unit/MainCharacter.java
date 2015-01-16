/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import graphics.RotatingShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import phyics.CollisionConstants;
import weapon.StandardWeapon;

/**
 *
 * @author david.green
 */
public class MainCharacter extends Unit {

    int shapeCount = 4;
    int rotationAngle = 0;
    int size;
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
    }

    @Override
    public void draw(Graphics g) {
        if (size < finalSize) {
            size++;
        }
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        int[][] data = RotatingShape.shape(getLocation(), size, shapeCount, rotationAngle);

        g.drawPolygon(data[0], data[1], data[0].length);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < data[0].length; i++) {
            int[][] shape2 = RotatingShape.shape(new Point(data[0][i], data[1][i]), size / 10, shapeCount, (360 / (i + 1)) * i);
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

    }

    @Override
    public void onCollide(Unit u) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        size = 1;
        this.allowFirePermission(true);
    }

    public void fire(Point target) {
        //for (int i = 0; i < 1000; i++) { //STRESS TESTING FOR BULLETS
        super.fire(target);
        //   }  
    }

    @Override
    public void executeAImove() {
    }

    @Override
    public Area getHitbox() {
        Rectangle hitbox = new Rectangle(this.getLocation());
        hitbox.x -= this.size / 2;
        hitbox.y -= this.size / 2;
        hitbox.setBounds(hitbox.x, hitbox.y, size, size);
        return new Area(hitbox);

    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public void allowFirePermission(boolean canFire) {
        super.allowFirePermission(canFire); 
        if (canFire && this.getWeapon() == null){
         this.setWeapon(new StandardWeapon(this));
        //this.setWeapon(new MultishotWeapon(this, 4, 10));
        }
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.FRIENDLY_UNIT;
    }
    
    

}
