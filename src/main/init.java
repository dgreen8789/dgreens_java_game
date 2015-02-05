/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import graphics.GUI;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import level.LevelMaker;
import phyics.UnitOperationHandler;

/**
 *
 * @author David
 */
public class init {

    private static GUI gameGUI;
    private static UnitOperationHandler unitOperationHandler;
    private static final File configFile = new File("\\cfg.dat");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //DEBUG CODE
        // END DEBUG CODE
        double[] vals;
        if (configFile.exists()) {
            System.out.println("Config File exists");
            try {
                try {
                    vals = loadConfigfromFile();
                } catch (FileNotFoundException ex) {
                    generateConfigFile();
                    vals = (double[]) defaultValues.clone();
                }
            } catch (IOException ex) {
                System.out.println("IO error. StackTrace:\n\n");
                ex.printStackTrace();
            }

        } else {
            generateConfigFile();
            vals = (double[]) defaultValues.clone();

        }

        gameGUI = new GUI(60);
        unitOperationHandler = new UnitOperationHandler();
        Thread unitHandler = new Thread(unitOperationHandler);
        unitHandler.start();
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

    private static double[] loadConfigfromFile() throws FileNotFoundException {
        double[] options = new double[configOptions.length];
        Scanner in = new Scanner(configFile);
        int x = 0;
        while (options[options.length - 1] == 0) {
            if (in.hasNext()) {
                options[x++] = in.nextDouble();
            }
            in.nextLine();
        }
        return options;

    }
    public static String[] configOptions = {"WINDOWED_MODE", "TARGET_FPS", "DIFFICULTY", "DRAW_HITBOXES",
        "STARTING_LEVEL"};
    public static String[] configNotes = {"1 for full screen, 0 for a resizable window", "Target FPS for the game to run at",
        "Can be \"EASY\", \"MEDIUM\", \"HARD\", \"NIGHTMARE\"", "1 to draw hitboxes, 0 to not. This is a dev option.",
        "Sets the starting level for the game. Higher Levels may cause instant death and or lag"};
    public static double[] defaultValues = {0, 60, LevelMaker.MEDIUM, 0, 1};

    private static void generateConfigFile() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
        for (int i = 0; i < configOptions.length; i++) {
            String string = configOptions[i];
            string = string.concat(" == ") + defaultValues[i];
            out.write(string);
            out.newLine();
            out.write(configNotes[i]);
            out.newLine();
        }
        out.flush();
        out.close();
        System.out.println("ATTEMPTED WRITE");
    }

}
