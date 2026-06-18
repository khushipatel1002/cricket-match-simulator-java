package player;

public class Batsman extends Player{
    // private int runScored = 0;
    // private int ballsFaced = 0;

    public Batsman(String name){
        super(name,"Batsman");
    }

    // public int addRuns(int runs){
    //     this.runScored += runs;
    //     this.ballsFaced++;
    //     return this.runScored;
    // }

    // public int getRunScored(){
    //     return runScored;
    // }

    // public int getBallsFaced(){
    //     return ballsFaced;
    // }

    // public void calculateAverage(){  
    //     // System.out.println("Batting Average : " + (runScored /(double)ballsFaced ));
    //     // System.out.println("Batting Average : " + (ballsFaced > 0 ? runScored / (double) ballsFaced : 0));

    //     if(super.ballsFaced > 0){
    //         double strikeRate = (double) runScored / ballsFaced * 100;
    //         System.out.println("Strike rate : %.2f%n" + strikeRate);
    //     }else{
    //         System.err.println("No balls Faced");
    //     }
    // }

    public void displayPlayerStats() {
        // System.out.println("Player: " + getName() + " | Role: Batsman");
        // // System.out.println("Player: " + getName());
        // System.out.println("Runs Scored: " + runScored + "\n | Balls Faced: " + ballsFaced);
        System.out.println(getName() + "\t\t" + getRole() + "\t" + getRunScored() + "\t\t" + getBallsFaced());
    }
}
