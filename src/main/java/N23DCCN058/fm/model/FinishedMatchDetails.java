/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

import java.sql.Date;

/**
 *
 * @author WokWee
 */
public class FinishedMatchDetails {
    private Integer matchId;
    private Integer refereeId;
    private Integer homeTeam;
    private Integer awayTeam;
    private Integer homeGoals;
    private Integer awayGoals;
    private Date matchDate;

    public FinishedMatchDetails() {
    }

    public FinishedMatchDetails(Integer matchId, Integer refereeId, Integer homeTeam, Integer awayTeam, Integer homeGoals, Integer awayGoals, Date matchDate) {
        this.matchId = matchId;
        this.refereeId = refereeId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.matchDate = matchDate;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Integer homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Integer awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "FinishedMatchDetail{" + "matchId=" + matchId + ", refereeId=" + refereeId + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeGoals=" + homeGoals + ", awayGoals=" + awayGoals + ", matchDate=" + matchDate + '}';
    }
    
    
}
