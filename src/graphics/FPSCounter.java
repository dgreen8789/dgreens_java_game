package graphics;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author David
 */
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
