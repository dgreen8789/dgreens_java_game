package graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import main.init;
import unit.MainCharacter;
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

    public GraphicsController(Insets insets) {
        this.insets = insets;
        units = new ArrayList<>();
        mainCharacter = new MainCharacter();
        this.addUnit(mainCharacter);
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
            }
        }
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
    

}
