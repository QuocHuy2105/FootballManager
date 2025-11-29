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
public class Match {
    private Integer matchId, refereeId;
    private Date matchDate;
    private MatchStatus matchStatus;

    public Match() {
    }

    public Match(Integer refereeId, Date matchDate, MatchStatus matchStatus) {
        this.refereeId = refereeId;
        this.matchDate = matchDate;
        this.matchStatus = matchStatus;
    }
    
    public Match(Integer matchId, Integer refereeId, Date matchDate, MatchStatus matchStatus) {
        this.matchId = matchId;
        this.refereeId = refereeId;
        this.matchDate = matchDate;
        this.matchStatus = matchStatus;
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

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Override
    public String toString() {
        return "Match{" + "match_id=" + matchId + ", referee_id=" + refereeId + ", match_date=" + matchDate + ", match_status=" + matchStatus + '}';
    }
    
    public Object[] toObjectArray(){
        String status = this.matchStatus.equals(MatchStatus.DA_DIEN_RA) ? "Đã diễn ra" : "Chưa diễn ra";
        return new Object[]{this.matchId,this.refereeId , this.matchDate, status};
    }
    
    
}
