package graphics;

import control.ControlClass;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.init;

public class GUI extends Thread {

    private boolean isRunning = true;
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D backgroundGraphics;
    private Graphics2D graphics;
    private JFrame frame;
    private int width = 320;
    private int height = 240;
    private int scale = 1;
    private AtomicInteger FPS = new AtomicInteger();
    private AtomicInteger FPSLimit;
    private GraphicsConfiguration config
            = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();
    private FPSCounter fpsCounter = new FPSCounter(FPS);
    private GraphicsController graphicsControl;
    private boolean paused;
    private ControlClass x = new ControlClass();

    // create a hardware accelerated image
    public final BufferedImage create(final int width, final int height, final boolean alpha) {
        return config.createCompatibleImage(width, height, alpha ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    // Setup
    public GUI(int FPSLimit) {
        // JFrame
        frame = new JFrame();
        frame.addWindowListener(new FrameClose());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width * scale, height * scale);
        frame.setVisible(true);

        // Canvas
        canvas = new Canvas(config);
        canvas.setSize(width * scale, height * scale);
        frame.add(canvas, 0);

        // Background & Buffer
        background = create(width, height, false);
        canvas.createBufferStrategy(2);
        do {
            strategy = canvas.getBufferStrategy();
        } while (strategy == null);
        this.FPSLimit = new AtomicInteger(FPSLimit);
        Timer x = new Timer();
        x.schedule(fpsCounter, 0, 1000);

        graphicsControl = new GraphicsController(frame.getInsets());
        addListeners();
        start();
    }

    private void addListeners() {
        canvas.addMouseMotionListener(x);
        canvas.addKeyListener(x);
        canvas.addFocusListener(x);
        canvas.addMouseListener(x);
        canvas.addMouseWheelListener(x);
    }

    private class FrameClose extends WindowAdapter {

        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    public void setFPSLimit(int FPSLimit) {
        this.FPSLimit.set(FPSLimit);
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (IllegalStateException e) {
                return null;
            }
        }
        return graphics;
    }

    private boolean updateScreen() {
        graphics.dispose();
        graphics = null;
        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());

        } catch (NullPointerException e) {
            return true;

        } catch (IllegalStateException e) {
            return true;
        }
    }

    public void run() {

        main:
        while (isRunning) {
            long fpsWait = (long) (1.0 / FPSLimit.get() * 1000);
            width = frame.getWidth();
            height = frame.getHeight();

            canvas.setSize(width, height);
            background = create(width, height, false);
            backgroundGraphics = (Graphics2D) background.getGraphics();
            //System.out.println(width + " " + height);
            long renderStart = System.nanoTime();
            updateApplication();
            frame.setTitle("Game version " + init.getVersion() + "  " + FPS.intValue() + " FPS");
            // Update Graphics
            do {
                Graphics2D bg = getBuffer();
                if (!isRunning) {
                    break main;
                }
                renderApplication(backgroundGraphics, canvas.getWidth(), canvas.getHeight(), frame.getInsets()); // this calls your draw method
                // thingy
                if (scale != 1) {
                    bg.drawImage(background, 0, 0, width * scale, height
                            * scale, 0, 0, width, height, null);
                } else {
                    bg.drawImage(background, 0, 0, null);
                }
                bg.dispose();
            } while (!updateScreen());
            fpsCounter.incrementFPSCount();
            // Better do some FPS limiting here
            long renderTime = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, fpsWait - renderTime));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTime = (System.nanoTime() - renderStart) / 1000000;

        }
        frame.dispose();
    }

    public void updateApplication() {
        if (!paused) {
            
        }
    }

    public void renderApplication(Graphics2D g, int width, int height, Insets insets) {

        if (!paused) {
            graphicsControl.render(g, width, height, insets);
        } else {
            Font f = g.getFont();
            g.setFont(this.fillRect("PAUSED", g, width, height));
            g.setColor(Color.RED);
            g.drawString("PAUSED", width/2 - (g.getFontMetrics().stringWidth("PAUSED") / 2), 
                    height/2);
        }
    }

    /**
     *
     * @param string
     * @param g
     * @param width
     * @param height
     * @return A font that will fill the given area with the given string, or
     * null if the area is too small
     */
    public Font fillRect(String string, Graphics2D g, int width, int height) {
        Font f = g.getFont();
        Rectangle2D bounds = f.getStringBounds(string, g.getFontRenderContext());

        while (bounds.getWidth() < width || bounds.getHeight() < height) {
            f = f.deriveFont(f.getSize2D() + 1);
            bounds = f.getStringBounds(string, g.getFontRenderContext());
        }

        while (bounds.getWidth() > width || bounds.getHeight() > height) {
            if (f.getSize2D() < 2) {
                return null;
            } else {
                f = f.deriveFont(f.getSize2D() - 1);
                bounds = f.getStringBounds(string, g.getFontRenderContext());
            }
        }

        return f;
    }

    public GraphicsController getGraphicsControl() {
        return graphicsControl;
    }

    public boolean isPaused() {
        return paused;
    }

    public void Pause(boolean paused) {
        this.paused = paused;
    }
     public Rectangle getBounds(){
        return this.canvas.getBounds();
    }

}

class FPSCounter extends TimerTask {

    private AtomicInteger val;
    private AtomicInteger myVal;

    public FPSCounter() {
        throw new UnsupportedOperationException("Default Constructor not allowed.");
    }

    public FPSCounter(AtomicInteger val) {
        this.val = val;
        myVal = new AtomicInteger();
    }

    public void incrementFPSCount() {
        myVal.addAndGet(1);
    }

    @Override
    public void run() {

        val.set(myVal.get());
        myVal.set(0);
    }
   
}
