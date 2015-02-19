package main;

import graphics.GUI;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    private static File configFile;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //DEBUG CODE
        // END DEBUG CODE
        //MAKE THIS BETTER AT A FUTURE DATE
        configFile = new File(System.getProperty("user.home") + "/Desktop/cfg.dat");
        double[] vals = null;
        if (configFile.exists()) {
            System.out.println("Config File exists");
            try {
                try {
                    vals = loadConfigfromFile();
                } catch (FileNotFoundException ex) {
                    generateConfigFile();
                    vals = convert(defaultValues);
                }
            } catch (IOException ex) {
                System.out.println("IO error. StackTrace:\n\n");
                ex.printStackTrace();
            }

        } else {
            generateConfigFile();
            vals = convert(defaultValues);

        }
        //System.out.println(Arrays.toString(vals));
        gameGUI = new GUI(vals);
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
        while (in.hasNextLine()) {
            String[] z = in.nextLine().split(" == ");
            if (z.length > 1) {
                options[x++] = Double.parseDouble(z[1]);
            }
        }
        return options;

    }
    public static String[] configOptions = {"WINDOWED_MODE", "TARGET_FPS", "DIFFICULTY", "DRAW_HITBOXES",
        "STARTING_LEVEL"};
    public static String[] configNotes = {"1 for full screen, 0 for a resizable window", "Target FPS for the game to run at",
        "Can be \"EASY\"(" + LevelMaker.EASY + " ), \"MEDIUM\"(" + LevelMaker.MEDIUM + ")"
        + "  \"HARD\"(" + LevelMaker.HARD + "), \"NIGHTMARE\"(" + LevelMaker.NIGHTMARE + ")",
        "1 to draw hitboxes, 0 to not. This is a dev option.",
        "Sets the starting level for the game. Higher Levels may cause instant death and or lag"};
    public static String[] defaultValues = {"0", "60", Double.toString(LevelMaker.MEDIUM), "0", "1"};

    private static void generateConfigFile() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
        for (int i = 0; i < configOptions.length; i++) {
            String string = configOptions[i];
            string = string.concat(" == ") + defaultValues[i];
            out.write(string);
            out.newLine();
            out.write(configNotes[i]);
            out.newLine();
            out.newLine();
        }
        out.flush();
        out.close();
        System.out.println("ATTEMPTED WRITE");
    }

    private static double[] convert(String[] s) {
        double[] vals = new double[s.length];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Double.parseDouble(s[i]);
        }
        return vals;
    }

}
