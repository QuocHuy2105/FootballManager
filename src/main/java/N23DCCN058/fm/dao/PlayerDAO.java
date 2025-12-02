/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.PlayerPosition;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author WokWee
 */
public class PlayerDAO{
    
    private Player mapRow(ResultSet rs) throws SQLException{ 
        if(rs == null) return null;
        Player myPlayer = new Player();
        myPlayer.setPlayerId(rs.getInt("player_id"));
        myPlayer.setPlayerName(rs.getString("player_name"));
        myPlayer.setDob(rs.getDate("dob"));
        myPlayer.setJerseyNumber(rs.getInt("jersey_number"));
        myPlayer.setPosition(PlayerPosition.fromString(rs.getString("position")));
        myPlayer.setTeamId(rs.getInt("team_id"));
        return myPlayer;
    }
    
    public List<Player> getAll(){
        String sql = "Select * From Players where is_active = 1 Order by team_id";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
               ){
            List<Player> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Set<String> getAllPlayerName(){
        String sql = "Select player_name From Players where is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
               ){
            Set<String> set = new HashSet<>();
            while(rs.next()) set.add(rs.getString("player_name"));
            return set;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    
    public Player getById(Integer playerId){
        String sql = "Select * From Players Where player_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);   
                ){
            ps.setInt(1, playerId);
            ResultSet rs = ps.executeQuery();
            Player myPlayer = null;
            if(rs.next()) myPlayer = mapRow(rs);
            return myPlayer;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }   
    }
    
    public List<Player> getAllByTeamId(Integer teamId){
        String sql = "Select * From Players where team_id = ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            List<Player> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }  
        
    }
    
    public boolean add(Player player){
        String sql = "Insert into PLayers (player_name, dob, jersey_number, position, team_id) Value (?, ?, ?, ?, ?)";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setString(1, player.getPlayerName());
            ps.setObject(2, player.getDob(), Types.DATE);
            ps.setInt(3, player.getJerseyNumber());
            ps.setString(4, player.getPosition().name());
            ps.setInt(5, player.getTeamId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public void add(List<Player> players){
        String sql = "Insert into PLayers (player_name, dob, jersey_number, position, team_id) Value (?, ?, ?, ?, ?)";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            for(Player player : players){
                ps.setString(1, player.getPlayerName());
                ps.setObject(2, player.getDob(), Types.DATE);
                ps.setInt(3, player.getJerseyNumber());
                ps.setString(4, player.getPosition().name());
                ps.setInt(5, player.getTeamId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean updateById(Player player){
        String sql = "Update Players Set player_name = ?,  dob = ?,  jersey_number = ?, position = ?, team_id = ? where player_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql); 
                ){
            ps.setString(1, player.getPlayerName());
            ps.setObject(2, player.getDob(), Types.DATE);
            ps.setInt(3, player.getJerseyNumber());
            ps.setString(4, player.getPosition().name());
            ps.setInt(5, player.getTeamId());
            ps.setInt(6, player.getPlayerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteById(Integer playerId){
        String sql = "Update Players Set is_active = 0 where player_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setInt(1, playerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from players";
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
    
    public List<Player> searchByNameContainKeyword(String playerName){
        String sql = "Select * From Players Where player_name like ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setString(1,"%" + playerName + "%");
            ResultSet rs = ps.executeQuery();
            List<Player> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Player> searchPlayer(Player player){
        StringBuilder sql = new StringBuilder("Select * from players where is_active = 1 ");
        List<Object> params = new ArrayList<>();
        
        if(player.getPlayerId() != null){
            sql.append("and player_id = ? ");
            params.add(player.getPlayerId());
        }
        
        if(player.getPlayerName() != null && !player.getPlayerName().trim().isEmpty()){
            sql.append("and player_name like ? ");
            params.add("%" + player.getPlayerName() + "%");
        }
        
        if(player.getDob() != null){
            sql.append("and dob = ? ");
            params.add(player.getDob());
        }
        
        if(player.getJerseyNumber() != null){
            sql.append("and jersey_number = ? ");
            params.add(player.getJerseyNumber());
        }
        
        if(player.getPosition() != null){
            sql.append("and position = ? ");
            params.add(player.getPosition().name());
        }
        
        if(player.getTeamId() != null){
            sql.append("and team_id = ? ");
            params.add(player.getTeamId());
        }
        
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());    
                ){
            for(int i = 0; i < params.size(); i++)
                    ps.setObject(i + 1, params.get(i));
            ResultSet rs = ps.executeQuery();
            List<Player> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public int countTotalPlayersOfTeam(Integer teamId){
        String sql = "Select count(*) as total_players from players where team_id = ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("total_players") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Map<Integer, Integer> getTotalGoalsOfAllPlayer(){
        String sql = """
                    Select m.player_id, count(event_type)as total_goals
                    From players as m 
                    Left join match_events as me
                    on m.player_id = me.player_id and event_type in ('goal', 'penalty_goal')
                    Where is_active = 1
                    Group by m.player_id
                    """;    
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);  
            ResultSet rs = ps.executeQuery();
                ){
            Map<Integer, Integer> map = new HashMap<>();
            while(rs.next()) map.put(rs.getInt("player_id"), rs.getInt("total_goals"));
            return map;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean existsJerseyNumberOfTeam(Integer teamId, Integer jerseyNumber){
        String sql = """
                     Select count(*)
                     From players
                     where team_id = ? and jersey_number = ? and is_active = 1
                     """;
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            ps.setInt(2, jerseyNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) > 0 : false;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
 
}
