package team;
import player.Player;

public class Team{
    private String teamName;
    private Player[] players;
    int playerCount;

    public Team(String teamName){
        this.teamName = teamName;
        this.players = new Player[11];
        this.playerCount = 0;
    }

    public String getTeamName(){
        return teamName;
    }

    public Player[] getPlayers(){
        return players;
    }

    public void addPlayer(Player player){ // add player object to players array
        if(playerCount < players.length){
            players[playerCount] = player; // Add player to the array
            playerCount++;
        }else{
            System.out.println("Cannot add more Players");
        }
    }
    
    public void displayTeam(){
        // System.out.println("\n----------------------------");
        System.err.println();
        System.out.println("      Team - " + teamName );
        System.out.println("----------------------------");
        System.out.println("Player Name\tPlayer Role");
        System.out.println("----------------------------");

        for(int i=0;i<playerCount;i++){
            System.out.println(players[i].getName() + " \t\t" + players[i].getRole());
        }
    }

    public Player[] getPlayersByRole(String role) {
        int count = 0;
        
        // First, calculate the number of players with the given role
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getRole().equalsIgnoreCase(role)) {
                count++;
            }
        }
        
        // Create an array to store players with the matching role
        Player[] playersByRole = new Player[count];
        int index = 0;
    
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getRole().equalsIgnoreCase(role)) {
                playersByRole[index++] = players[i];
            }
        }
        
        return playersByRole;
    } 
}
