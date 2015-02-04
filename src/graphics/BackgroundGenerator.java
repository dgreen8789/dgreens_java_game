/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 *
 * @author david.green
 */
public class BackgroundGenerator {

    private static double[][][] data;
    private static int width;
    private static int height;
    private static int state = 0;

    public static boolean Generate(Graphics2D g) {
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        data = new double[width][height][];
        System.out.println("STARTED DATA COLLECTION");
        Raster r = bg.getData();
       
        for (int i = 0; i < bg.getWidth(); i++) {
            for (int j = 0; j < bg.getHeight(); j++) {
                //System.out.println("ATTEMPTED DATA COLLECTION OF " + new Point(i, j));
                r.getPixel(i, j, data[i][j]);
            }
        }
        System.out.println("GOT DATA");
        shift(data);
        draw(bg.createGraphics(), state, width, height);
        state++;
        g.drawImage(bg, null, null);
        return true;
    }

    public static void setup(int width, int height) {
        BackgroundGenerator.width = width;
        BackgroundGenerator.height = height;
        System.out.println("SETUP");
    }

    private static void shift(double[][][] data) {
        for (int i = 1; i < data.length; i++) {
            System.arraycopy(data[i], 0, data[i - 1], 0, data[i].length);
        }
    }

    private static void draw(Graphics2D g, int state, int w, int h) {
        g.setBackground(Color.GRAY);
        
        g.setColor(Color.yellow);
        if(state % 60 == 0){
            int x = (int)(Math.random() * w);
            int y = (int)(Math.random() * h);
            g.drawOval(x,y,20,20);
        }
    }
}
