package graphics.tasks;

import java.awt.Point;
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
        //System.out.println("CALLED " + init.getGameGUI().getLevel().getLevelNum());
        init.getGameGUI().getLevel().incrementandSetup();
    }
    
}
