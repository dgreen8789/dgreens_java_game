package graphics;

import AI.EnemyAI;
import graphics.tasks.GraphicsTask;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import main.init;
import unit.MainCharacter;
import unit.ProjectileExplosion;
import unit.Unit;
import unit_enemy.BasicEnemy;

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
    private boolean drawHitboxes; //Debug Line
    private ArrayList<GraphicsTask> tasks;
    private Unit mouseOverUnit;
    private boolean isDrawingTasks;
    private BackgroundGenerator bgGenerator;

    public GraphicsController(Insets insets, boolean drawHitboxes) {
        this.insets = insets;
        score = new AtomicLong(0);
        tasks = new ArrayList<>();
        this.drawHitboxes = drawHitboxes;
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
        boolean forceWait;
        width -= insets.left + insets.right;
        height -= insets.top + insets.bottom;
        if (!init.getGameGUI().getBounds().equals(oldBounds)) {
            scale();
            oldBounds = init.getGameGUI().getBounds();
            bgGenerator = new BackgroundGenerator(width, height);
        }
        forceWait = bgGenerator.Generate(g);
        isDrawingTasks = true;
//        System.out.println("Started drawing tasks at " + System.nanoTime());
//        System.out.println(tasks);
//        System.out.print("{");
//        for (GraphicsTask task : tasks) {
//            System.out.print(task.frames + ", ");
//        }
//        System.out.println("}");
        for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
            GraphicsTask x = (GraphicsTask) iterator.next();
            x.draw(g, width, height);
            x.frames--;
            if (x.frames == 0) {
                iterator.remove();
                x.onCompletion();
            }
        }
        isDrawingTasks = false;
//        System.out.println("Finished drawing tasks at " + System.nanoTime());
        forceWait = init.getUnitOperationHandler().lock();
        ArrayList<Unit> units = init.getUnitOperationHandler().getUnits();
        for (int i = 0; i < units.size(); i++) {
            try {
                units.get(i).draw(g);
                if (drawHitboxes) {
                    g.setColor(Color.MAGENTA);
                    GraphicsUtilities.drawArea(units.get(i).getHitbox(), g);
                    if (units.get(i).getAi() instanceof EnemyAI) {
                        int[][] points = ((EnemyAI) units.get(i).getAi()).getFormation().getPoints();
                        g.setColor(Color.CYAN);
                        g.drawPolygon(points[0], points[1], points[0].length);
                    }
                }
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        }
        forceWait = init.getUnitOperationHandler().unlock();
        drawScore(0, 0, 40, g);

    }

    public Unit getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(Unit mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    private void firstRender() {
        Rectangle bounds = init.getGameGUI().getBounds();
        bgGenerator = new BackgroundGenerator(bounds.width, bounds.height);
        firstRender = false;
        mainCharacter = new MainCharacter(bounds.width / 2, bounds.height / 2, 20);
        oldBounds = bounds;
        ProjectileExplosion explosion = new ProjectileExplosion(mainCharacter);
        explosion.setLocation(mainCharacter.getLocation());
        explosion.onCreate();
    }

    private void scale() {
        double xRatio = init.getGameGUI().getBounds().getWidth() / oldBounds.width * 1.0;
        double yRatio = init.getGameGUI().getBounds().getHeight() / oldBounds.height * 1.0;
        for (Unit unit : init.getUnitOperationHandler().getUnits()) {
            unit.setLocation((int) (unit.getX() * xRatio), (int) (unit.getY() * yRatio));
        }
    }

    public final long getScore() {
        return score.get();
    }

    public final void addScore(long l) {
        score.addAndGet((int) (l * init.getGameGUI().getLevel().getScoreMultiplier()));
    }

    private void drawScore(int x, int y, int height, Graphics2D g) {
        Font f = g.getFont();
        g.setColor(Color.WHITE);
        String scoreString = "Score = " + this.getScore();
        g.setFont(GraphicsUtilities.fillRect(scoreString, g,
                10 * scoreString.length(), height));
        g.drawString(scoreString, x, y + (int) (g.getFontMetrics()
                .getLineMetrics(scoreString, g).getHeight()));
        g.setFont(f);
    }

    public int getGameWidth() {
        return oldBounds.width - (this.insets.right + this.insets.left);
    }

    public int getGameHeight() {
        return oldBounds.height - (this.insets.top + this.insets.bottom);
    }

    public boolean addTask(GraphicsTask e) {
        if (tasks.contains(e)) {
            return false;
        }
        while (this.isDrawingTasks);
        return tasks.add(e);
    }

    public boolean removeTask(GraphicsTask o) {
        while (this.isDrawingTasks);
        return tasks.remove(o);
    }

    public Unit getMouseOverUnit() {
        return mouseOverUnit;
    }

    public void setMouseOverUnit(Unit mouseOverUnit) {
        this.mouseOverUnit = mouseOverUnit;
    }

    public boolean isDrawingHitboxes() {
        return drawHitboxes;
    }

    public void drawHitboxes(boolean drawHitboxes) {
        this.drawHitboxes = drawHitboxes;
    }
    
}
