/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.tasks;

import graphics.GraphicsUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author David
 */
public class TextTask extends GraphicsTask {

    private String text;
    private int width;
    private int height;
    private Point position;
    private int colorSwitch;

    public TextTask(String text, int width, int height, Point position, int frames) {
        super(frames);
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g, int width, int height) {
        Font f = g.getFont();
        Color c = g.getColor();
        g.setColor((colorSwitch++ == 0) ? Color.GREEN : Color.WHITE);
        colorSwitch %=2;
        Font newFont = this.getFont(g);
        g.setFont(newFont);
        g.drawString(text, position.x, position.y);
        g.setFont(f);
        g.setColor(c);
        
    }

    private Font getFont(Graphics2D g) {
        return GraphicsUtilities.fillRect(text, g, this.width, this.height);

    }

    @Override
    public void onCompletion() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
