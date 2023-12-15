//I have neither given nor received unauthorized aid on this program
import java.util.*;
import java.io.*;

public class MonkeyBayesNet {
    private int row, col;
    private double[][] lastLocation;
    private List<int []> sensorReadings = new ArrayList<>();
    public boolean debug;
    public double [][] prob;
    public double soundProb [][][][];
    private int time;
    public double movProb [][][][];
    double[][] currentLocationM1;
    double[][] falseCurrentLocationM1;
    double[][] currentLocationM2;
    double[][] falseCurrentLocationM2;

    public MonkeyBayesNet(String fileName) throws FileNotFoundException {
        readFile(fileName);
        prob =  new double[row][col];
        soundProb = new double[row][col][row][col];
        movProb = new double[row][col][row][col];
        time = 0;
        movementProbs();
        currentLocationM1 =  new double[row][col];
        falseCurrentLocationM1 = new double[row][col];
        currentLocationM2 = new double[row][col];
        falseCurrentLocationM2 = new double[row][col];
        debug = false;
    }
    private void readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        // Read the first line for dimensions
        String[] dimensions = scanner.nextLine().split(" ");
        row = Integer.parseInt(dimensions[0]);
        col = Integer.parseInt(dimensions[1]);

        // Initialize the lastLocation 2D array
        lastLocation = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                lastLocation[i][j] = 1.0 / (row * col);
            }
        }

        // Read and process the remaining lines
        while (scanner.hasNextLine()) {
            String [] splitLine = scanner.nextLine().split(" ");
            int [] readings = new int[splitLine.length];

            for(int i = 0; i < splitLine.length; i++){
                readings[i] = Integer.parseInt(splitLine[i]);
            }
            sensorReadings.add(readings);
        }
        scanner.close();
    }

    //calcuates the initial probabilities at each location
    public double[][] initialProbabilities(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                prob[i][j] = 1.0 / (row * col);
            }
        }
        return prob;
    }

    // populates the moveProb 4d array with next location probabilities for all current locations
    public void movementProbs(){
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++){
                List<int []> temp = getNextLocations(i,j);
                for(int k = 0; k < temp.size(); k++){
                    movProb[i][j][temp.get(k)[0]][temp.get(k)[1]] = 1.0 / temp.size();
                }
            }
        }
    }

    public void printLastLocationDistribution(){
        System.out.println("Last location distribution:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.println("Last location: (" +i + ", " + j + "), prob:" + String.format("%.8f",prob[i][j]));
            }
        }
    }

    public void printCurrentLocationDistribution() {
        System.out.println("Current location distribution:");

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.println("Last location: (" + i + ", " + j + ")");

                List<int []> nextLocations = getNextLocations(i, j);
                for (int k = 0; k < nextLocations.size(); k++) {
                    System.out.println("  Current location: (" + nextLocations.get(k)[0] + ", " + nextLocations.get(k)[1] + "), prob: " + String.format("%.8f", 1.0 / nextLocations.size()));
                }
            }
        }
    }

    // gets the next location of the agent based off of current locaiton
    private List <int[]> getNextLocations(int currRow, int currCol) {
        List<int[]> tempCoordinates = new ArrayList<>();

        if (currRow > 0) {
            tempCoordinates.add(new int[]{currRow - 1, currCol});
        }
        if (currRow < row - 1) {
            tempCoordinates.add(new int[]{currRow + 1, currCol});
        }
        if (currCol > 0) {
            tempCoordinates.add(new int[]{currRow, currCol - 1});
        }
        if (currCol < col - 1) {
            tempCoordinates.add(new int[]{currRow, currCol + 1});
        }

        return tempCoordinates;
    }

    // calculates probabilities for motion sensor 1 located in the top-left corner
    public void MotionSensorOne() {
        if(debug) {
            System.out.println("Motion sensor #1 (top left) distribution ");
        }
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++)
            {
                currentLocationM1[i][j] = 0.05;
            }
        }
        // True probabilities
        // for column 0
        for(int i = 0; i < row; i++)
        {
            currentLocationM1[i][0] = 0.9 - 0.1 * i;
        }
        // for row 0
        for(int i = 0; i < col; i++)
        {
            currentLocationM1[0][i] = 0.9 - 0.1 * i;
        }
        // false probabilities
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++)
            {
                falseCurrentLocationM1[i][j] = 1 - currentLocationM1[i][j];
                if(debug){
                    System.out.println("Current location: (" + i + ", " + j + "), " + "true prob: " + String.format("%.8f",currentLocationM1[i][j]) +
                            ", false prob: " +  String.format("%.8f", falseCurrentLocationM1[i][j]));
                }
            }
        }
    }

    // calculates probabilities for motion sensor 2 located in the bottom-right corner
    public void MotionSensorTwo() {
        if(debug){
            System.out.println("Motion sensor #2 (bottom right) distribution ");
        }
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                currentLocationM2[i][j] = 0.05;
            }
        }
        // True probabilities
        // for column 0
        for(int i = 0; i < row; i++) {
            currentLocationM2[row - i - 1][col - 1] = 0.9 - 0.1 * i;
        }
        // for row 0
        for(int i = 0; i < col; i++) {
            currentLocationM2[row - 1][col - i - 1] = 0.9 - 0.1 * i;
        }
        // false probabilities
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++)
            {
                falseCurrentLocationM2[i][j] = 1 - currentLocationM2[i][j];
                if(debug){
                    System.out.println("Current location: (" + i + ", " + j + "), " + "true prob: " + String.format("%.8f",currentLocationM2[i][j]) +
                            ", false prob: " +  String.format("%.8f", falseCurrentLocationM2[i][j]));
                }
            }
        }
    }

    // calculates the probability of location for any sound sensor input
    public void soundSensorLocation(int i, int j){
        double[][] currentLocation = new double[row][col];
        List <int[]> tempLocation;
        List <int[]> tempLocation2 = new ArrayList<>();
        currentLocation[i][j] = 0.6;
        tempLocation = getNextLocations(i,j);
        for(int k = 0; k < tempLocation.size(); k++) {
            currentLocation[tempLocation.get(k)[0]][tempLocation.get(k)[1]] = 0.3/tempLocation.size();
        }
        for(int k = 0; k < tempLocation.size(); k++) {
            List <int[]> temp = getNextLocations(tempLocation.get(k)[0],tempLocation.get(k)[1]);
            for(int a = 0; a < temp.size(); a++){
                if(!contains(tempLocation2, temp.get(a)) && !(i == temp.get(a)[0] && j == temp.get(a)[1])){
                    tempLocation2.add(temp.get(a));
                }
            }
        }

        for(int a = 0; a < tempLocation2.size(); a++){
            currentLocation[tempLocation2.get(a)[0]][tempLocation2.get(a)[1]] = 0.1/(tempLocation2.size());
        }

        // sort and print the results
        List<SoundProb> soundProbLst = new ArrayList<>();

        for(int k = 0; k < row; k++){
            for(int a = 0; a < col; a++){
                soundProbLst.add(new SoundProb(k,a,currentLocation[k][a]));
            }
        }

        // sorts soundProbLst using compareProb to order the SoundProb objects by descending probability.
        Collections.sort(soundProbLst, new SoundProb.compareProb());

        if(debug){
            System.out.println("Current location: (" + i + ", " + j + ")");
            for(int k = 0; k < soundProbLst.size(); k++){
                SoundProb sp = soundProbLst.get(k);
                if(sp.getProb() > 0){
                    System.out.println("  Sound reported at: (" + sp.getRow() + ", " + sp.getCol() + "), prob: " + String.format("%.8f", sp.getProb()));
                }
            }
        }


        double[][] soundProbLoc = new double[row][col];

        for(int k = 0; k < soundProbLst.size(); k++){
            SoundProb sp = soundProbLst.get(k);
            soundProbLoc[sp.getRow()][sp.getCol()] = sp.getProb();
            soundProb[i][j][sp.getRow()][sp.getCol()] = sp.getProb();
        }
    }

    public boolean contains(List<int []> lst, int[] elm){
        for(int i = 0; i < lst.size(); i++){
            if(lst.get(i)[0] == elm[0] && lst.get(i)[1] == elm[1]){
                return true;
            }
        }
        return false;
    }
    // loops through the sound sensor locations
    public void allSoundSensorLocation(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j< col; j++){
                soundSensorLocation(i,j);
            }
        }
    }
    public void printInitialDistribution(){
        System.out.println();
        System.out.println("Initial distribution of monkey's last location: ");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("  " + String.format("%.8f",prob[i][j]));
            }
            System.out.println();
        }
    }

    // calculates the probability of each location in a single step
    public void bayesNetSingleStep(){
        boolean m1 = false;
        boolean m2 = false;
        double [][] currProb = new double[row][col];
        if(sensorReadings.get(time)[0] == 1){
            m1 = true;
        }
        if(sensorReadings.get(time)[1] == 1){
            m2 = true;
        }
        int [] soundLoc = new int[2];
        soundLoc[0] = sensorReadings.get(time)[2];
        soundLoc[1] = sensorReadings.get(time)[3];
        System.out.println("Observation: Motion1: " + m1 + ", " +
                "Motion2: " + m2 + ", Sound location: (" + soundLoc[0] + ", " + soundLoc[1] +")");
        System.out.println("Monkey's predicted current location at time step: " + time);
        for(int i = 0; i < row; i++){
            for(int j = 0; j <col; j++){
                currProb[i][j] = singleStepSingPoint(m1,m2,soundLoc,i,j);
            }
        }
        if(debug){
            System.out.println();
            System.out.println("Before normalization: ");
        }
        double sum = 0;

        for(int i= 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(debug){
                    System.out.print("  " + String.format("%.8f",currProb[i][j]));
                }
                sum+=currProb[i][j];
            }
            if(debug){
                System.out.println();
            }
        }
        if(debug){
            System.out.println();
            System.out.println("After normalization: ");
        }

        for(int i= 0; i < row; i++){
            for(int j = 0; j < col; j++){
                currProb[i][j] = currProb[i][j]/sum;
                System.out.print("  " + String.format("%.8f",currProb[i][j]));
            }
            System.out.println();
        }
        prob = currProb;
        System.out.println();

    }

    // loops through the sensor readings/steps of the BayesNet calculations
    public void bayesNet(){
        while(time < sensorReadings.size()){
            bayesNetSingleStep();
            time+=1;
        }
    }

    public double singleStepSingPoint(boolean m1, boolean m2, int [] soundLoc, int i, int j ){
        if(debug){
            System.out.println("  Calculating total prob for current location (" + i + ", " + j+ ") ");
        }
        double currProb;
        double totalProb = 0;
        double m1Prob;
        double m2Prob;

        // loops through the row,col from the last location and multiplies and prints out the total probabilities
        for(int lastLoci = 0; lastLoci < row; lastLoci++){
            for(int lastLocj = 0; lastLocj < col; lastLocj++){
                if(m1 == true){
                    m1Prob = currentLocationM1[i][j];
                }
                else{
                    m1Prob = falseCurrentLocationM1[i][j];
                }
                if(m2 == true){
                    m2Prob = currentLocationM2[i][j];
                }
                else{
                    m2Prob = falseCurrentLocationM2[i][j];
                }
                if(debug){
                    System.out.print("    Probs being multiplied for last location (" + lastLoci + ", " + lastLocj + "): ");
                    System.out.println( prob[lastLoci][lastLocj] + " " + movProb[lastLoci][lastLocj][i][j] +
                            " " + String.format("%.1f",m1Prob) + " " + String.format("%.1f",m2Prob) + " " + soundProb[i][j][soundLoc[0]][soundLoc[1]]);
                }
                currProb = prob[lastLoci][lastLocj] * movProb[lastLoci][lastLocj][i][j] *
                        soundProb[i][j][soundLoc[0]][soundLoc[1]] * m1Prob * m2Prob;
                totalProb += currProb;
            }
        }
        return totalProb;
    }

}