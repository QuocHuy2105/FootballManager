/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public class TeamStatistic {
    
    private Integer numOfPlayers, numOfPLayedMatches, numOfWinMatches, numOfLoseMatches, numOfDrawMatches, numOfGoals, rank, totalPoints;

    public TeamStatistic() {
    }

    public TeamStatistic(Integer numOfPlayers, Integer numOfPLayedMatches, Integer numOfWinMatches, Integer numOfLoseMatches, Integer numOfDrawMatches, Integer numOfGoals, Integer rank, Integer totalPoints) {
        this.numOfPlayers = numOfPlayers;
        this.numOfPLayedMatches = numOfPLayedMatches;
        this.numOfWinMatches = numOfWinMatches;
        this.numOfLoseMatches = numOfLoseMatches;
        this.numOfDrawMatches = numOfDrawMatches;
        this.numOfGoals = numOfGoals;
        this.rank = rank;
        this.totalPoints = totalPoints;
    }

    public Integer getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(Integer numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public Integer getNumOfPLayedMatches() {
        return numOfPLayedMatches;
    }

    public void setNumOfPLayedMatches(Integer numOfPLayedMatches) {
        this.numOfPLayedMatches = numOfPLayedMatches;
    }

    public Integer getNumOfWinMatches() {
        return numOfWinMatches;
    }

    public void setNumOfWinMatches(Integer numOfWinMatches) {
        this.numOfWinMatches = numOfWinMatches;
    }

    public Integer getNumOfLoseMatches() {
        return numOfLoseMatches;
    }

    public void setNumOfLoseMatches(Integer numOfLoseMatches) {
        this.numOfLoseMatches = numOfLoseMatches;
    }

    public Integer getNumOfDrawMatches() {
        return numOfDrawMatches;
    }

    public void setNumOfDrawMatches(Integer numOfDrawMatches) {
        this.numOfDrawMatches = numOfDrawMatches;
    }

    public Integer getNumOfGoals() {
        return numOfGoals;
    }

    public void setNumOfGoals(Integer numOfGoals) {
        this.numOfGoals = numOfGoals;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "TeamStatistic{" + "numOfPlayers=" + numOfPlayers + ", numOfPLayedMatches=" + numOfPLayedMatches + ", numOfWinMatches=" + numOfWinMatches + ", numOfLoseMatches=" + numOfLoseMatches + ", numOfDrawMatches=" + numOfDrawMatches + ", numOfGoals=" + numOfGoals + ", rank=" + rank + ", totalPoints=" + totalPoints + '}';
    }
    
    
    
}
