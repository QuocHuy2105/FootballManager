
package N23DCCN058.fm.model;

import java.sql.Date;


public class Player {
    private Integer playerId;
    private String playerName;
    private Date dob;
    private Integer jerseyNumber; 
    private PlayerPosition position;
    private Integer teamId;

    public Player() {
    }

    public Player(String playerName, Date dob, Integer jerseyNumber, PlayerPosition position, Integer teamId) {
        this.playerName = playerName;
        this.dob = dob;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.teamId = teamId;
    }

    public Player(Integer playerId, String playerName, Date dob, Integer jerseyNumber, PlayerPosition position, Integer teamId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.dob = dob;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.teamId = teamId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Player{" + "player_id=" + playerId + ", player_name=" + playerName + ", dob=" + dob + ", jersey_number=" + jerseyNumber + ", position=" + position + ", team_id=" + teamId + '}';
    }
    
    public Object[] toObjectArray(){
        return new Object[]{this.playerId, this.playerName, this.dob, this.jerseyNumber, this.position, this.teamId};
    }
    
}
