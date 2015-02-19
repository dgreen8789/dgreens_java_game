package menu;

import control.Clickable;
import graphics.tasks.GraphicsTask;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Menu extends GraphicsTask implements Clickable{
    private ArrayList<MenuItem> menuItems;
    private boolean displayed = true;
    public Menu() {
        super(1);
        this.menuItems = new ArrayList<>();
    }

    public boolean add(MenuItem e) {
        return (causesHitboxConflict(e)) ? false : menuItems.add(e);
    }

    public void add(int index, MenuItem element) {
        if (!causesHitboxConflict(element))
        menuItems.add(index, element);
    }
    
    private MenuItem getObjectAtClickedLocation(Point p){
        for (MenuItem menuItem : menuItems) {
            Area area = new Area(menuItem.getBounds());
            if (area.contains(p)) return menuItem;
        }
        return null;
    }
    private boolean causesHitboxConflict(MenuItem m){
        for (MenuItem menuItem : menuItems) {
            Area area = new Area(menuItem.getBounds());
            if (area.intersects(m.getBounds())) return true;
        }
        return false;
    }
    public void onClick(Point p){
        MenuItem item = getObjectAtClickedLocation(p);
        if (item != null) item.onClick();
    }
    private void draw(Graphics2D g){
        for (MenuItem menuItem : menuItems) {
            menuItem.draw(g);
        }
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
        draw(g);
        frames+= displayed ?  1:0;
    }

    @Override
    public void onCompletion() {
        frames = 1;
        displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }
    
    
}
