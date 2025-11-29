/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.Match;
import N23DCCN058.fm.model.MatchStatus;
import N23DCCN058.fm.model.MatchTeams;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class MatchDAO {
    
    private Match mapRow(ResultSet rs) throws SQLException {
        Match match = new Match();
        match.setMatchId(rs.getInt("match_id"));
        match.setRefereeId(rs.getInt("referee_id"));
        match.setMatchDate(rs.getDate("match_date"));
        match.setMatchStatus(MatchStatus.fromString(rs.getString("match_status")));
        return match;
    }
    
    public List<Match> getAll(){
        String sql = "Select * from matches";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
               ){
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> getAllFinishedMatches(){
        String sql = "Select * from matches where match_status = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> getAllUnfinishedMatches(){
        String sql = "Select * from matches where match_status = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1, MatchStatus.CHUA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Match getByMatchId(Integer matchId){
        String sql = "Select * from Matches where match_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, matchId);
            ResultSet rs = ps.executeQuery();
            Match match = null;
            if(rs.next()) match = mapRow(rs);
            return match;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> getAllMatchesOfReferee(Integer refereeId){
        String sql = "Select * from Matches where referee_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, refereeId);
            ResultSet rs = ps.executeQuery();
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean add(Match t){
        String sql = "Insert into Matches(referee_id, match_date, match_status) Values (?, ?, ?)";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, t.getRefereeId());
            ps.setDate(2, t.getMatchDate());
            ps.setString(3, t.getMatchStatus().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean updateById(Match t){
        String sql = "Update Matches Set referee_id = ?, match_date = ?, match_status = ? where match_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, t.getRefereeId());
            ps.setDate(2, t.getMatchDate());
            ps.setString(3, t.getMatchStatus().name().toLowerCase());
            ps.setInt(4, t.getMatchId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean deleteById(Integer matchId){
        String sql = "Delete from Matches where match_id = ?";
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

    public boolean deleteAll(){
        String sql = "Delete from Matches";
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
    
    public int countTotalMatchesOfReferee(Integer refereeId){
        String sql = "Select count(*) as total_matches from matches where referee_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, refereeId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_matches") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countTotalFinishedMatchesOfReferee(Integer refereeId){
        String sql = "Select count(*) as total_matches from matches where referee_id = ? and match_status = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, refereeId);
            ps.setString(2, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_matches") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean isFinished(Integer matchId){
        String sql = "Select match_status from matches where match_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("match_status").equalsIgnoreCase(MatchStatus.DA_DIEN_RA.name()) : false;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> searchMatchByDate(Date matchDate){
        String sql = "Select * from matches where match_date = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setDate(1, matchDate);
            ResultSet rs = ps.executeQuery();
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> searchMatchByRefereeId(Integer refereeId){
        String sql = "Select * from matches where referee_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, refereeId);
            ResultSet rs = ps.executeQuery();
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Match> getAllReadyMatch(){
        String sql = """
                     SELECT m.*
                     FROM matches m
                     WHERE m.match_id IN (
                         -- Điều kiện đủ 2 đội: 1 home + 1 away
                         SELECT match_id
                         FROM match_teams
                         GROUP BY match_id
                         HAVING COUNT(DISTINCT team_id) = 2
                            AND SUM(team_role = 'home') = 1
                            AND SUM(team_role = 'away') = 1
                     )
                     AND m.match_id IN (
                         -- Điều kiện đủ 22 cầu thủ (không phân biệt đá chính hay dự bị)
                         SELECT mp.match_id
                         FROM match_players mp
                         JOIN players p ON mp.player_id = p.player_id
                         JOIN match_teams mt ON p.team_id = mt.team_id AND mp.match_id = mt.match_id
                         GROUP BY mp.match_id
                         HAVING COUNT(mp.player_id) = 22
                     );
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
                ){
            List<Match> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean isMatchReady(Integer matchId){
        String sql = "SELECT is_match_ready(?) as isReady;";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1,matchId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getBoolean("isReady") : false;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

}
