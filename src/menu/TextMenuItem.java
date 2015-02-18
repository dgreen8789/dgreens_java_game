/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import graphics.GraphicsUtilities;
import graphics.tasks.GraphicsTask;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import main.init;

/**
 *
 * @author David
 */
public class TextMenuItem extends MenuItem {
    private String text;
    private Color color;
    private Color textColor;
    private GraphicsTask task;
    public TextMenuItem(Menu reference, Point pos, Color c, String text, int width, int height) {
        super(new Rectangle(pos.x, pos.y, width, height), reference);
        this.text = text;
        this.color = c;
    }

    @Override
    public void draw(Graphics2D g) {
        Rectangle r = getBounds();
        g.setColor(color);
        g.fill(r);
        Font initial =  g.getFont();
        Font f = GraphicsUtilities.fillRect(text, g, r.width, r.height);
        g.setFont(f);
        g.setColor(textColor == null ? Color.WHITE : textColor);
        g.drawString(text, r.x, r.y + r.height);
        g.setFont(initial);
    }

    @Override
    public void onClick() {
        init.getGameGUI().getGraphicsControl().addTask(task);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GraphicsTask getTask() {
        return task;
    }

    public void setTask(GraphicsTask task) {
        this.task = task;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    
    
    
}
