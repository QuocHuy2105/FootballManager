/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.EventType;
import N23DCCN058.fm.model.MatchEvents;
import N23DCCN058.fm.model.MatchStatus;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class MatchEventDAO {
    
    private MatchEvents mapRow(ResultSet rs) throws SQLException {
        MatchEvents me = new MatchEvents();
        me.setEventId(rs.getInt("event_id"));
        me.setMatchId(rs.getInt("match_id"));
        
        Integer playerId = rs.getInt("player_id");
        me.setPlayerId(rs.wasNull() ? null : playerId);
        
        me.setEventType(EventType.fromString(rs.getString("event_type")));
        me.setEventTime(rs.getInt("event_time"));
        return me;
    }

    public List<MatchEvents> getAll(){
        String sql = "Select * from match_events order by event_id";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
                ){
            List<MatchEvents> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean add(MatchEvents me){
        String sql = "Insert into match_events (match_id, player_id, event_type, event_time) value (?, ?, ? ,?)";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, me.getMatchId());
            ps.setObject(2, me.getPlayerId(), Types.INTEGER);
            ps.setString(3, me.getEventType().name());
            ps.setInt(4, me.getEventTime());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteById(Integer eventId){
        String sql = "Delete from match_events where event_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, eventId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from match_events";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAllEventsOfMatch(Integer matchId){
        String sql = "Delete from match_events where match_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean update(MatchEvents me){
        String sql = "Update match_events Set match_id = ?, player_id = ?, event_type = ?, event_time = ? where event_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, me.getMatchId());
            ps.setObject(2, me.getPlayerId(), Types.INTEGER);
            ps.setString(3, me.getEventType().name());
            ps.setInt(4, me.getEventTime());
            ps.setInt(5, me.getEventId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    

    
    public MatchEvents getById(Integer eventId){
        String sql = "Select * from match_events where event_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            MatchEvents me = null;
            if(rs.next()) me = mapRow(rs);
            return me;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchEvents> getAllEventsOfMatch(Integer matchId){
        String sql = "Select * from match_events where match_id = ? order by event_time";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ResultSet rs = ps.executeQuery();
            List<MatchEvents> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    
    }
    
    public List<MatchEvents> getEventsOfPlayerInMatch(Integer matchId, Integer playerId){
        String sql = "Select * from match_events where match_id = ? and player_id = ? order by event_time";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, playerId);
            ResultSet rs = ps.executeQuery();
            List<MatchEvents> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countGoalsOfPlayer(Integer playerId){
        String sql = """
                    Select count(*) as total_goals 
                    from match_events as me
                    join matches as m on me.match_id = m.match_id
                    where me.player_id = ? and me.event_type in (?, ?) and m.match_status = ?
                  """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.GOAL.name());
            ps.setString(3, EventType.PENALTY_GOAL.name());
            ps.setString(4, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_goals") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countPenaltyGoalsOfPlayer(Integer playerId){
        String sql = """
                     Select count(*) as total_goals 
                     from match_events as me
                    join matches as m on me.match_id = m.match_id
                     where me.player_id = ? and me.event_type = ? and m.match_status = ?
                    """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.PENALTY_GOAL.name());
            ps.setString(3, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_goals") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countOwnGoalsOfPlayer(Integer playerId){
        String sql = """
                     Select count(*) as total_own_goals 
                     from match_events as me
                    join matches as m on me.match_id = m.match_id
                     where me.player_id = ? and me.event_type = ? and m.match_status = ?
                    """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.OWN_GOAL.name());
            ps.setString(3, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_own_goals") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countFoulsOfPlayer(Integer playerId){
        String sql = """
                     Select count(*) as total_fouls 
                     from match_events as me
                    join matches as m on me.match_id = m.match_id
                     where me.player_id = ? and me.event_type = ? and m.match_status = ?
                    """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.FOUL.name());
            ps.setString(3, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_fouls") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countYellowCardsOfPlayer(Integer playerId){
        String sql = """
                     Select count(*) as total_yellow_cards 
                     from match_events as me
                    join matches as m on me.match_id = m.match_id
                     where me.player_id = ? and me.event_type = ? and m.match_status = ?
                    """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.YELLOW_CARD.name());
            ps.setString(3, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_yellow_cards") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countRedCardsOfPlayer(Integer playerId){
        String sql = """
                     Select count(*) as total_red_cards 
                     from match_events as me
                    join matches as m on me.match_id = m.match_id
                     where me.player_id = ? and me.event_type = ? and m.match_status = ?
                    """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, EventType.RED_CARD.name());
            ps.setString(3, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_red_cards") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchEvents> getAllEventsOfMatchAndTeam(Integer matchId, Integer teamId){
        String sql = """
                     select mp.*
                     from match_events as mp
                     join players as p on mp.player_id = p.player_id
                     where match_id = ? and team_id = ?
                     Order by mp.event_time
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            ResultSet rs = ps.executeQuery();
            List<MatchEvents> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countGoalsOfTeamInMatch(Integer matchId, Integer teamId){
        String sql = "Select get_team_goals(?, ?) as total";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    private int countEventsByTeamInMatch(Integer matchId, Integer teamId, EventType type){
        String sql = """
                    Select count(*) as total
                    from match_events as me join players as p
                    on me.player_id = p.player_id
                    where match_id = ? and team_id = ? and event_type = ?
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            ps.setString(3, type.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countPenaltyGoalsOfTeamInMatch(Integer matchId, Integer teamId){
        return countEventsByTeamInMatch(matchId, teamId, EventType.PENALTY_GOAL);
    }
    
    public int countOwnGoalsOfTeamInMatch(Integer matchId, Integer teamId){
        return countEventsByTeamInMatch(matchId, teamId, EventType.OWN_GOAL);
    }
    
    public int countFoulsOfTeamInMatch(Integer matchId, Integer teamId){
        return countEventsByTeamInMatch(matchId, teamId, EventType.FOUL);
    }
    
    public int countYellowCardsOfTeamInMatch(Integer matchId, Integer teamId){
        return countEventsByTeamInMatch(matchId, teamId, EventType.YELLOW_CARD);
    }
    
    public int countRedCardsOfTeamInMatch(Integer matchId, Integer teamId){
        return countEventsByTeamInMatch(matchId, teamId, EventType.RED_CARD);
    }
    
}
