/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public class MatchEvents {
    private Integer eventId, matchId, playerId;
    private EventType eventType;
    private Integer eventTime;

    public MatchEvents() {
    }

    public MatchEvents(Integer matchId, Integer playerId, EventType eventType, Integer eventTime) {
        this.matchId = matchId;
        this.playerId = playerId;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public MatchEvents(Integer eventId, Integer matchId, Integer playerId, EventType eventType, Integer eventTime) {
        this.eventId = eventId;
        this.matchId = matchId;
        this.playerId = playerId;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Integer getEventTime() {
        return eventTime;
    }

    public void setEventTime(Integer eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "MatchEvents{" + "event_id=" + eventId + ", match_id=" + matchId + ", player_id=" + playerId + ", event_type=" + eventType + ", event_time=" + eventTime + '}';
    }
    
    
}
