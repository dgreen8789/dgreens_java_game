package graphics;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicLong;
import main.init;
import phyics.CollisionConstants;
import unit.MainCharacter;
import unit.ProjectileExplosion;
import unit.Target;
import unit.Unit;

/**
 *
 * @author David
 */
public class GraphicsController {

    private boolean lock;
    private final Insets insets;
    private final ArrayList<Unit> units;
    private Unit mainCharacter;
    private boolean firstRender = true;
    private Rectangle oldBounds;
    private final AtomicLong score;
    private Object owner;
    private final boolean drawHitboxes = false; //Debug Line

    public GraphicsController(Insets insets) {
        this.insets = insets;
        units = new ArrayList<Unit>();
        score = new AtomicLong(0);

    }

    /**
     * Executes the render instructions for the board
     *
     * @param g the graphics context
     * @param width the width of the canvas to draw on
     * @param height the height of the canvas to draw on
     */
    void render(Graphics2D g, int width, int height, Insets insets) {
        if (firstRender) {
            firstRender();
        }
        width -= insets.left + insets.right;
        height -= insets.top + insets.bottom;
        if (!init.getGameGUI().getBounds().equals(oldBounds)) {
            scale();
            oldBounds = init.getGameGUI().getBounds();
        }
        for (int i = 0; i < units.size(); i++) {
            try {
                units.get(i).draw(g);
                if (drawHitboxes) {
                    g.setColor(Color.MAGENTA);
                    GraphicsController.drawArea(units.get(i).getHitbox(), g);

                }
            } catch (ConcurrentModificationException e) {
                if (units.get(i) == (null)) {
                    units.remove(i);
                } else {
                    //i--;
                }
            } catch (NullPointerException e) {
                System.out.println("AAAA");
            }
        }
        drawScore(0, 0, 50, 20, g);
    }

    public void addUnit(Unit u, Object o) {
        if (o == owner) {
            int z = u.getCollisionConstant();
            int x = init.getGameGUI().getCollisionHandler().getBeginningIndex(z);
//        System.out.println("Collision Constant = #" + z + " or " + CollisionConstants.getCodeName(z));
//        System.out.println("Index = " + z);
//        System.out.println("Index List: " + Arrays.toString(init.getGameGUI().getCollisionHandler().getIndexes()));
//        System.out.println(units.contains(mainCharacter) + "\n");
            units.add(x, u);
            init.getGameGUI().getCollisionHandler().updateListLocs(z, true);
        }
    }

    public void removeUnit(Unit u, Object o) {
        if (o == owner) {
            if (units.remove(u)) //Is this slowing my program down
            {

                init.getGameGUI().getCollisionHandler().updateListLocs(u.getCollisionConstant(), false);
            }
        }

    }

    public Unit getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(Unit mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    private void firstRender() {
        Rectangle bounds = init.getGameGUI().getBounds();
        firstRender = false;
        mainCharacter = new MainCharacter(bounds.width / 2, bounds.height / 2, 20);
        oldBounds = bounds;
        ProjectileExplosion explosion = new ProjectileExplosion(mainCharacter);
        explosion.onCreate();
        Target g = new Target();
        g.setSize(10);
        g.setColor(Color.BLUE);
        g.onCreate();
    }

    private void scale() {
        double xRatio = init.getGameGUI().getBounds().getWidth() / oldBounds.width * 1.0;
        double yRatio = init.getGameGUI().getBounds().getHeight() / oldBounds.height * 1.0;
        for (Unit unit : units) {
            unit.setLocation((int) (unit.getX() * xRatio), (int) (unit.getY() * yRatio));
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public final long getScore() {
        return score.get();
    }

    public final void addScore(long l) {
        score.addAndGet(l);
    }

    private void drawScore(int x, int y, int width, int height, Graphics2D g) {
        Font f = g.getFont();
        g.setColor(Color.WHITE);
        g.setFont(init.getGameGUI().fillRect(Long.toString(this.getScore()), g, width, height));
        g.drawString(Long.toString(this.getScore()), 0, (int) (g.getFontMetrics()
                .getLineMetrics(Long.toString(this.getScore()), g).getHeight()));
        g.setFont(f);
    }

    public int getGameWidth() {
        return oldBounds.width - (this.insets.right + this.insets.left);
    }

    public int getGameHeight() {
        return oldBounds.height - (this.insets.top + this.insets.bottom);
    }

    public static void drawArea(Area a, Graphics g) {
        PathIterator path = a.getPathIterator(null);
        double[] pathData = new double[6];
        ArrayList<Point> lineData = new ArrayList<>();
        while (!path.isDone()) {
            path.currentSegment(pathData);
            //System.out.println(Arrays.toString(pathData));
            double[] x = new double[2];
            System.arraycopy(pathData, 0, x, 0, 2);
            path.next();
            lineData.add(new Point((int) x[0], (int) x[1]));
        }
        for (int i = 0; i < lineData.size() - 1; i++) {
            g.drawLine(lineData.get(i).x,
                    lineData.get(i).y,
                    lineData.get(i + 1).x,
                    lineData.get(i + 1).y);
        }
        g.drawLine(lineData.get(0).x,
                lineData.get(0).y,
                lineData.get(lineData.size() - 1).x,
                lineData.get(lineData.size() - 1).y);

    }

    public boolean isLocked() {
        return lock;
    }

    public void lock(Object o) {
        if (owner == null) {
            lock = true;
            this.owner = o;
            System.out.println("Locked by " + o);
        }

    }

    public void unlock(Object o) {
        if (o == owner) {
            lock = false;
            System.out.println("Unlocked by " + o);
        }

    }

}
