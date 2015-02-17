/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 *
 * @author david.green
 */
public class BackgroundGenerator {

    private int width;
    private int height;
    private int state = 0;

    public boolean Generate(Graphics2D g) {
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        draw(bg.createGraphics(), state, width, height);
        g.drawImage(bg, 0, 0, null);
        state++;
        return true;
    }

    public BackgroundGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }
    private static final int SHADE_GRADIENT_NUMBER = 4;
    private void draw(Graphics2D g, int state, int w, int h) {
        int shadeGradient = 255 / SHADE_GRADIENT_NUMBER;
        Color c = Color.BLACK;
        float[] vals = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        for (int i = 0; i < SHADE_GRADIENT_NUMBER; i++) {
            Color x = Color.getHSBColor(1, 0, shadeGradient * i);
            g.setColor(x);
            g.fillRect(i * SHADE_GRADIENT_NUMBER, 0, width, h);
        }
        g.setColor(c);
        g.clearRect(0, 0, width, height);
    }
}
