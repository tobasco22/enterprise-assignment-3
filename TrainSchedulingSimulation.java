
package trainschedulingsimulation;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TrainSchedulingSimulation {
    //code to send output to a file
    private static void redirectOutputToFile(String filename) {
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //code to make output go to a file. i took it out so it will just go to console
        //redirectOutputToFile("output.txt"); // the output file
        // Read configuration from theFleetFile.csv and theYardFile.csv
        List<Train> trains = readFleetFile("theFleetFile.csv", readYardFile("theYardFile.csv"));
        List<YardConfiguration> yardConfigurations = readYardFile("theYardFile.csv");

        // Output details of the Trains being simulated in this run
        System.out.println("Fall 2023 - Project 2 - Train Simulator");
        System.out.println("\n************** INITIALIZATION OF SIMULATION DETAILS BEGINS **************\n");
        System.out.println("Details of the Trains being simulated in this run\n");
        System.out.printf("%-20s%-20s%-20s%n", "Train Number", "Inbound Track", "Outbound Track");

        for (Train train : trains) {
            String trainInfo = String.format("%-20d%-20d%-20d",
                train.getTrainNumber(), train.getInboundTrack(), train.getOutboundTrack());
            System.out.println(trainInfo);
        }

        // Output details of the Track/Switch Alignments being simulated in the run
        System.out.println("\nDetails of the Track/Switch Alignments being simulated in this run\n");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%n", "Inbound Track #", "Switch 1", "Switch 2", "Switch 3", "Outbound Track #");

        for (YardConfiguration config : yardConfigurations) {
            System.out.printf("%-20d%-20d%-20d%-20d%-20d%n",
                config.getInboundTrack(), config.getFirstSwitch(), config.getSecondSwitch(),
                config.getThirdSwitch(), config.getOutboundTrack());
        }

        // Output details of the Tracks/Switch Alignments for each Train
        System.out.println("\nDetails of the Tracks/Switch Alignments for each Train:\n");
        for (Train train : trains) {
            train.printTrainDetails();
        }

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(30);

        // Add log message
        System.out.println("..........$ $ $ TRAIN MOVEMENT SIMULATION BEGINS........... $ $ $");
        System.out.println("Trains on permanent hold is at the top for some reason D: \n");
        System.out.println("Trains on permanent hold is also an error message in terminal for funsies too :D \n");




        // Launch trains
        for (Train train : trains) {
            executor.execute(train);
        }

        // Shutdown the thread pool when all tasks are completed
        executor.shutdown();

        // Wait for all trains to complete
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        // Add simulation end message
        System.out.println("..........$ $ $ SIMULATION ENDS $ $ $..........");
    }
    //read fleet file
   private static List<Train> readFleetFile(String fleetFileName, List<YardConfiguration> yardConfigurations) {
    List<Train> trains = new ArrayList<>();


    //get fleet info and store it for later use
    try (InputStream inputStream = TrainSchedulingSimulation.class.getResourceAsStream(fleetFileName);
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            int trainNumber = Integer.parseInt(parts[0]);
            int inboundTrack = Integer.parseInt(parts[1]);
            int outboundTrack = Integer.parseInt(parts[2]);

            // Assuming that the switches are read from the yard configuration
            YardConfiguration yardConfig = findYardConfiguration(yardConfigurations, inboundTrack, outboundTrack);

            if (yardConfig != null) {
                List<Integer> requiredSwitches = Arrays.asList(
                    yardConfig.getFirstSwitch(),
                    yardConfig.getSecondSwitch(),
                    yardConfig.getThirdSwitch()
                );

                trains.add(new Train(trainNumber, inboundTrack, outboundTrack, requiredSwitches));
            } else {
                // Handle the case where no matching YardConfiguration is found
                //prints as error just to make it red and look cool
                //prints at the top for some reason tho
                System.out.println("Train: " + trainNumber + " is on permanent hold\n");
                System.err.println("Train: " + trainNumber + " is on permanent hold\n");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return trains;
}


   // Helper method to find the YardConfiguration for a specific train
private static YardConfiguration findYardConfiguration(List<YardConfiguration> yardConfigurations, int inboundTrack, int outboundTrack) {
   for (YardConfiguration yardConfig : yardConfigurations) {
       if (yardConfig.getInboundTrack() == inboundTrack && yardConfig.getOutboundTrack() == outboundTrack) {
           return yardConfig;
       }
   }
   // Return null if no matching YardConfiguration is found
   return null;
}


    //read yard file
   private static List<YardConfiguration> readYardFile(String fileName) {
   List<YardConfiguration> configurations = new ArrayList<>();

    //store info for later use
   try (InputStream inputStream = TrainSchedulingSimulation.class.getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
       String line;
       while ((line = br.readLine()) != null) {
           String[] parts = line.split(",");
           int inboundTrack = Integer.parseInt(parts[0]);
           int firstSwitch = Integer.parseInt(parts[1]);
           int secondSwitch = Integer.parseInt(parts[2]);
           int thirdSwitch = Integer.parseInt(parts[3]);
           int outboundTrack = Integer.parseInt(parts[4]);
           configurations.add(new YardConfiguration(inboundTrack, firstSwitch, secondSwitch, thirdSwitch, outboundTrack));
       }
   } catch (Exception e) {
       e.printStackTrace();
   }

   return configurations;

}
}
