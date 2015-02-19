package graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class GraphicsUtilities {
    public static void drawArea(Area a, Graphics g) {
        PathIterator path = a.getPathIterator(null);
        double[] pathData = new double[6];
        ArrayList<Point> lineData = new ArrayList<>();
        while (!path.isDone()) {
            path.currentSegment(pathData);
            //System.out.println(Arrays.toString(pathData));
            double[] x = new double[6];
            System.arraycopy(pathData, 0, x, 0, 6);
            path.next();
            for (int i = 0; i < 3; i++) {
                 if (x[2 * i] != 0)
                 lineData.add(new Point((int) x[2 * i], (int) x[2 * i + 1]));
                 else{
                     i = 4;
                 }
            }
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
        /**
     *
     * @param string
     * @param g
     * @param width
     * @param height
     * @return A font that will fill the given area with the given string, or
     * null if the area is too small
     */
    public static Font fillRect(String string, Graphics2D g, int width, int height) {
        Font f = g.getFont();
        Rectangle2D bounds = f.getStringBounds(string, g.getFontRenderContext());

        while (bounds.getWidth() < width || bounds.getHeight() < height) {
            f = f.deriveFont(f.getSize2D() + 1);
            bounds = f.getStringBounds(string, g.getFontRenderContext());
        }

        while (bounds.getWidth() > width || bounds.getHeight() > height) {
            if (f.getSize2D() < 2) {
                return null;
            } else {
                f = f.deriveFont(f.getSize2D() - 1);
                bounds = f.getStringBounds(string, g.getFontRenderContext());
            }
        }

        return f;
    }
}
