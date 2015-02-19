package menu;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author David
 */
public abstract class MenuItem {
    private Rectangle bounds;
    private Menu reference;
    private boolean exitMenuOnClick;

    public MenuItem(Rectangle bounds, Menu reference) {
        this.bounds = bounds;
        this.reference = reference;
    }
    public abstract void draw(Graphics2D g);
    
    public void onClick(){
        if (exitMenuOnClick){
            reference.onCompletion();
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean exitMenuOnClick() {
        return exitMenuOnClick;
    }

    public void setExitMenuOnClick(boolean exitMenuOnClick) {
        this.exitMenuOnClick = exitMenuOnClick;
    }
    
    
    
}
