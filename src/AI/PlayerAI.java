package AI;

import control.ControlHandler;
import main.init;
import unit.Unit;

/**
 *
 * @author david.green
 */
public class PlayerAI extends AI {

    private final ControlHandler controls = init.getGameGUI().getControlHandler();
    private boolean keys[] = new boolean[6];
    private final int MOVE_AMOUNT = 10;

    public PlayerAI(Unit unit) {
        super(unit);
    }

    @Override
    protected void move(boolean b) {
        move();
    }

    @Override
    protected void move() {
        keys = controls.getKeysPressed();
        if (keys[5]){
            getUnit().specialAbility();
        }
        //if up or down and left or right
        if ((keys[0] || keys[1]) && (keys[2] || keys[3])) {
            int moveDelta = (int) (Math.sqrt(2) * (MOVE_AMOUNT / 2));
            int newX = getUnit().getX() + moveDelta * (keys[3] ? 1 : -1);
            int newY = getUnit().getY() + moveDelta * (keys[1] ? 1 : -1);
            getUnit().setLocation(newX, newY);
            return;
        }
        if (keys[0] || keys[1]) {
            getUnit().moveY(MOVE_AMOUNT * (keys[1] ? 1 : -1));
        }
        if (keys[2] || keys[3]) {
            getUnit().moveX(MOVE_AMOUNT * (keys[3] ? 1 : -1));
        }
    }

    @Override
    protected void attack() {
        if (keys[4]){
            getUnit().fire(init.getGameGUI().getMousePosition());
        }
    }

}
