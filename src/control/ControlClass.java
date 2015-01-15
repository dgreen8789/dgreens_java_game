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

/**
 *
 * @author David
 */
public class ControlClass implements MouseInputListener, KeyListener, WindowListener, FocusListener, MouseWheelListener {

    private char UP_KEY = 'W';
    private char DOWN_KEY = 'S';
    private char LEFT_KEY = 'A';
    private char RIGHT_KEY = 'D';
    private int MOVE_AMOUNT = 10;

    public ControlClass() {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = Character.toUpperCase(e.getKeyChar());
        
        if (c == UP_KEY || e.getKeyCode() ==  KeyEvent.VK_UP) {
            init.getGameGUI().getGraphicsControl().getMainCharacter().moveY(MOVE_AMOUNT * -1);
        }
        if (c == DOWN_KEY || e.getKeyCode() ==  KeyEvent.VK_DOWN) {
            init.getGameGUI().getGraphicsControl().getMainCharacter().moveY(MOVE_AMOUNT);
        }
        if (c == LEFT_KEY || e.getKeyCode() ==  KeyEvent.VK_LEFT ) {
            init.getGameGUI().getGraphicsControl().getMainCharacter().moveX(MOVE_AMOUNT * -1);
        }
        if (c == RIGHT_KEY || e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            init.getGameGUI().getGraphicsControl().getMainCharacter().moveX(MOVE_AMOUNT);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            init.getGameGUI().getGraphicsControl().getMainCharacter().fire(init.getGameGUI().getMousePosition());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            for (int i = 0; i < e.getClickCount() ; i++) {
                            init.getGameGUI().getGraphicsControl().getMainCharacter().fire(init.getGameGUI().getMousePosition());

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            init.getGameGUI().getGraphicsControl().getMainCharacter().fire(init.getGameGUI().getMousePosition());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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

}
