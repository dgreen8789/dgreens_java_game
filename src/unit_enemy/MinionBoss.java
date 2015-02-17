/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit_enemy;

import AI.AI;
import AI.EnemyAI;
import AI.Formation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;
import level.LevelMaker;
import main.init;
import unit.Unit;
import weapon.Weapon;

/**
 *
 * @author David
 */
public class MinionBoss extends BossEnemy {

    protected ArrayList<Unit> minions;
    protected BossEnemy master;

    public MinionBoss(BossEnemy master) {
        super(master.getMaxHealth());
        this.master = master;
        minions = new ArrayList<>();
    }

    public static MinionBoss generate(int complexity) {
        LevelMaker l = init.getGameGUI().getLevel();
        if (complexity > 200) {
            BossEnemy x = BossEnemy.generate(complexity - 100);
            MinionBoss z = new MinionBoss(x);
            int numMinions = l.nextInt(5) + 5;
            int complexityPer = 10;//(complexity - 100) / minions;
            Formation f = new Formation(numMinions, Formation.GEOMETRIC_FORMATION, new Point(0, 0));
            f.setCenterUnit(x);
            f.setCenteredOnUnit(true);
            f.setRotation(true);
            f.setRotationPerFrame(l.nextInt(3));
            for (int i = 0; i < numMinions; i++) {
                BasicEnemy newEnemy = BasicEnemy.generate(complexityPer);
                ((EnemyAI) newEnemy.getAi()).setFormation(f);
                ((EnemyAI) newEnemy.getAi()).setNumberInFormation(i);
                newEnemy.setLocation(x.getLocation());
                z.minions.add(newEnemy);
                //System.out.println("Minion number " + i + " for boss " + x + " created.\n\tHash = " + newEnemy);
            }
            f.setDistance(l.nextInt(100) + x.getSize() + z.minions.get(0).getSize());
            //System.out.println("MINION BOSS CREATED");
            return z;
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        for (Unit minion : minions) {
            minion.onCreate();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        master.draw(g);
    }

    @Override
    public Area getHitbox() {
        return master.getHitbox();
    }

    @Override
    public int getComplexity() {
        int minionComplexity = 0;
        for (Unit minion : minions) {
            minionComplexity += minion.getComplexity();
        }
        return master.getComplexity() + minionComplexity;
    }

    @Override
    public void specialAbility() {
        master.specialAbility();
    }

    @Override
    public int getScore() {
        return master.getScore();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        System.out.print("MINIONS KILLED ");
        for (Unit minion : minions) {
            minion.onDeath();
        }
    }

    @Override
    public void onCollide(Unit u) {
        System.out.println("CALLED");
        master.onCollide(u);
        onDeath();
    }

    @Override
    public int getCollisionConstant() {
        return master.getCollisionConstant();
    }

    @Override
    public int getSpeed() {
        return master.getSpeed();
    }

    @Override
    public Color getColor() {
        return master.getColor();
    }

    @Override
    public void setColor(Color color) {
        master.setColor(color);
    }

    @Override
    public Point getLocation() {
        return master.getLocation();
    }

    @Override
    public void setLocation(Point location) {
        master.setLocation(location);
    }

    @Override
    public void setLocation(int x, int y) {
        master.setLocation(x, y);
    }

    @Override
    public AI getAi() {
        return master.getAi();
    }

    @Override
    public void setAi(AI ai) {
        master.setAi(ai);
    }

    @Override
    public boolean isHittable() {
        return master.isHittable();
    }

    @Override
    public void setHittable(boolean hittable) {
        master.setHittable(hittable);
    }

    @Override
    public boolean isCollidable() {
        return master.isCollidable();
    }

    @Override
    public void setCollidable(boolean collidable) {
        master.setCollidable(collidable);
    }

    @Override
    public void moveX(int xDelta) {
        master.moveX(xDelta);
    }

    @Override
    public void moveY(int yDelta) {
        master.moveY(yDelta);
    }

    @Override
    public int getX() {
        return master.getX();
    }

    @Override
    public int getY() {
        return master.getY();
    }

    @Override
    public void fire(Point target) {
        master.fire(target);
    }

    @Override
    public void executeAImove() {
        master.executeAImove();
    }

    @Override
    public boolean isValidLocation(Point location) {
        return master.isValidLocation(location);
    }

    @Override
    public int compareTo(Object o) {
        return master.compareTo(o);
    }

    @Override
    public boolean canFire() {
        return master.canFire();
    }

    @Override
    public void allowFirePermission(boolean canFire) {
        master.allowFirePermission(canFire);
    }

    @Override
    public Weapon getWeapon() {
        return master.getWeapon();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        master.setWeapon(weapon);
    }

    @Override
    public int getSize() {
        return master.getSize();
    }

    @Override
    public void setSize(int size) {
        master.setSize(size);
    }

    @Override
    public int getHealth() {
        return master.getHealth();
    }

    @Override
    public void setHealth(int health) {
        master.setHealth(health);
    }

    @Override
    public int getMaxHealth() {
        return master.getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        master.setMaxHealth(maxHealth);
    }

    @Override
    public void setSpecialAbilityCooldown(int specialAbilityCooldown) {
        master.setSpecialAbilityCooldown(specialAbilityCooldown);
    }

    @Override
    public int getSpecialAbilityCooldown() {
        return master.getSpecialAbilityCooldown();
    }

    @Override
    public void setSpeed(int speed) {
        master.setSpeed(speed);
    }

    @Override
    public void setInvincible(boolean b) {
        master.setInvincible(b);
    }

    @Override
    public boolean isInvincible() {
        return master.isInvincible();
    }

}
