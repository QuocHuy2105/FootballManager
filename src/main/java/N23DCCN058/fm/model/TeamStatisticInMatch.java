/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public class TeamStatisticInMatch {
    private Integer totalGoals, totalPenaltyGoals, totalOwnGoals, totalFouls, totalYellowCards, totalRedCards;

    public TeamStatisticInMatch() {
    }

    public TeamStatisticInMatch(Integer totalGoals, Integer totalPenaltyGoals, Integer totalOwnGoals, Integer totalFouls, Integer totalYellowCards, Integer totalRedCards) {
        this.totalGoals = totalGoals;
        this.totalPenaltyGoals = totalPenaltyGoals;
        this.totalOwnGoals = totalOwnGoals;
        this.totalFouls = totalFouls;
        this.totalYellowCards = totalYellowCards;
        this.totalRedCards = totalRedCards;
    }

    public Integer getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(Integer totalGoals) {
        this.totalGoals = totalGoals;
    }

    public Integer getTotalPenaltyGoals() {
        return totalPenaltyGoals;
    }

    public void setTotalPenaltyGoals(Integer totalPenaltyGoals) {
        this.totalPenaltyGoals = totalPenaltyGoals;
    }

    public Integer getTotalOwnGoals() {
        return totalOwnGoals;
    }

    public void setTotalOwnGoals(Integer totalOwnGoals) {
        this.totalOwnGoals = totalOwnGoals;
    }

    public Integer getTotalFouls() {
        return totalFouls;
    }

    public void setTotalFouls(Integer totalFouls) {
        this.totalFouls = totalFouls;
    }

    public Integer getTotalYellowCards() {
        return totalYellowCards;
    }

    public void setTotalYellowCards(Integer totalYellowCards) {
        this.totalYellowCards = totalYellowCards;
    }

    public Integer getTotalRedCards() {
        return totalRedCards;
    }

    public void setTotalRedCards(Integer totalRedCards) {
        this.totalRedCards = totalRedCards;
    }

    @Override
    public String toString() {
        return "TeamStatisticInMatch{" + "totalGoals=" + totalGoals + ", totalPenaltyGoals=" + totalPenaltyGoals + ", totalOwnGoals=" + totalOwnGoals + ", totalFouls=" + totalFouls + ", totalYellowCards=" + totalYellowCards + ", totalRedCards=" + totalRedCards + '}';
    }
    
    
}
