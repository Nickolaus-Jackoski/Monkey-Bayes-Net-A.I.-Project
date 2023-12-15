import java.util.Comparator;

public class SoundProb {
    private int row,col;
    private double prob;

    public SoundProb(int row, int col, double prob){
        this.row = row;
        this.col = col;
        this.prob = prob;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getProb() {
        return prob;
    }

    // compares the two Probs and returns -1 if the proability of a > b, 1 if b > a and 0 if equal
    public static class compareProb implements Comparator<SoundProb> {
            public int compare(SoundProb a, SoundProb b)
            {
                if(a.getProb() == b.getProb()){
                    return 0;
                }
                if(a.getProb() > b.getProb()){
                    return -1;
                }
                else {
                    return 1;
                }
            }
    }
}
