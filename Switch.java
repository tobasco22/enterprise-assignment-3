
package trainschedulingsimulation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Switch {
    private int switchName; // switch identifier number
    private Lock theSwitchLock = new ReentrantLock(); // define the lock

    // Constructor method
    public Switch(int switchName) {
        this.switchName = switchName;
    }

    // Method for a train to acquire the switch lock
    public boolean lockSwitch() {
        if (theSwitchLock.tryLock()) {
            // Lock acquired by the requesting train
            return true;
        } else {
            // Lock not available, handle the case when the switch is already locked
            return false;
        }
    }

    // Method for a train to release the switch lock
    public void unlockSwitch() {
        theSwitchLock.unlock();
    }
}
