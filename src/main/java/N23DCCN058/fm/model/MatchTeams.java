
package N23DCCN058.fm.model;


public class MatchTeams {
    private Integer matchId, teamId;
    private TeamRole teamRole;

    public MatchTeams() {
    }

    public MatchTeams(Integer matchId, Integer teamId, TeamRole teamRole) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.teamRole = teamRole;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public TeamRole getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    @Override
    public String toString() {
        return "MatchTeams{" + "match_id=" + matchId + ", team_id=" + teamId + ", team_role=" + teamRole + '}';
    }
    
    
}
