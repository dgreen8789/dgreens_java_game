/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

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
}
