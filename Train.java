
package trainschedulingsimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import trainschedulingsimulation.YardConfiguration;

//train
class Train implements Runnable {
   private int trainNumber;
   private int inboundTrack;
   private int outboundTrack;
   private List<Integer> requiredSwitches;
   private List<ReentrantLock> switchLocks;
   private boolean permanentHold;
   private YardConfiguration yardConfiguration;





    public void setRequiredSwitches(List<Integer> requiredSwitches) {
        this.requiredSwitches = requiredSwitches;
    }

    public List<Integer> getRequiredSwitches() {
        return requiredSwitches;
    }





//constructor for train info
   public Train(int trainNumber, int inboundTrack, int outboundTrack, List<Integer> requiredSwitches) {
       this.trainNumber = trainNumber;
       this.inboundTrack = inboundTrack;
       this.outboundTrack = outboundTrack;
       this.requiredSwitches = requiredSwitches;
       this.switchLocks = new ArrayList<>();
   }


  @Override
public void run() {
    try {
        // logic for acquiring locks and moving through the yard
        for (int switchNumber : requiredSwitches) {
            acquireLock(switchNumber);
        }

        // Check if all required locks have been acquired
        boolean allLocksAcquired = switchLocks.size() == requiredSwitches.size();

        // If all locks are acquired, print the message
        if (allLocksAcquired) {
            System.out.println("Train " + trainNumber + " has acquired all locks and is ready to move.\n");
            System.out.flush();
            System.out.println("Train " + trainNumber + " Releasing all switch locks.\n");
            System.out.flush();
            System.out.println("Train " + trainNumber + " Moves clear of yard control.\n");
            System.out.flush();
        }

        // Simulate train movement through the yard
        Thread.sleep(1000); // Adjust as needed

        // Release locks
        releaseLocks();

    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

    //acquire lock
   private void acquireLock(int switchNumber) throws InterruptedException {
       // logic to acquire a lock for a switch
       ReentrantLock lock = new ReentrantLock();
       lock.lock();
       switchLocks.add(lock);
       System.out.println("Train " + trainNumber + " HOLDS LOCK on Switch " + switchNumber+"\n");
   }
   //release the lock
   private void releaseLocks() {
       //logic to release all acquired locks
       for (ReentrantLock lock : switchLocks) {
           lock.unlock();
       }
       System.out.println("Train " + trainNumber + " Releasing all switch locks.\n");
       System.out.println("Train " + trainNumber + " Moves clear of yard control.\n");

   }
   //getters for trainfo
   public int getTrainNumber() {
       return trainNumber;
   }

   public int getInboundTrack() {
       return inboundTrack;
   }

   public int getOutboundTrack() {
       return outboundTrack;
   }

   public boolean isPermanentHold() {
       return permanentHold;
   }

   public YardConfiguration getYardConfiguration() {
       return yardConfiguration;
   }
   //setter for yard config
   public void setYardConfiguration(YardConfiguration yardConfiguration) {
       this.yardConfiguration = yardConfiguration;
   }
   //print details
   public void printTrainDetails() {
   System.out.println("Train Number: " + trainNumber);
   System.out.println("Inbound Track: " + inboundTrack);
   System.out.println("Outbound Track: " + outboundTrack);

   if (requiredSwitches.isEmpty()) {
       System.out.println("No required switches for this train.");
   } else {
       System.out.println("Required Switches:");
       for (int i = 0; i < requiredSwitches.size(); i++) {
           System.out.println("Switch " + (i + 1) + ": " + requiredSwitches.get(i));
       }
   }
   // newline to separate the train details
    System.out.println();
}
}
