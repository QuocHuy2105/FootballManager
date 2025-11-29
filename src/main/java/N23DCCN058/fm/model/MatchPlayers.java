
package N23DCCN058.fm.model;


public class MatchPlayers {
    private Integer matchId, playerId;
    private PlayerRole playerRole;
    private PlayerPosition position;

    public MatchPlayers() {
    }

    public MatchPlayers(Integer matchId, Integer playerId, PlayerRole playerRole, PlayerPosition position) {
        this.matchId = matchId;
        this.playerId = playerId;
        this.playerRole = playerRole;
        this.position = position;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }
            
    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    @Override
    public String toString() {
        return "MatchPlayers{" + "match_id=" + matchId + ", player_id=" + playerId + ", player_role=" + playerRole + '}';
    }
    
    
}
