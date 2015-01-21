/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.tasks;

import java.awt.Graphics2D;
import level.LevelMaker;
import main.init;

/**
 *
 * @author David
 */
public class LevelStartDelayer extends GraphicsTask{
    private int levelNum;
    public LevelStartDelayer(int frames, int levelNum) {
        super(frames);
        this.levelNum = levelNum;
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
    }

    @Override
    public void onCompletion() {
        init.getGameGUI().setLevelMaker(new LevelMaker(levelNum));
        init.getGameGUI().getLevel().setup();
    }


}
