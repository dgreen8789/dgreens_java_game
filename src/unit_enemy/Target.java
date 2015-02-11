/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_enemy;

import AI.AI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import main.init;
import phyics.CollisionConstants;
import unit.ColoredUnit;
import unit.ColoredUnit;
import unit.ColoredUnit;
import unit.StandardProjectile;
import unit.StandardProjectile;
import unit.StandardProjectile;
import unit.Unit;
import unit.Unit;
import unit.Unit;
import unit.UnitUtilities;
import unit.UnitUtilities;
import unit.UnitUtilities;
import unit_enemy.EnemyUnit;

/**
 *
 * @author david.green
 */
public class Target extends EnemyUnit implements ColoredUnit {

    private Color color;

    public Target() {
        this(0, 0, 100);
        this.setLocation(UnitUtilities.getRandomLocation(this));
    }

    public Target(int x, int y) {
        this(x, y, 100);
    }

    public Target(int x, int y, int size) {
        this(x, y, size, Color.RED);
    }

    public Target(int x, int y, int size, Color color) {
        super(x, y, 1);
        this.color = color;
        setSize(size);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.getX() - getSize() / 2, this.getY() - getSize() / 2, getSize(), getSize());
        if (getSize() > 6) {
            g.setColor(Color.WHITE);
            g.fillOval(this.getX() - 2, this.getY() - 2, 4, 4);
        }

    }

    @Override
    public void onCollide(Unit u) {
        if (u instanceof StandardProjectile) {
            init.getGameGUI().getGraphicsControl().addScore(getScore());
            Point loc = UnitUtilities.getRandomLocation(this);
            this.setLocation(loc);
            //System.out.println(loc.toString());
        }

    }

    @Override
    public void fire(Point target) {
    }

    @Override
    public Area getHitbox() {
        Ellipse2D hitbox = new Ellipse2D.Double();
        Dimension2D hitboxSize = new Dimension();
        hitboxSize.setSize(getSize(), getSize());
        Point hitboxLocation = (Point) this.getLocation().clone();
        hitboxLocation.x -= getSize() / 2;
        hitboxLocation.y -= getSize() / 2;
        hitbox.setFrame(hitboxLocation, hitboxSize);
        return new Area(hitbox);
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

    @Override
    public boolean isValidLocation(Point location) {
        Rectangle bounds = (Rectangle)init.getGameGUI().getBounds().clone();
        int change = this.getSize() * 2;
        bounds.x += change;
        bounds.y += change;
        bounds.width -= change;
        bounds.height -= change;
        return bounds.contains(location);
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.NEUTRAL_UNIT;
    }

    @Override
    public int getComplexity() {
        return 1;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public void specialAbility() {
    }

    

    
}
