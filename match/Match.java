package match;

import custexc.InvalidInputException;
import interfaces.ManageMatch;
import java.util.InputMismatchException;
import java.util.Scanner;
import player.Batsman;
import player.Bowler;
import player.Player;
import team.Team;

public class Match implements ManageMatch{
    private Team battingTeam;
    private Team bowlingTeam;

    private final int totalOvers;
    private int totalRuns = 0;
 
    private int[] runsPerOver;
    private int[] sixesPerOver;
    private int[] foursPerOver;
    private int[] wideBallsPerOver;
    private int[] wicketsPerOver;
    private int[] noBallsPerOver;
 
    private int wickets = 0;
    private int wideBalls = 0;
    private int noBalls = 0;
    private int totalBalls;
    private int balls = 0;
    private int sixes = 0;
    private int fours = 0;
    private int oversBowled = 0;

    private final int maxWickets = 10;

    private int inning1Score = 0;
    private int inning2Score = 0;
    private int innings = 1; 
 
    private int inning1TotalRuns = 0;
    private int inning2TotalRuns = 0;
    private int wicketsInInning1 = 0;
    private int wicketsInInning2 = 0;
    private int widesInInning1 = 0;
    private int widesInInning2 = 0;
    private int noBallsInInning1 = 0;
    private int noBallsInInning2 = 0;

    private Player currentBatsman;
    private Player nextBatsman;
    private Bowler currentBowler;
    private int batsmanIndex = 0;
    private int bowlerIndex = 0; 

    public Match(int totalOvers){
        // this.battingTeam = battingTeam;
        // this.bowlingTeam = bowlingTeam;
        this.totalOvers = totalOvers;
        this.totalBalls = totalOvers * 6;

        this.runsPerOver = new int[totalOvers];
        this.sixesPerOver = new int[totalOvers];
        this.foursPerOver = new int[totalOvers];
        this.wideBallsPerOver = new int[totalOvers];
        this.wicketsPerOver = new int[totalOvers];
        this.noBallsPerOver = new int[totalOvers];
    }

