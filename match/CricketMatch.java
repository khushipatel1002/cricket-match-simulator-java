package match;

import custexc.InvalidInputException;
import java.util.InputMismatchException;
import java.util.Scanner;
import player.Batsman;
import player.Bowler;
import team.Team;

public class CricketMatch{
    static Team[] availableTeams = {
        createTeam("South Africa", new String[][] {
            {"AB De", "bat"}, {"Imran", "bow"}, {"Faf", "bat"}, {"Rabada", "bow"},
            {"David", "bat"}, {"Maharaj", "bow"}, {"De Kock", "bat"}, {"Marco", "bow"},
            {"Markram", "bat"}, {"Ngidi", "bow"}, {"Klaasen", "bat"}
        }),
        createTeam("Pakistan", new String[][] {
            {"Babar", "bat"}, {"Shaheen", "bow"}, {"Rizwan", "bat"}, {"Shadab", "bow"},
            {"Imam", "bat"}, {"Ahmed", "bow"}, {"Salman", "bat"}, {"Naseem", "bow"},
            {"Nawaz", "bat"}, {"Haris", "bow"}, {"Zaman", "bat"}
        }),
        createTeam("India    ", new String[][] {
            {"Dhoni", "bat"}, {"Hardik", "bow"}, {"Rohit", "bat"}, {"Axar", "bow"},
            {"Virat", "bat"}, {"Bumrah", "bow"}, {"Rishabh", "bat"}, {"Jadeja", "bow"},
            {"Shivam", "bat"}, {"Gill", "bow"}, {"Suryakumar", "bat"}
        }),
        createTeam("Australia", new String[][] {
            {"Marsh", "bat"}, {"Green", "bow"}, {"David", "bat"}, {"Josh", "bow"},
            {"Warner", "bat"}, {"Cummins", "bow"}, {"Travis", "bat"}, {"Starc", "bow"},
            {"Matthew", "bat"}, {"Marcus", "bow"}, {"Maxwell", "bat"}
        })
    };

    public static Team createTeam(String teamName,String[][] playersData){        
        Team team = new Team(teamName);

        for (String[] player : playersData) {
            String playerName = player[0];
            String playerRole = player[1];

            if ("bat".equalsIgnoreCase(playerRole)) {
                team.addPlayer(new Batsman(playerName));
            } else if ("bow".equalsIgnoreCase(playerRole)) {
                team.addPlayer(new Bowler(playerName));
            } else {
                System.out.println("Invalid role for player: " + playerName);
            }
        }       
        return team;
    }

    static {
        System.err.println("***************************************************************************");
        System.out.println("*                      WELCOME TO THE CRICKET MATCH                       *");
        System.err.println("***************************************************************************");
    }

    public static void main(String[] args)throws InvalidInputException{        
        Scanner sc = new Scanner(System.in);
        int ch = 0;
        int totalOvers = 0;
        String matchType = "";

        final int ODI = 50;
        final int T20 = 20;
        final int TEST = 90;
        
        while(true){
            System.out.print("Select Match Type (1 - ODI, 2 - T20, 3 - TEST,4 - Other): ");
            try {
                ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        matchType = "ODI";
                        totalOvers = ODI;
                        break;
                    case 2:
                        matchType = "T20";
                        totalOvers = T20;
                        break;
                    case 3:
                        matchType = "TEST";
                        totalOvers = TEST;
                        break;
                    case 4:
                        matchType = "CUSTOM MATCH";

                        while (true) {
                            System.out.print("\nEnter Number of Total Overs :");
                            try{
                                totalOvers = sc.nextInt();
                                
                                if(totalOvers <= 0){
                                    throw  new InvalidInputException("Overs must be greater than 0");
                                }

                                break;
                            }catch(InputMismatchException e){
                                System.err.println("Invalid input! Please enter a valid number.");
                                sc.nextLine();
                            }catch(InvalidInputException e){
                                System.out.println( e.getMessage());
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid match type.Please Enter a valid number");
                        continue;
                }  
                break;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }

        System.out.println("Selected match type: " + matchType);
        System.out.println("Total Overs: " + totalOvers);

        // Selection of Teams ----------------
        System.out.println("\nAvailable Teams:");
        for(int i = 0; i < availableTeams.length; i++) {
            System.out.println((i + 1) + ". " + availableTeams[i].getTeamName());
        }

        // Selecttion of batting and bowling teams -----
        System.out.print("Select First Team (1|2|3|4): ");
        int baIndex = sc.nextInt() - 1;
        Team team1 = availableTeams[baIndex];

        System.out.print("Select Second Team (1|2|3|4): ");
        int boIndex = sc.nextInt() - 1;

        while (boIndex == baIndex) {
            System.out.print("Teams Cannot be the Same. Please Select again: ");
            boIndex = sc.nextInt() - 1;
        }
        Team team2 = availableTeams[boIndex];

        team1.displayTeam();
        team2.displayTeam();
        
        Match match = new Match(totalOvers);
        match.toss(team1, team2);

        System.err.println("\n***************************************************************************");
        System.out.println("*                              Match Started                              *");
        System.err.println("***************************************************************************");

        match.startMatch();
        match.displayScoreMenu();
    }
}