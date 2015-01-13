package graphics;

import AI.Formation;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import unit.Unit;

/**
 *
 * @author David
 */
public class GraphicsController {

    private boolean lock;
    private Insets insets;
    private ArrayList<Unit> units;
    int x = 4;
    int r = 0;

    public GraphicsController(Insets insets) {
        this.insets = insets;
        units = new ArrayList<>();

    }

    /**
     * Executes the render instructions for the board
     *
     * @param g the graphics context
     * @param width the width of the canvas to draw on
     * @param height the height of the canvas to draw on
     */
    void render(Graphics2D g, int width, int height, Insets insets) {

        width -= insets.left + insets.right;
        height -= insets.top + insets.bottom;
        g.setColor(Color.GREEN);
        int[][] data = Formation.shape(new Point(width / 2, height / 2), 100, x, r);

        g.drawPolygon(data[0], data[1], data[0].length);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < data[0].length; i++) {
            int[][] shape2 = Formation.shape(new Point(data[0][i], data[1][i]), 10, x, (360 / (i + 1)) * i);
            g.drawPolygon(shape2[0], shape2[1], shape2[0].length);
        }
        x = (x > 15) ? 3 : x;
        r ++;
        if (r == 360) {
            r %= 360;
            x++;
        }
    }

    public void addUnit(Unit u) {
        units.add(u);
    }

    public Point getPlayerLocation() {
        return null;
    }
}
