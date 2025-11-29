/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

import N23DCCN058.fm.model.PlayerPosition;

/**
 *
 * @author WokWee
 */
public class PlayerStatistic {
    private Integer numOfPlayedMatches, numOfStartingRole, totalPlayedTime, numOfGoals, numOfPenaltyGoals, numOfOwnGoals, numOfFouls, numOfYellowCards, numOfRedCards;



    public PlayerStatistic() {
    }

    public PlayerStatistic(Integer numOfPlayedMatches, Integer numOfStartingRole, Integer totalPlayedTime, Integer numOfGoals, Integer numOfPenaltyGoals, Integer numOfOwnGoals, Integer numOfFouls, Integer numOfYellowCards, Integer numOfRedCards) {
        this.numOfPlayedMatches = numOfPlayedMatches;
        this.numOfStartingRole = numOfStartingRole;
        this.totalPlayedTime = totalPlayedTime;
        this.numOfGoals = numOfGoals;
        this.numOfPenaltyGoals = numOfPenaltyGoals;
        this.numOfOwnGoals = numOfOwnGoals;
        this.numOfFouls = numOfFouls;
        this.numOfYellowCards = numOfYellowCards;
        this.numOfRedCards = numOfRedCards;
    }

    public Integer getNumOfPlayedMatches() {
        return numOfPlayedMatches;
    }

    public void setNumOfPlayedMatches(Integer numOfPlayedMatches) {
        this.numOfPlayedMatches = numOfPlayedMatches;
    }

    public Integer getNumOfStartingRole() {
        return numOfStartingRole;
    }

    public void setNumOfStartingRole(Integer numOfStartingRole) {
        this.numOfStartingRole = numOfStartingRole;
    }

    public Integer getTotalPlayedTime() {
        return totalPlayedTime;
    }

    public void setTotalPlayedTime(Integer totalPlayedTime) {
        this.totalPlayedTime = totalPlayedTime;
    }

    public Integer getNumOfGoals() {
        return numOfGoals;
    }

    public void setNumOfGoals(Integer numOfGoals) {
        this.numOfGoals = numOfGoals;
    }

    public Integer getNumOfPenaltyGoals() {
        return numOfPenaltyGoals;
    }

    public void setNumOfPenaltyGoals(Integer numOfPenaltyGoals) {
        this.numOfPenaltyGoals = numOfPenaltyGoals;
    }

    public Integer getNumOfOwnGoals() {
        return numOfOwnGoals;
    }

    public void setNumOfOwnGoals(Integer numOfOwnGoals) {
        this.numOfOwnGoals = numOfOwnGoals;
    }

    public Integer getNumOfFouls() {
        return numOfFouls;
    }

    public void setNumOfFouls(Integer numOfFouls) {
        this.numOfFouls = numOfFouls;
    }

    public Integer getNumOfYellowCards() {
        return numOfYellowCards;
    }

    public void setNumOfYellowCards(Integer numOfYellowCards) {
        this.numOfYellowCards = numOfYellowCards;
    }

    public Integer getNumOfRedCards() {
        return numOfRedCards;
    }

    public void setNumOfRedCards(Integer numOfRedCards) {
        this.numOfRedCards = numOfRedCards;
    }
    
    @Override
    public String toString() {
        return "PlayerStatistic{" + "numOfPlayedMatches=" + numOfPlayedMatches + ", numOfStartingRole=" + numOfStartingRole + ", totalPlayedTime=" + totalPlayedTime + ", numOfGoals=" + numOfGoals + ", numOfPenaltyGoals=" + numOfPenaltyGoals + ", numOfOwnGoals=" + numOfOwnGoals + ", numOfFouls=" + numOfFouls + ", numOfYellowCards=" + numOfYellowCards + ", numOfRedCards=" + numOfRedCards + '}';
    }
    
}
