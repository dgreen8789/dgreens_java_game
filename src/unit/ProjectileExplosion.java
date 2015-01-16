/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import main.init;
import phyics.CollisionConstants;

/**
 *
 * @author david.green
 */
public class ProjectileExplosion extends Unit {

    private Unit createOnFinish;
    private int numProjectilesPerFrame;
    private int frameCount;
    private Color[] colors;
    private boolean speciallyColored;
    
    
    public static final Color[] DEFAULT_PROJECTILE_COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.WHITE};//, Color.GREEN, Color.BLUE};
    public static final int MINIMUM_PROJECTILE_RADIUS = 1;
    public static final int MAXIMUM_PROJECTILE_RADIUS = 5;
    public static final int PROJECTILE_SPEED = 3;
    public static final int DEFAULT_PROJECTILES_PER_FRAME = 200;
    public static final int DEFAULT_FRAME_COUNT = 90;
    public static final int FRAMES_BETWEEN_SIZE_DECREASE = 20;

    public ProjectileExplosion(Unit createOnFinish, int numProjectilesPerFrame,int frameCount, Color[] colors) {
        super();
        this.createOnFinish = createOnFinish;
        this.frameCount = frameCount;
        this.numProjectilesPerFrame = numProjectilesPerFrame;
        this.colors = colors;
        speciallyColored = colors.length > 0;
        this.setLocation(createOnFinish.getLocation());
    }

    public ProjectileExplosion(Unit createOnFinish) {
        this(createOnFinish,
                (int) (DEFAULT_PROJECTILES_PER_FRAME), DEFAULT_FRAME_COUNT,
                new Color[0]);

    }

    @Override
    public void draw(Graphics g) {
        if (frameCount > 0) {
            Rectangle bounds = init.getGameGUI().getBounds();
            Point target = new Point((int) (Math.random() * bounds.width), (int) (Math.random() * bounds.height));
            int radius = (int) (Math.random() * MAXIMUM_PROJECTILE_RADIUS) 
                    + MINIMUM_PROJECTILE_RADIUS
                    + frameCount / FRAMES_BETWEEN_SIZE_DECREASE;
            Color c = (isSpeciallyColored()) ? colors[(int) (Math.random() * colors.length)]
                    : DEFAULT_PROJECTILE_COLORS[(int) (Math.random() * DEFAULT_PROJECTILE_COLORS.length)];
            for (int i = 0; 
                     i < ((numProjectilesPerFrame == 0) 
                     ? DEFAULT_PROJECTILES_PER_FRAME : numProjectilesPerFrame); i++) {
                StandardProjectile newProjectile = new StandardProjectile(c, radius, this.getX(), this.getY(), target,
                CollisionConstants.GRAPHICAL_PROJECTILE);
                newProjectile.setSpeed(PROJECTILE_SPEED);
                newProjectile.setCollidable(false);
                newProjectile.setHittable(false);
                newProjectile.onCreate();
            }
            frameCount--;
        }
        if (frameCount <= 0) {
            this.onDeath();
        }

    }

    @Override
    public void onCollide(Unit u) {
    }

    @Override
    public void fire(Point target) {

    }

    @Override
    public Area getHitbox() {
        return null;
    }

    public boolean isSpeciallyColored() {
        return speciallyColored;

    }

    @Override
    public void onDeath() {
        this.createOnFinish.onCreate();
        super.onDeath(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.UNTARGETABlE;
    }
    
    

}
