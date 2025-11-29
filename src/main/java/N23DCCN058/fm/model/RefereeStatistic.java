/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public class RefereeStatistic {
    private Integer numOfMatchesJoin, numOfFinishedMatches;

    public RefereeStatistic() {
    }

    public RefereeStatistic(Integer numOfMatchesJoin, Integer numOfFinishedMatches) {
        this.numOfMatchesJoin = numOfMatchesJoin;
        this.numOfFinishedMatches = numOfFinishedMatches;
    }

    public Integer getNumOfMatchesJoin() {
        return numOfMatchesJoin;
    }

    public void setNumOfMatchesJoin(Integer numOfMatchesJoin) {
        this.numOfMatchesJoin = numOfMatchesJoin;
    }

    public Integer getNumOfFinishedMatches() {
        return numOfFinishedMatches;
    }

    public void setNumOfFinishedMatches(Integer numOfFinishedMatches) {
        this.numOfFinishedMatches = numOfFinishedMatches;
    }

    @Override
    public String toString() {
        return "RefereeStatistic{" + "numOfMatchesJoin=" + numOfMatchesJoin + ", numOfFinishedMatches=" + numOfFinishedMatches + '}';
    }
    
    
}
