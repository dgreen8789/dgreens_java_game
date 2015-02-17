/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.event.MouseInputListener;
import main.init;
import unit.Unit;

/**
 *
 * @author David
 */
public class ControlHandler implements MouseInputListener, KeyListener, WindowListener, FocusListener, MouseWheelListener {

    private char UP_KEY = 'W';
    private char DOWN_KEY = 'S';
    private char LEFT_KEY = 'A';
    private char RIGHT_KEY = 'D';
    /**
     * UP - DOWN - LEFT - RIGHT - SPACE/MOUSE BUTTON ONE - MOUSE BUTTON TWO
     */
    private final boolean[] keysPressed = new boolean[6];

    /**
     * UP - DOWN - LEFT - RIGHT - SPACE/MOUSE BUTTON ONE - MOUSE BUTTON TWO
     */
    public ControlHandler() {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //keyPressed(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = Character.toUpperCase(e.getKeyChar());

        if (c == UP_KEY || e.getKeyCode() == KeyEvent.VK_UP) {
            keysPressed[0] = true;
            keysPressed[1] = false;
        }
        if (c == DOWN_KEY || e.getKeyCode() == KeyEvent.VK_DOWN) {
            keysPressed[1] = true;
            keysPressed[0] = false;
        }
        if (c == LEFT_KEY || e.getKeyCode() == KeyEvent.VK_LEFT) {
            keysPressed[3] = false;
            keysPressed[2] = true;
        }
        if (c == RIGHT_KEY || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keysPressed[3] = true;
            keysPressed[2] = false;
        }
        if (c == 'H' ) {
            init.getGameGUI().getGraphicsControl().drawHitboxes(
            !init.getGameGUI().getGraphicsControl().isDrawingHitboxes());
        }
        if (c == 'G' ) {
            Unit u = init.getGameGUI().getGraphicsControl().getMainCharacter();
            u.setInvincible(!u.isInvincible());
            u.getWeapon().setDamage(u.isInvincible() ? Integer.MAX_VALUE : 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            init.getGameGUI().Pause(!init.getGameGUI().isPaused());
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keysPressed[4] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char c = Character.toUpperCase(e.getKeyChar());
        if (c == UP_KEY || e.getKeyCode() == KeyEvent.VK_UP) {
            keysPressed[0] = false;
        }
        if (c == DOWN_KEY || e.getKeyCode() == KeyEvent.VK_DOWN) {
            keysPressed[1] = false;
        }
        if (c == LEFT_KEY || e.getKeyCode() == KeyEvent.VK_LEFT) {
            keysPressed[2] = false;
        }
        if (c == RIGHT_KEY || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keysPressed[3] = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keysPressed[4] = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            keysPressed[4] = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            keysPressed[5] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            keysPressed[4] = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            keysPressed[5] = false;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        init.getGameGUI().Pause(false);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        init.getGameGUI().Pause(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    public char getUP_KEY() {
        return UP_KEY;
    }

    public void setUP_KEY(char UP_KEY) {
        this.UP_KEY = UP_KEY;
    }

    public char getDOWN_KEY() {
        return DOWN_KEY;
    }

    public void setDOWN_KEY(char DOWN_KEY) {
        this.DOWN_KEY = DOWN_KEY;
    }

    public char getLEFT_KEY() {
        return LEFT_KEY;
    }

    public void setLEFT_KEY(char LEFT_KEY) {
        this.LEFT_KEY = LEFT_KEY;
    }

    public char getRIGHT_KEY() {
        return RIGHT_KEY;
    }

    public void setRIGHT_KEY(char RIGHT_KEY) {
        this.RIGHT_KEY = RIGHT_KEY;
    }

    public boolean[] getKeysPressed() {
        return keysPressed.clone();
    }

}
