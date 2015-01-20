/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import graphics.GUI;
import phyics.UnitOperationHandler;

/**
 *
 * @author David
 */
public class init {

    public static GUI gameGUI;
    public static UnitOperationHandler unitOperationHandler;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        gameGUI = new GUI(60);
        unitOperationHandler = new UnitOperationHandler();
        Thread unitHandler = new Thread(unitOperationHandler);
        unitHandler.start();
        System.out.println(unitHandler.getName());

    }

    public static String getVersion() {
        return "1.0";
    }

    public static GUI getGameGUI() {
        return gameGUI;
    }

    public static UnitOperationHandler getUnitOperationHandler() {
        return unitOperationHandler;
    }

}
