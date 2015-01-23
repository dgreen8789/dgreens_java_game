/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import graphics.GraphicsUtilities;
import java.awt.Graphics2D;
import java.awt.Point;
import main.init;

/**
 *
 * @author David
 */
public class UnitUtilities {

    public static Point getRandomLocation(Unit u) {
        Point loc = new Point(-1, -1);
        while (!u.isValidLocation(loc)) {
            loc.x = (int) (Math.random() * init.getGameGUI().getGraphicsControl().getGameWidth());
            loc.y = (int) (Math.random() * init.getGameGUI().getGraphicsControl().getGameHeight());
        }
        return loc;
    }

    public static void drawHealth(Graphics2D g, Unit u) {
        int health = u.getHealth();
        int size = u.getSize();
        int x = u.getX();
        int y = u.getY();
        int characterAllowance = (size / 10 + 1);
        if (characterAllowance == 0) {
            characterAllowance++;
        }
        characterAllowance *= (size / 4);
        g.setFont(GraphicsUtilities.fillRect(Integer.toString(health), g,
                (size / 2) + characterAllowance, size));
        g.drawString(Integer.toString(health),
                x - characterAllowance * Integer.signum(x),
                y - (size * Integer.signum(y)));
    }
}
