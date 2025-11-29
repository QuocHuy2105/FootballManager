/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.util.DBErrorTranslator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author WokWee
 */
public class ConfigDAO {
    
    private static final String CONFIG_KEY_TOURNAMENT_EXISTS = "tournament_exists";
    private static final String CONFIG_KEY_TOURNAMENT_NAME = "tournament_name";
    
    public boolean getTournamentExists(){
        String sql = "Select config_value From system_config where config_key = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, CONFIG_KEY_TOURNAMENT_EXISTS);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? "true".equalsIgnoreCase(rs.getString("config_value")) : false;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public boolean setTournamentExists(String value){
        String sql = """
                    INSERT INTO system_config (config_key, config_value)
                    VALUES (?, ?)
                    ON DUPLICATE KEY UPDATE config_value = VALUES(config_value)
                     """;  
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setString(1, CONFIG_KEY_TOURNAMENT_EXISTS);
            ps.setString(2, value);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    public String getTournamentName(){
        String sql = "Select config_value From system_config where config_key = ?";
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ){
            ps.setString(1, CONFIG_KEY_TOURNAMENT_NAME);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("config_value") : null;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }
    
    
    public boolean setTournamentName(String name){
        String sql = """
                    INSERT INTO system_config (config_key, config_value)
                    VALUES (?, ?)
                    ON DUPLICATE KEY UPDATE config_value = VALUES(config_value)
                     """; 
        try(
            Connection conn = DBConnectionPool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);    
                ){
            ps.setString(1, CONFIG_KEY_TOURNAMENT_NAME);
            if(name != null && !name.isEmpty())
                ps.setString(2, name);
            else 
                ps.setNull(2, Types.VARCHAR);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            String msg = DBErrorTranslator.translate(e);
            throw new DatabaseException(msg, e);
        }
    }

}
