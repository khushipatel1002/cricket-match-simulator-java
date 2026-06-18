package player;

public class Bowler extends Player{
    private int wicketsTaken = 0;
    private int oversBowled = 0;

    public Bowler(String name){
        super(name,"Bowler");
        // this.wicketsTaken = 0;
        // this.oversBowled = 0;
    }

    public void addWickets(){
        this.wicketsTaken++;
        // return  this.wicketsTaken;
    }

    public void addOversBowled(){
        this.oversBowled++;
    }

    public int getWicketsTaken(){
        return wicketsTaken;
    }

    public int getOversBowled(){
        return oversBowled;
    }

    // public void calculateAverage(){
    //     // System.out.println("Bowling Average : " + (wicketsTaken / (double)oversBowled));
    //     // System.out.println("Bowling Average : " + (oversBowled > 0 ? wicketsTaken / (double) oversBowled : 0));
    //     System.out.println("Not applicable for Bowler");
    // }

    public void displayPlayerStats() {
        // System.out.println("Bowler Name: " + getName());
        // System.out.println("Wickets Taken: " + wicketsTaken);
        // System.out.println("Overs Bowled: " + oversBowled);
        System.out.println(getName() + "\t\t" + getRole() + "\t" + getRunScored() + "\t\t" + getBallsFaced() + "\t(Wickets: " + wicketsTaken + ", Overs - " + oversBowled + ")");
    }
}