    public void toss(Team team1, Team team2) throws InvalidInputException{
        Scanner sc = new Scanner(System.in);
        System.out.print("\nToss Time!\n");
        int ch;
        
        while(true){
            try {
                System.out.print("\n" + team1.getTeamName() + " Enter 1 for Heads or 2 for Tails : ");
                ch = sc.nextInt();
                
                if(ch == 1 || ch == 2){
                    break;
                }else{
                    // System.err.println("Invalid choice! Please enter 1 for Heads or 2 for Tails.");
                    throw new InvalidInputException("Invalid choice! Please enter 1 for Heads or 2 for Tails.");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid Input! Please Enter a valid Number");
                sc.nextLine();
            } catch(InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }

        int tossResult = (int)(Math.random() * 2) + 1;
        System.out.println("Toss Result : " + (tossResult == 1 ? "Heads" : "Tails"));

        Team tossWinner = (ch == tossResult) ? team1 : team2;
        System.out.println(tossWinner.getTeamName() + " WON THE TOSS!");

        int choice;
        while (true) { 
            try {
                System.out.print("\nChoose 1 for Batting OR 2 for Bowling : ");
                choice = sc.nextInt();

                if(choice == 1 || choice == 2){
                    break;
                }else{
                    // System.out.println("Invalid input! Please enter 1 for Batting or 2 for Bowling.");
                    throw new InvalidInputException("Invalid input! Please enter 1 for Batting or 2 for Bowling.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Please Enter a valid Number");
                sc.nextLine();
            } catch(InvalidInputException e){
                System.out.println(e.getMessage());
            }       
        }

        if(choice == 1){
            this.battingTeam = tossWinner;
            this.bowlingTeam = (tossWinner == team1) ? team2 : team1;
        }else{
            this.bowlingTeam = tossWinner;
            this.battingTeam = (tossWinner == team1) ? team2 : team1;
        }
 
        System.out.println("\nBatting Team: " + battingTeam.getTeamName());
        System.out.println("Bowling Team: " + bowlingTeam.getTeamName());
    }

    public void runsScored(int runs, int over){ // Overloaded method with two argument
        while(runs < 0 || runs > 6){
            System.out.print("Invalid Run ! Please enter runs between 0 and 6 :");
            Scanner sc = new Scanner(System.in);
            runs = sc.nextInt();
        }

        totalRuns += runs;
        runsPerOver[over] += runs;

        if(runs == 4){
            foursPerOver[over]++;
            fours++;
        }
        if(runs == 6){
            sixesPerOver[over]++;
            sixes++;
        }
        currentBatsman.addRuns(runs);
    }

    public void runsScored(int over){ // Overloaded method with one argument
        totalRuns++; // wide or noball run
        runsPerOver[over]++;
    }

    void handleWicket(int over) {
        wickets++;

        if (wickets >= maxWickets) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~");
            System.out.println("All players are out!");
            return;
        }

        wicketsPerOver[over]++;
        System.out.println("Wicket Fall! Score: " + totalRuns + " - " + wickets);
        
        currentBowler.addWickets();

        // Increase the batsman index when wicet fall
        batsmanIndex++;
        Player[] batsmen = battingTeam.getPlayersByRole("Batsman");
        Player[] bowlers = battingTeam.getPlayersByRole("Bowler");
        
        Player[] allPlayers = new Player[11];
        int i = 0;

        for(Player batsman : batsmen){
            allPlayers[i] = batsman;
            i++;
        }
        for(Player bowler : bowlers){
            allPlayers[i] = bowler;
            i++;
        }
    
        if (batsmanIndex < allPlayers.length) {
            currentBatsman = allPlayers[batsmanIndex];

            // Set the next batsman if available
            if (batsmanIndex + 1 < allPlayers.length) {
                nextBatsman = allPlayers[batsmanIndex + 1];
            } else {
                nextBatsman = null;
            }

            // currentBatsman = allPlayers[batsmanIndex];
            // nextBatsman = allPlayers[batsmanIndex + 1];

        }
    }    
    
    public void resetOverStatistics(int over){
            runsPerOver[over] = 0;
            sixesPerOver[over] = 0;
            foursPerOver[over] = 0;
            wideBallsPerOver[over] = 0;
            wicketsPerOver[over] = 0;
            noBallsPerOver[over] = 0;
    }
    
    public void resetForNextInnings() {
        totalRuns = 0;
        wickets = 0;
        balls = 0;
        wideBalls = 0;
        noBalls = 0;
        sixes = 0;
        fours = 0;
        batsmanIndex = 0;
        bowlerIndex = 0;
        oversBowled = 0;

        Player[] batsmen = battingTeam.getPlayersByRole("Batsman");
        Player[] bowlers = battingTeam.getPlayersByRole("Bowler");

        Player[] allPlayers = new Player[11];

        int index = 0;
            for(Player batsman : batsmen){
                allPlayers[index] = batsman;
                index++;
            }
            for(Player bowler : bowlers){
                allPlayers[index] = bowler;
                index++;
            }

        // currentBatsman = batsmen[batsmanIndex];
        // nextBatsman = batsmen[batsmanIndex + 1];

        if(allPlayers.length > 0){
            currentBatsman = allPlayers[batsmanIndex];
        }else{
            currentBatsman = null;
        }

        if(batsmanIndex + 1 < allPlayers.length){
            nextBatsman = batsmen[batsmanIndex + 1];
        }else{
            nextBatsman = null;
        }

        // Player[] bowlers = bowlingTeam.getPlayersByRole("Bowler");
            
        currentBowler = (Bowler) bowlers[bowlerIndex];
    } 

    public void switchTeams() {
        Team temp = battingTeam;
        battingTeam = bowlingTeam;
        bowlingTeam = temp;
        resetForNextInnings();
    }

    public void startMatch(){
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n\nInnings " + innings + " - " + battingTeam.getTeamName() + " is batting.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            Player[] batsmen = battingTeam.getPlayersByRole("Batsman");
            Player[] bowlers = battingTeam.getPlayersByRole("Bowler");

            Player[] allPlayers = new Player[11];

            int index = 0;
            for(Player batsman : batsmen){
                allPlayers[index] = batsman;
                index++;
            }
            for(Player bowler : bowlers){
                allPlayers[index] = bowler;
                index++;
            }
            // currentBatsman = batsmen[batsmanIndex];
            // nextBatsman = batsmen[batsmanIndex + 1];

            if (allPlayers.length > batsmanIndex) {
                currentBatsman =  allPlayers[batsmanIndex];
            } else {
                currentBatsman = null;
            }

            if (batsmanIndex + 1 < allPlayers.length) {
                nextBatsman = allPlayers[batsmanIndex + 1];
            } else {
                nextBatsman = null;
            }

            currentBowler = (Bowler) bowlers[bowlerIndex];
            // currentBowler.addOversBowled();
            
            System.out.print("Enter Runs for Valid Ball OR 'W' for Wicket OR 'WD' for Wide Ball OR 'N' for No Ball: \n");

            for (int over = 0; over < totalOvers && wickets < maxWickets; over++) {
                System.out.println("\nOver: " + (over + 1));
                System.out.println("Bowler : " + currentBowler.getName());
                resetOverStatistics(over);
    
                for (int ball = 0; ball < 6 && wickets < maxWickets; ball++) {
                    System.out.println("\nBatsman : " + currentBatsman.getName());
                    System.out.print("Ball - " + (over + 1) + "." + (ball + 1) + " : ");                    
                    String input = sc.next();
    
                    if(input.equalsIgnoreCase("w")) {//Wicket
                        handleWicket(over);
                        balls++;
                    }else if (input.equalsIgnoreCase("wd")) {// Wide Ball
                        wideBalls++;
                        wideBallsPerOver[over]++;
                        runsScored(over);
                        System.out.println("Wide Ball! Score: " + totalRuns + " - " + wickets);
                        ball--;
                    } else if (input.equalsIgnoreCase("n")) { // No ball
                        noBalls++;
                        noBallsPerOver[over]++;
                        runsScored(over);
    
                        System.out.println("No Ball! Score: " + totalRuns + " - " + wickets);
                        int freeHitRuns;
                        String nbInput;
                        
                        System.out.print("FREE HIT! Enter runs scored on Free Hit: ");                        
                        try {
                            nbInput = sc.next();  
                            if(nbInput.equalsIgnoreCase("w")){
                                System.out.print("Is there a run-out? (1 for Yes, 0 for No): ");
                                int runOut = sc.nextInt();
                                
                                if (runOut == 1) {
                                    System.out.println("Run-out!");
                                    handleWicket(over);
                                }
                            }else{                            
                                    // int freeHitRuns;
                                    freeHitRuns = Integer.parseInt(nbInput);
                                    runsScored(freeHitRuns, over); 
                                    System.out.println("Score: " + totalRuns + " - " + wickets);                   
                            } 
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input! Please Enter valid run OR 'W' for wicket");
                        }
                                            
                        ball--; // No ball doesn't count
                    }else { // Valid/Legal Ball
                        try {
                            int runs;
                            runs = Integer.parseInt(input);
                            runsScored(runs, over);                          
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please Enter a valid Number.");
                            ball--;
                        }catch (NumberFormatException e) {
                            System.out.println("Invalid Input. Please enter a number for runs or 'W' for Wide or 'N' for No Ball.");
                            ball--;
                        }
                        balls++;
                    }
                }
                System.out.println("Score: " + totalRuns + " - " + wickets);
            
                currentBowler.addOversBowled();

                bowlerIndex = (bowlerIndex + 1) % bowlers.length;
                currentBowler = (Bowler) bowlers[bowlerIndex];            
                
                displayOverallStatistics(over); // Display after each over
                oversBowled++;
            }
            
            System.out.println("\n" + battingTeam.getTeamName().toUpperCase() +  " SCORE : " + totalRuns + "-" + wickets);

            if (innings == 1) {
                inning1Score = totalRuns;
                wicketsInInning1 = wickets;
                widesInInning1 = wideBalls;
                noBallsInInning1 = noBalls;
                inning1TotalRuns = totalRuns;                
            } else {
                inning2Score = totalRuns;
                wicketsInInning2 = wickets;
                widesInInning2 = wideBalls;
                noBallsInInning2 = noBalls;
                inning2TotalRuns = totalRuns;
            }
            
            displayAvgRunsPerOver();
            // displayStatistics(innings);
            // displayPlayerScores();
    
            if (innings == 2) {// End of the second innings
                displayOverallStatistics(); 
                displayResult();      
                break;
            }
    
            switchTeams();
            resetForNextInnings();
            innings++;
        } while (innings <= 2);
    }

    // public void displayPlayerScores(){
    //     System.out.println("\nScore of Each Player for Team " + battingTeam.getTeamName());
    //     System.out.println("Player Name\tRole\tRuns Scored\tBalls Faced");

    //     for(Player player : pbattingTeam.getPlayers()){
    //         // if(player instanceof Batsman){
    //         //     Batsman batsman = (Batsman)player;
    //             System.out.println(player.getName() + "\t\t"+ player.getRole() + "\t" + player.getRunScored() + "\t\t" + player.getBallsFaced());
    //         // }
    //     }
    // }

    public void displayPlayerScoresByTeam(Team team){
        System.err.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|                               Score of Each Player for Team " + team.getTeamName().toUpperCase() + "                                |");
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Player Name\tRole\tRuns Scored\tBalls Faced");
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        for (Player player : team.getPlayers()) {
            if (player instanceof Batsman) {
                Batsman batsman = (Batsman) player;
                batsman.displayPlayerStats();
                // System.out.println(player.getName() + "\t\t" + player.getRole() + "\t" + player.getRunScored() + "\t\t" + player.getBallsFaced());
            } else if (player instanceof Bowler) {
                Bowler bowler = (Bowler) player;
                bowler.displayPlayerStats();
                // System.out.println(player.getName() + "\t\t" + player.getRole() + "\t" + player.getRunScored() + "\t\t" + player.getBallsFaced() + "\t(Wickets: " + bowler.getWicketsTaken() + ")");
            }
        }
    }

    public void displayScoreMenu() {
        Scanner sc = new Scanner(System.in);
        int ch = 0;
    
        do {
            System.out.println("\nWhich Team Player's Scores do you want to view?");
            System.out.println("1. " + battingTeam.getTeamName());
            System.out.println("2. " + bowlingTeam.getTeamName());
            System.out.println("3. Exit");
            
            try{
                System.out.print("Enter your choice (1, 2, or 3): ");
                ch = sc.nextInt();
    
                switch (ch) {
                    case 1:
                        displayPlayerScoresByTeam(battingTeam);
                        break;
                    case 2:
                        displayPlayerScoresByTeam(bowlingTeam);
                        break;
                    case 3:
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid Input! please Enter valid number.");
                sc.nextLine();
            }
        } while (ch != 3);
    }

    public void displayAvgRunsPerOver(){
        int totalRuns = 0;
        // int oversBowled = runsPerOver.length;

        for(int i = 0; i < oversBowled;i++){
            totalRuns += runsPerOver[i];
        }

        double avgRunsPerOver = (double)totalRuns / oversBowled;

        System.out.println("\n\n|                    Average Runs Per Over                  |");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Overs Bowled\tTotal Runs\tAverage Runs Per Over");
        System.out.println("-------------------------------------------------------------");
        System.out.println(oversBowled + "\t\t\t" + totalRuns + "\t\t" + avgRunsPerOver);
        System.out.println("-------------------------------------------------------------");
    }

    // public void displayMatchDetails() {
    //     System.out.println("--------------------------------------------------------------------------------------------------------------------");
    //     System.out.println("Total Overs\tTotal Runs\tTotal Wickets\tTotal Wide Balls\tTotal No Balls\tTotal Left Balls");
    //     System.out.println("--------------------------------------------------------------------------------------------------------------------");
    //     System.out.println(totalOvers + "\t\t" + totalRuns + "\t\t" + wickets + "\t\t" + wideBalls + "\t\t\t" + noBalls + "\t\t" + (totalBalls - balls));
    //     System.out.println("--------------------------------------------------------------------------------------------------------------------");
    // }

    public void displayOverallStatistics(int over){ // Overloded method with one argument
        System.out.println("\n\n|                   Over " + (over + 1) + " Statistics                   |");
        System.out.println("---------------------------------------------------------");
        System.out.println("Runs\t6's\t4's\tWides\tNo Balls\tWickets");
        System.out.println("---------------------------------------------------------");
        System.out.println(runsPerOver[over] + "\t" + sixesPerOver[over] + "\t" + foursPerOver[over] + "\t" + wideBallsPerOver[over] + "\t" + noBallsPerOver[over] + "\t\t" + wicketsPerOver[over]);
        System.out.println("---------------------------------------------------------");
    }

    public void displayStatistics(int inning){
        System.err.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|                                        MATCH STATISTICS FOR INNING " + inning + "                                         |");
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Runs\t6's\t4's\tWickets\t\tWide Balls\tNo Balls\tTotal Balls Played\tTotal Left Balls");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println(totalRuns + "\t" + sixes + "\t" + fours + "\t" + wickets + "\t\t" + wideBalls + "\t\t" + noBalls + "\t\t\t" + balls + "\t\t\t" + (totalBalls - balls));
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public void displayOverallStatistics(){ // Overloded method with no argument
        System.err.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|                                           OVERALL MATCH STATISTICS                                           |");
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // System.out.println("\n-----------------------------------------------------------------------------------------------------------------");
        System.out.println("Inning\tTeam\t\t\tRuns\tWickets\tWide Balls\tNo Balls");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println("1\t" + bowlingTeam.getTeamName() + "\t\t" + inning1TotalRuns + "\t" + wicketsInInning1 + "\t" + widesInInning1 + "\t\t" + noBallsInInning1);
        System.out.println("2\t" + battingTeam.getTeamName() + "\t\t" + inning2TotalRuns + "\t" + wicketsInInning2 + "\t" + widesInInning2 + "\t\t" + noBallsInInning2);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public void displayResult(){
        System.err.println("\n\n****************************************************************************************************************");
        System.out.println("*                                                 MATCH RESULT                                                 *");
        System.err.println("****************************************************************************************************************");

        inning1Score = inning1TotalRuns;
        inning2Score = inning2TotalRuns;

        System.out.println(bowlingTeam.getTeamName().toUpperCase() + " SCORED : " + inning1Score);
        System.out.println(battingTeam.getTeamName().toUpperCase() + " SCORED : " + inning2Score);
        
        if (inning1Score > inning2Score) {
            System.out.println("\n" + bowlingTeam.getTeamName().toUpperCase() + " WINS BY " + (inning1Score - inning2Score) + " RUNS.");
        } else if (inning2Score > inning1Score) {
            System.out.println("\n" + battingTeam.getTeamName().toUpperCase() + " WINS BY " + (maxWickets - wicketsInInning2) + " WICKETS.");
        } else {
            System.out.println("\nMATCH IS TIE!");
        }
        System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
