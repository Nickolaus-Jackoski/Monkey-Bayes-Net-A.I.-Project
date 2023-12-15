import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();
        System.out.print("Do you want to see debugging info (y/n): ");
        String debug = scanner.nextLine();

        MonkeyBayesNet monkeyBayesNet = new MonkeyBayesNet(filename);
        if(debug.equalsIgnoreCase("y")){
            monkeyBayesNet.debug = true;
            monkeyBayesNet.initialProbabilities();
            monkeyBayesNet.printLastLocationDistribution();
            System.out.println();
            monkeyBayesNet.printCurrentLocationDistribution();
            System.out.println();
            monkeyBayesNet.MotionSensorOne();
            System.out.println();
            monkeyBayesNet.MotionSensorTwo();
            System.out.println();
            // calls soundSensorLocation for each location
            System.out.println("Sound distribution: ");
            monkeyBayesNet.allSoundSensorLocation();
            monkeyBayesNet.printInitialDistribution();
            System.out.println();
            monkeyBayesNet.bayesNet();
        }
        else if(debug.equalsIgnoreCase("n")){
            monkeyBayesNet.debug = false;
            monkeyBayesNet.initialProbabilities();
            monkeyBayesNet.MotionSensorOne();
            monkeyBayesNet.MotionSensorTwo();
            monkeyBayesNet.allSoundSensorLocation();
            monkeyBayesNet.printInitialDistribution();
            System.out.println();
            monkeyBayesNet.bayesNet();
        }
    }
}
