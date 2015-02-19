package graphics.tasks;

import java.awt.Graphics2D;
import main.init;

/**
 *
 * @author David
 */
public class LevelStartDelayer extends GraphicsTask{

    public LevelStartDelayer(int frames) {
        super(frames);

    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
    }

    @Override
    public void onCompletion() {
        //System.out.println("CALLED");
        init.getGameGUI().getLevel().incrementandSetup();
    }


}
