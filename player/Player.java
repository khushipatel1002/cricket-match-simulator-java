package player;

public abstract class Player{
    private String name;
    private String role; // Batsman OR Bowler
    
    private int runScored;
    private int ballsFaced;

    public Player(String name,String role){
        this.name = name;
        this.role = role;
        this.runScored = 0;
        this.ballsFaced = 0;
    }

    public String getName(){
        return name;
    }
    
    public String getRole(){
        return role;
    }

    public int addRuns(int runs){
        this.runScored += runs;
        this.ballsFaced++;
        return this.runScored;
    }

    public int getRunScored(){
        return runScored;
    }

    public int getBallsFaced(){
        return ballsFaced;
    }

    // public abstract void calculateAverage();
    public abstract void displayPlayerStats();
}

