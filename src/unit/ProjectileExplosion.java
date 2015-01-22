/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import main.init;
import phyics.CollisionConstants;
import phyics.MultipleUnitAdditionOperation;

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
    private int projectileMoves;

    public static final Color[] DEFAULT_PROJECTILE_COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.WHITE};//, Color.GREEN, Color.BLUE};
    public static final int MINIMUM_PROJECTILE_RADIUS = 1;
    public static final int MAXIMUM_PROJECTILE_RADIUS = 5;
    public static final int PROJECTILE_SPEED = 3;
    public static final int DEFAULT_PROJECTILES_PER_FRAME = 20;
    public static final int DEFAULT_FRAME_COUNT = 15;
    public static final int FRAMES_BETWEEN_SIZE_DECREASE = 20;

    public ProjectileExplosion(Unit createOnFinish, int numProjectilesPerFrame, int frameCount, Color[] colors) {
        super();
        this.createOnFinish = createOnFinish;
        this.frameCount = frameCount;
        this.numProjectilesPerFrame = numProjectilesPerFrame;
        this.colors = colors;
        speciallyColored = colors.length != 0;
    }

    public ProjectileExplosion(Unit createOnFinish) {
        this(createOnFinish,
                (int) (DEFAULT_PROJECTILES_PER_FRAME), DEFAULT_FRAME_COUNT,
                new Color[0]);

    }

    @Override
    public void draw(Graphics2D g) {
        if (frameCount > 0) {
            Rectangle bounds = init.getGameGUI().getBounds();
            ArrayList<Unit> newProjectiles = new ArrayList<>();
            for (int i = 0;
                    i < ((numProjectilesPerFrame == 0)
                            ? DEFAULT_PROJECTILES_PER_FRAME : numProjectilesPerFrame); i++) {
                Point target = new Point((int) (Math.random() * bounds.width), (int) (Math.random() * bounds.height));
                int radius = (int) (Math.random() * MAXIMUM_PROJECTILE_RADIUS)
                        + MINIMUM_PROJECTILE_RADIUS
                        + frameCount / FRAMES_BETWEEN_SIZE_DECREASE;
                Color c = (isSpeciallyColored()) ? colors[(int) (Math.random() * colors.length)]
                        : DEFAULT_PROJECTILE_COLORS[(int) (Math.random() * DEFAULT_PROJECTILE_COLORS.length)];
                StandardProjectile newProjectile;
                if (projectileMoves != 0) {
                    newProjectile = new SelfDeletingProjectile(c, radius, this.getX(), this.getY(), target,
                            CollisionConstants.GRAPHICAL_PROJECTILE, PROJECTILE_SPEED, projectileMoves);
                } else {
                    newProjectile = new StandardProjectile(c, radius, this.getX(), this.getY(), target,
                            CollisionConstants.GRAPHICAL_PROJECTILE, PROJECTILE_SPEED);
                }
                newProjectile.setCollidable(false);
                newProjectile.setHittable(false);
                newProjectiles.add(newProjectile);
            }
            frameCount--;
            init.getUnitOperationHandler().addOperation(new MultipleUnitAdditionOperation(newProjectiles));
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
        if (createOnFinish != null) {
            this.createOnFinish.onCreate();
        }
        super.onDeath(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCollisionConstant() {
        return CollisionConstants.UNTARGETABlE;
    }

    @Override
    public int getComplexity() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

    public int getProjectileMoves() {
        return projectileMoves;
    }

    public void setProjectileMoves(int projectileMoves) {
        this.projectileMoves = projectileMoves;
    }
    

}
