package graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicLong;
import main.init;
import unit.MainCharacter;
import unit.Target;
import unit.Unit;

/**
 *
 * @author David
 */
public class GraphicsController {

    private boolean lock;
    private Insets insets;
    private ArrayList<Unit> units;
    private Unit mainCharacter;
    private boolean firstRender = true;
    private Rectangle oldBounds;
    private AtomicLong score;

    public GraphicsController(Insets insets) {
        this.insets = insets;
        units = new ArrayList<>();
        score = new AtomicLong(0);
        mainCharacter = new MainCharacter();
        this.addUnit(mainCharacter);
        Target g = new Target(10, 10, 10, Color.BLUE);
        this.addUnit(g);
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
            try{
                units.get(i).draw(g);
            }catch(ConcurrentModificationException e){
                if(units.get(i) == (null)) units.remove(i); else{
                    i--;
                }
            }catch(NullPointerException e){
                
            }
        }
        drawScore(0,0,50,20, g);
    }

    public void addUnit(Unit u) {
        units.add(u);
    }

    public void removeUnit(Unit u) {
        units.remove(u);
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
        mainCharacter.setLocation(bounds.width / 2, bounds.height / 2);
        oldBounds = bounds;

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
        g.drawString(Long.toString(this.getScore()), 0, (int)(g.getFontMetrics()
                .getLineMetrics(Long.toString(this.getScore()), g).getHeight()));
    }


    
    

}
