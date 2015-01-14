/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.Formation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import main.init;

/**
 *
 * @author david.green
 */
public class MainCharacter extends Unit {

    int shapeCount = 4;
    int rotationAngle = 0;
    public final int BULLET_SPEED = 20;

    public MainCharacter() {
        this(0, 0);
    }

    public MainCharacter(int x, int y) {
        super(x, y);
        System.out.println("WOO");
    }

    @Override
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        int[][] data = Formation.shape(getLocation(), 100, shapeCount, rotationAngle);

        g.drawPolygon(data[0], data[1], data[0].length);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < data[0].length; i++) {
            int[][] shape2 = Formation.shape(new Point(data[0][i], data[1][i]), 10, shapeCount, (360 / (i + 1)) * i);
            g.drawPolygon(shape2[0], shape2[1], shape2[0].length);
        }
        shapeCount = (shapeCount > 15) ? 3 : shapeCount;
        rotationAngle++;
        if (rotationAngle == 360) {
            rotationAngle %= 360;
            shapeCount++;
        }
        g.setColor(c);

    }

    @Override
    public void onHit() {
    }

    @Override
    public void onCollide() {
    }

    @Override
    public void onCreate() {
        //TODO: Cool Animation
    }

    public void fire() {
        StandardProjectile x = new StandardProjectile(Color.RED, 3, getX(), getY(), init.getGameGUI().getMousePosition());
        x.setSpeed(BULLET_SPEED);
        init.getGameGUI().getGraphicsControl().addUnit(x); 
        
    }

    @Override
    public void executeAImove() {
    }
    
}
