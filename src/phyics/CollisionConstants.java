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

    public static final int FRIENDLY_UNIT = 6;
    public static final int NEUTRAL_UNIT = 5;
    public static final int ENEMY_UNIT = 4;
    public static final int FRIENDLY_PROJECTILE = 3;
    public static final int ENEMY_PROJECTILE = 2;
    public static final int GRAPHICAL_PROJECTILE = 1;
    public static final int UNTARGETABlE = 0;
    public static final int[] CODE_LIST = {UNTARGETABlE, GRAPHICAL_PROJECTILE,
        ENEMY_PROJECTILE, FRIENDLY_PROJECTILE, ENEMY_UNIT, NEUTRAL_UNIT,
        FRIENDLY_UNIT};
    public static final String[] CODE_NAME_LIST = {"UNTARGETABlE", "GRAPHICAL_PROJECTILE",
        "ENEMY_PROJECTILE", "FRIENDLY_PROJECTILE", "ENEMY_UNIT", "NEUTRAL_UNIT",
        "FRIENDLY_UNIT"};
    public static boolean isValidCollisionCode(int code){
        return !(Arrays.binarySearch(CODE_LIST, code) == -1);
    }
    public static String getCodeName(int code){
        return (isValidCollisionCode(code)) ? CODE_NAME_LIST[code] : "INVALID_CODE";
    }
}
