/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.MatchStatus;
import N23DCCN058.fm.model.Referee;
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
public class RefereeDAO {

    public Referee mapRow(ResultSet rs) throws SQLException{
        Referee myReferee = new Referee();
        myReferee.setRefereeId(rs.getInt("referee_id"));
        myReferee.setRefereeName(rs.getString("referee_name"));
        myReferee.setPhoneNumber(rs.getString("phone_number"));
        return myReferee;
        
    }

    public List<Referee> getAll(){
        String sql = "Select * From Referees where is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
               ){
            List<Referee> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Set<String> getAllPhoneNumber(){
        String sql = "Select phone_number From Referees where is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
               ){
            Set<String> set = new HashSet<>();
            while(rs.next()) set.add(rs.getString("phone_number"));
            return set;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public Referee getById(Integer id){
        String sql = "Select * From Referees Where referee_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();     
            Referee myReferee = null;        
            if(rs.next()) myReferee = mapRow(rs);
            return myReferee;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean add(Referee r){
        String sql = "Insert into Referees (referee_name, phone_number) Values (?, ?);";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1, r.getRefereeName());
            ps.setObject(2, r.getPhoneNumber(), Types.VARCHAR);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public void add(List<Referee> referees){
        String sql = "Insert into Referees (referee_name, phone_number) Values (?, ?);";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            for(Referee r : referees){
                ps.setString(1, r.getRefereeName());
                ps.setObject(2, r.getPhoneNumber(), Types.VARCHAR);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean updateById(Referee r){
        String sql = "Update Referees Set referee_name = ?, phone_number = ? where referee_id = ? ";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1, r.getRefereeName());
            ps.setObject(2, r.getPhoneNumber(), Types.VARCHAR);
            ps.setInt(3, r.getRefereeId());     
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

    public boolean deleteById(Integer refereeỈd){
        String sql = "Update Referees Set is_active = 0 where referee_id = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setInt(1, refereeỈd);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean deleteAll(){
        String sql = "Delete from Referees";
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

    public List<Referee> searchByNameContainKeyword(String name){
        String sql = "Select * From Referees Where referee_name like ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();     
            List<Referee> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Referee searchByPhoneNumber(String phoneNumber){
        String sql = "Select * From Referees Where phone_number = ? and is_active = 1";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
               ){
            ps.setString(1,phoneNumber);
            ResultSet rs = ps.executeQuery();     
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public List<Referee> searchReferee(Referee referee){
        StringBuilder sql = new StringBuilder("Select * from referees where is_active = 1 ");
        List<Object> params = new ArrayList<>();
        
        if(referee.getRefereeId() != null){
            sql.append("and referee_id = ? ");
            params.add(referee.getRefereeId());
        }
        
        if(referee.getRefereeName() != null && !referee.getRefereeName().trim().isEmpty()){
            sql.append("and referee_name like ? ");
            params.add("%" + referee.getRefereeName() + "%");
        }
        
        if(referee.getPhoneNumber() != null && !referee.getPhoneNumber().trim().isEmpty()){
            sql.append("and phone_number = ? ");
            params.add(referee.getPhoneNumber());
        }
        
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
               ){
            for(int i = 0; i < params.size(); i++)
                    ps.setObject(i + 1, params.get(i));
            ResultSet rs = ps.executeQuery();     
            List<Referee> list = new ArrayList<>();
            while(rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public Map<Integer, Integer> getTotalFinishedMatchOfAllReferee(){
        String sql = """
                     Select r.referee_id, count(match_id) as total_matches
                     From referees as r 
                     Left join matches as m
                     on r.referee_id = m.referee_id and m.match_status = ?
                     where is_active = 1
                     Group by r.referee_id
                     """;
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
               ){
            ps.setString(1, MatchStatus.DA_DIEN_RA.name());
            ResultSet rs = ps.executeQuery();     
            Map<Integer, Integer> map = new HashMap<>();
            while(rs.next()) map.put(rs.getInt("referee_id"), rs.getInt("total_matches"));
            return map;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
}
