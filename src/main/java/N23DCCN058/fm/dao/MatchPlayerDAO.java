/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.MatchPlayers;
import N23DCCN058.fm.model.PlayerPosition;
import N23DCCN058.fm.model.PlayerRole;
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
public class MatchPlayerDAO {
    
    private MatchPlayers mapRow(ResultSet rs) throws SQLException {
        MatchPlayers mp = new MatchPlayers();
        mp.setMatchId(rs.getInt("match_id"));
        mp.setPlayerId(rs.getInt("player_id"));
        mp.setPlayerRole(PlayerRole.fromString(rs.getString("player_role")));
        mp.setPosition(PlayerPosition.fromString(rs.getString("position_in_match")));
        return mp;
    }
    
    public boolean add(MatchPlayers mp){
        String sql = "Insert into match_players (match_id, player_id, player_role, position_in_match) Values (?, ?, ?, ?)";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, mp.getMatchId());
            ps.setInt(2, mp.getPlayerId());
            ps.setString(3, mp.getPlayerRole().name());
            ps.setString(4, mp.getPosition().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteById(Integer matchId, Integer playerId){
        String sql = "Delete from match_players where match_id = ? and player_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, playerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from match_players";
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
    
    public List<MatchPlayers> getbyMatchId(Integer matchId){
        String sql = "Select * from match_players where match_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            List<MatchPlayers> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchPlayers> getByPlayerId(Integer playerId){
        String sql = "Select * from match_players where player_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            List<MatchPlayers> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countTotalPlayedMatches(Integer playerId){
        String sql = "Select count(*) as total_matches from match_players where player_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_matches") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countTotalStartingPlayedMatches(Integer playerId){
        String sql = "Select count(*) as total_matches from match_players where player_id = ? and player_role = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ps.setString(2, PlayerRole.STARTING.name());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_matches") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Integer> getAllMatchIdsOfPlayer(Integer playerId){
        String sql = "Select match_id from match_players where player_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, playerId);
            ResultSet rs = ps.executeQuery();
            List<Integer> list = new ArrayList<>();
            while(rs.next()) list.add(rs.getInt("match_id"));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public PlayerRole getPlayerRoleInMatch(Integer matchId, Integer playerId){
        String sql = "Select player_role from match_players where match_id = ? and player_id = ?";
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, playerId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? PlayerRole.fromString(rs.getString("player_role")): null;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Integer> getPlayerIdsByMatchAndTeam(Integer matchId, Integer teamId){
        String sql = """
                     select mp.player_id
                     from match_players as mp
                     join players as p on mp.player_id = p.player_id
                     where match_id = ? and team_id = ?
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            ResultSet rs = ps.executeQuery();
            List<Integer> list = new ArrayList<>();
            while(rs.next()) list.add(rs.getInt("player_id"));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<MatchPlayers> getByMatchAndTeam(Integer matchId, Integer teamId){
        String sql = """
                     select mp.match_id, mp.player_id, mp.player_role, mp.position_in_match
                     from match_players as mp
                     join players as p on mp.player_id = p.player_id
                     where match_id = ? and team_id = ?
                     Order by mp.player_role, mp.position_in_match
                     """;
        try (
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, matchId);
            ps.setInt(2, teamId);
            ResultSet rs = ps.executeQuery();
            List<MatchPlayers> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

}
