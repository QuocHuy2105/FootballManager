/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.model.FinishedMatchDetails;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class FinishedMatchDetailsDAO {
    
    private FinishedMatchDetails mapRow(ResultSet rs) throws SQLException{
        FinishedMatchDetails fmd = new FinishedMatchDetails();
        fmd.setMatchId(rs.getInt("match_id"));
        fmd.setRefereeId(rs.getInt("referee_id"));
        fmd.setHomeTeam(rs.getInt("home_team"));
        fmd.setAwayTeam(rs.getInt("away_team"));
        fmd.setHomeGoals(rs.getInt("home_goals"));
        fmd.setAwayGoals(rs.getInt("away_goals"));
        fmd.setMatchDate(rs.getDate("match_date"));
        return fmd;
    }
    
    public List<FinishedMatchDetails> getAll(){
        String sql = "{Call get_all_finished_match_details()}";
        try(
            Connection conn = DBConnectionPool.getConnection();
            CallableStatement ps = conn.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
                ){
            List<FinishedMatchDetails> list = new ArrayList<>();
            while(rs.next())
                list.add(mapRow(rs));
            return list;
        } catch(SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public FinishedMatchDetails getDetailsOfMatch(Integer matchId){
        String sql = "{Call get_details_of_match(?)}";
        try(
            Connection conn = DBConnectionPool.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
                ){
            cs.setInt(1, matchId);
            ResultSet rs = cs.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }  
    }

    public int countTotalGoalsOfTeam(Integer teamId){
        String sql = "{Call get_team_total_goals(?)}";
        try(
            Connection conn = DBConnectionPool.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
                ){
            cs.setInt(1, teamId);
            ResultSet rs = cs.executeQuery();
            return rs.next() ? rs.getInt("total_goals") : 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }    
    }
    
}
