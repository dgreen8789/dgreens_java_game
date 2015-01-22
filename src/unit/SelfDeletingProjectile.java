/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import AI.ProjectileAI;
import AI.TimedAI;
import java.awt.Color;
import java.awt.Point;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class SelfDeletingProjectile extends StandardProjectile {

    public SelfDeletingProjectile(Color color, int radius, Point target, int affiliation, int moves) {
        super(color, radius, target, affiliation);
    }

    public SelfDeletingProjectile(Color color, int radius, int x, int y, Point target, int affiliation, int speed, int moves) {
        super(color, radius, x, y, target, affiliation, speed);
        this.setAi(new TimedAI(new ProjectileAI(this), moves));

    }

}
