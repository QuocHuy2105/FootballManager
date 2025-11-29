/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.MatchPlayerDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.MatchPlayers;
import N23DCCN058.fm.model.PlayerRole;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class MatchPlayerService {
    

    
    public List<Integer> getPlayerIdsOfMatchAndTeam(int matchId, int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(matchId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try{
            MatchPlayerDAO dao = new MatchPlayerDAO();
            return dao.getPlayerIdsByMatchAndTeam(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách cầu thủ của đội bóng tham gia vào trận: " + e.getMessage(), e);
        }
        
    }
    
    public List<MatchPlayers> getAllOfMatch(int matchId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        try{
            MatchPlayerDAO dao = new MatchPlayerDAO();
            return dao.getbyMatchId(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách cầu thủ tham gia vào trận: " + e.getMessage(), e);
        }
        
    }
    
    public List<MatchPlayers> getByMatchAndTeam(int matchId, int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try{
            MatchPlayerDAO dao = new MatchPlayerDAO();
            return dao.getByMatchAndTeam(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách cầu thủ của đội bóng tham gia vào trận: " + e.getMessage(), e);
        }
     
    }
    
    public boolean add(MatchPlayers matchPlayers){
        if(matchPlayers.getMatchId() <= 0)
             throw new ValidationException("Id trận đấu không hợp lệ!");
        if(matchPlayers.getPlayerId() <= 0)
             throw new ValidationException("Id cầu thủ không hợp lệ!");
        if(matchPlayers.getPlayerRole() == null)
             throw new ValidationException("Vai trò cầu thủ không hợp lệ!");
        try{
            MatchPlayerDAO dao = new MatchPlayerDAO();
            return dao.add(matchPlayers);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm cầu thủ tham gia vào trận: " + e.getMessage(), e);
        }
        
    }
    
    public void delete(List<MatchPlayers> list){
        if(list == null)
            throw new ValidationException("Danh sách không hợp lệ!");
        try{
            MatchPlayerDAO dao = new MatchPlayerDAO();
            for(MatchPlayers mp : list) dao.deleteById(mp.getMatchId(), mp.getPlayerId());
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm cầu thủ tham gia vào trận: " + e.getMessage(), e);
        }
        
    }
    
}
