/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.MatchStatus;
import N23DCCN058.fm.model.MatchTeams;
import N23DCCN058.fm.model.TeamRole;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class MatchTeamDAO {
    
    private MatchTeams mapRow(ResultSet rs) throws SQLException {
        MatchTeams mt = new MatchTeams();
        mt.setMatchId(rs.getInt("match_id"));
        mt.setTeamId(rs.getInt("team_id"));
        mt.setTeamRole(TeamRole.fromString(rs.getString("team_role")));
        return mt;
    }
    
    public boolean add(MatchTeams mt){
        String sql = "Insert into match_teams (match_id, team_id, team_role) Values (?, ?, ?)";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, mt.getMatchId());
            ps.setInt(2, mt.getTeamId());
            ps.setString(3, mt.getTeamRole().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteById(Integer matchId, Integer teamId){
        String sql = "Delete from match_teams where match_id = ? and team_id = ?" ;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from match_teams";
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
    
    public List<MatchTeams> getByMatchId(Integer matchId){
        String sql = "Select * from match_teams where match_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ResultSet rs = ps.executeQuery();
            List<MatchTeams> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchTeams> getByTeamId(Integer teamId){
        String sql = "Select * from match_teams where team_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            List<MatchTeams> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countTotalPlayedMatchedOfTeam(Integer teamId){
        String sql = """
                     Select count(*) as total_match 
                     from match_teams as mt
                     join matches as m on mt.match_id = m.match_id
                     where mt.team_id = ? and m.match_status = ?
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            ps.setString(2, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_match") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchTeams> getTeamOfMatch(Integer matchId){
        String sql = "Select * from match_teams where match_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ResultSet rs = ps.executeQuery();
            List<MatchTeams> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean update(MatchTeams matchTeam){
        String sql = "Update match_teams Set team_id = ? where match_id = ? and team_role = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setInt(1, matchTeam.getTeamId());
            ps.setInt(2, matchTeam.getMatchId());
            ps.setString(3, matchTeam.getTeamRole().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    

}
