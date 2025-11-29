/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author WokWee
 */
public class TeamDAO{
    
    private Team mapRow(ResultSet rs) throws SQLException{  
        Team myTeam = new Team();
        myTeam.setTeamId(rs.getInt("team_id"));
        myTeam.setTeamName(rs.getString("team_name"));
        myTeam.setDepartment(rs.getString("department"));
        myTeam.setCoachName(rs.getString("coach_name"));
        return myTeam;
    }
    
    public List<Team> getAll(){
        String sql = "Select * from Teams where is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
                ){
            List<Team> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Set<String> getAllTeamName(){
        String sql = "Select team_name from Teams where is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
                ){
            Set<String> teamName = new HashSet<>();
            while(rs.next()) teamName.add(rs.getString("team_name"));
            return teamName;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    
    public Team getById(Integer teamId){
        String sql = "Select * From Teams Where team_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            Team myTeam = null;
            if(rs.next()) myTeam = mapRow(rs);
            return myTeam;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public String getTeamNameById(Integer teamId){
        String sql = "Select team_name From Teams Where team_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("team_name") : null;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
           
    public boolean add(Team team){
        String sql = "Insert into Teams (team_name, department, coach_name) Values (?, ?, ?)";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getDepartment());
            ps.setString(3, team.getCoachName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public void add(List<Team> list){
        String sql = "Insert into Teams (team_name, department, coach_name) Values (?, ?, ?);";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            for(Team team : list){
                ps.setString(1, team.getTeamName());
                ps.setString(2, team.getDepartment());
                ps.setString(3, team.getCoachName());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean updateById(Team team){
        String sql = "Update Teams Set team_name = ?, department = ?, coach_name = ? Where team_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getDepartment());
            ps.setString(3, team.getCoachName());
            ps.setInt(4, team.getTeamId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteById(Integer teamId){
        String sql = "Update Teams Set is_active = 0 where team_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setInt(1, teamId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from Teams";
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
    
    public List<Team> searchByNameContainKeyword(String keyword){
        String sql = "Select * From Teams Where team_name like ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            List<Team> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Team> searchTeam(Team t){
        StringBuilder sql = new StringBuilder("Select * From Teams Where is_active = 1 ");
        List<Object> params = new ArrayList<>();
        
        if(t.getTeamId() != null){
            sql.append("and team_id = ? ");
            params.add(t.getTeamId());
        }
        
        if(t.getTeamName() != null && !t.getTeamName().trim().isEmpty()){
            sql.append("and team_name like ? ");
            params.add("%" + t.getTeamName() + "%");
        }
        
        if(t.getDepartment() != null && !t.getDepartment().trim().isEmpty()){
            sql.append("and department like ? ");
            params.add("%" + t.getDepartment() + "%");
        }
        
        if(t.getCoachName() != null && !t.getCoachName().trim().isEmpty()){
            sql.append("and coach_name like ?");
            params.add("%" + t.getCoachName() + "%");
        }
        
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
                ){
            for(int i = 0; i < params.size(); i++)
                ps.setObject(i+1, params.get(i));
            ResultSet rs = ps.executeQuery();
            List<Team> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    

}
