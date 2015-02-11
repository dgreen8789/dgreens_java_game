/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.tasks;

import java.awt.Point;
import level.LevelMaker;
import main.init;

/**
 *
 * @author David
 */
public class LevelCompleteTextTask extends TextTask {

    public LevelCompleteTextTask(String text, int width, int height, Point position, int frames) {
        super(text, width, height, position, frames);
    }

    @Override
    public void onCompletion() {
        init.getGameGUI().getLevel().incrementandSetup();
    }
    
}
