package interfaces;

import custexc.InvalidInputException;
import team.Team;

public interface ManageMatch {
    void toss(Team team1, Team team2) throws InvalidInputException;
    void startMatch() throws Exception;

    void displayOverallStatistics(int over);
    // void displayStatistics(int inning);
    
    void displayAvgRunsPerOver();
    void displayOverallStatistics();
    void displayResult();
    void displayScoreMenu();  
} 