/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.MatchTeamDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.MatchTeams;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.model.TeamRole;
import java.util.List;

/**
 *
 * @author WokWee
 */
public class MatchTeamService {
    

    
    public List<MatchTeams> getTeamOfMatch(int matchId){
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchTeamDAO dao = new MatchTeamDAO();
            return dao.getTeamOfMatch(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy đội bóng tham gia trận đấu: " + e.getMessage(), e);
        }
        
    }
    
    public boolean addMatchTeam(MatchTeams matchTeam){
        if(matchTeam.getMatchId() <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(matchTeam.getTeamId() <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        if(matchTeam.getTeamRole() == null)
            throw new ValidationException("Vai trò đội bóng không hợp lệ!");
        
        try {
            MatchTeamDAO dao = new MatchTeamDAO();
            return dao.add(matchTeam);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm đội bóng tham gia trận đấu: " + e.getMessage(), e);
        }
        
    }
    
    public boolean updateMatchTeam(MatchTeams matchTeam){
        if(matchTeam.getMatchId() <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(matchTeam.getTeamId() <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        if(matchTeam.getTeamRole() == null)
            throw new ValidationException("Vai trò đội bóng không hợp lệ!");
        
        try {
            MatchTeamDAO dao = new MatchTeamDAO();
            return dao.update(matchTeam);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập đội bóng tham gia trận đấu: " + e.getMessage(), e);
        }
        
    }
    
    public boolean deleteById(int matchId, int teamId){
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            MatchTeamDAO dao = new MatchTeamDAO();
            return dao.deleteById(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa đội bóng tham gia trận đấu: " + e.getMessage(), e);
        }
    }
    
}
