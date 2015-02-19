package graphics.tasks;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author David
 */
public abstract class GraphicsTask {
    public int frames;
    private Color color;
    public GraphicsTask(int frames) {
        this.frames = frames;
    }
    public abstract void draw(Graphics2D g, int width, int height);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void onCompletion();
    
    
}
