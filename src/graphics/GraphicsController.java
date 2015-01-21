package graphics;

import graphics.tasks.GraphicsTask;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import main.init;
import unit.MainCharacter;
import unit.ProjectileExplosion;
import unit.Unit;

/**
 *
 * @author David
 */
public class GraphicsController {

    private final Insets insets;
    private Unit mainCharacter;
    private boolean firstRender = true;
    private Rectangle oldBounds;
    private final AtomicLong score;
    private final boolean drawHitboxes = false; //Debug Line
    private ArrayList<GraphicsTask> tasks;

    public GraphicsController(Insets insets) {
        this.insets = insets;
        score = new AtomicLong(0);
        tasks = new ArrayList<>();
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
        boolean b = init.getUnitOperationHandler().lock();
        ArrayList<Unit> units = init.getUnitOperationHandler().getUnits();
        for (int i = 0; i < units.size(); i++) {
            try {
                units.get(i).draw(g);
                if (drawHitboxes) {
                    g.setColor(Color.MAGENTA);
                    GraphicsUtilities.drawArea(units.get(i).getHitbox(), g);

                }
            } catch (NullPointerException e) {
                System.out.println("Yo dawg, null pointer - drawing operations");
            }
        }
        b = init.getUnitOperationHandler().unlock();
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            GraphicsTask x = (GraphicsTask) iterator.next();
            x.draw(g, width, height);
            x.frames--;
            if (x.frames == 0) {
                x.onCompletion();
                iterator.remove();
            }

        }
        drawScore(0, 0, 50, 20, g);
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
    }

    private void scale() {
        double xRatio = init.getGameGUI().getBounds().getWidth() / oldBounds.width * 1.0;
        double yRatio = init.getGameGUI().getBounds().getHeight() / oldBounds.height * 1.0;
        for (Unit unit : init.unitOperationHandler.getUnits()) {
            unit.setLocation((int) (unit.getX() * xRatio), (int) (unit.getY() * yRatio));
        }
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
        g.setFont(GraphicsUtilities.fillRect(Long.toString(this.getScore()), g, width, height));
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

    public boolean addTask(GraphicsTask e) {
        return tasks.add(e);
    }

    public boolean removeTask(GraphicsTask o) {
        return tasks.remove(o);
    }

}
