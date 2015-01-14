/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.AI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import main.init;

/**
 *
 * @author david.green
 */
public class Target extends Unit implements ColoredUnit {

    private Color color;
    private int size;

    public Target() {
        this(0, 0, 100);
    }

    public Target(int x, int y) {
        this(x, y, 100);
    }

    public Target(int x, int y, int size) {
        this(x, y, size, Color.RED);
    }

    public Target(int x, int y, int size, Color color) {
        super(x, y);
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(this.getX() - size / 2, this.getY() - size / 2, size, size);
        if (size > 6) {
            g.setColor(Color.WHITE);
            g.fillOval(this.getX() - 2, this.getY() - 2, 4, 4);
        }

    }

    @Override
    public void onCollide(Unit u) {
        if (u instanceof StandardProjectile) {
            init.getGameGUI().getGraphicsControl().addScore(1);
            this.setLocation((int) (Math.random() * init.getGameGUI().getBounds().width),
                    (int) (Math.random() * init.getGameGUI().getBounds().height));
        }

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void fire() {
    }

    @Override
    public Ellipse2D getHitbox() {
        Ellipse2D hitbox = new Ellipse2D.Double();
        Dimension2D hitboxSize = new Dimension();
        hitboxSize.setSize(size, size);
        hitbox.setFrame(this.getLocation(), hitboxSize);
        return hitbox;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void executeAImove() {
    }

    @Override
    public void setAi(AI ai) {
    }

    @Override
    public AI getAi() {
        return null; 
    }
    

}
