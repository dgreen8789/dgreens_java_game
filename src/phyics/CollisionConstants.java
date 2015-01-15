/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phyics;

import java.util.Arrays;

/**
 *
 * @author david.green
 */
public class CollisionConstants {

    public static final int FRIENDLY_UNIT = 10;
    public static final int ENEMY_UNIT = 9;
    public static final int NEUTRAL_UNIT = 8;
    public static final int FRIENDLY_PROJECTILE = 7;
    public static final int ENEMY_PROJECTILE = 6;
    public static final int GRAPHICAL_PROJECTILE = Integer.MIN_VALUE + 1;
    public static final int UNTARGETABlE = Integer.MIN_VALUE;
    public static final int[] CODE_LIST = {UNTARGETABlE, GRAPHICAL_PROJECTILE,
        ENEMY_PROJECTILE, FRIENDLY_PROJECTILE, NEUTRAL_UNIT, ENEMY_UNIT,
        FRIENDLY_UNIT};
    public static boolean isValidCollisionCode(int code){
        return !(Arrays.binarySearch(CODE_LIST, code) == -1);
    }
}
