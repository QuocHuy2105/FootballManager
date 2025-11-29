/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.ConfigDAO;
import N23DCCN058.fm.dao.MatchDAO;
import N23DCCN058.fm.dao.MatchEventDAO;
import N23DCCN058.fm.dao.MatchPlayerDAO;
import N23DCCN058.fm.dao.MatchTeamDAO;
import N23DCCN058.fm.dao.PlayerDAO;
import N23DCCN058.fm.dao.RefereeDAO;
import N23DCCN058.fm.dao.TeamDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.ErrorDetail;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.PlayerPosition;
import N23DCCN058.fm.model.Referee;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.NameValidationChecking;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class TournamentService {
    
    
    //Lưu trạng thái đã có giải đấu nào hay chưa
    private static boolean cachedStatus;
    //Đánh dấu đã load lần đầu hay chưa
    private static boolean cachedInitialized;
    
    public boolean hasTournament(){
        ConfigDAO cf = new ConfigDAO();
        try{
            if(!cachedInitialized){
                cachedStatus = cf.getTournamentExists();
                cachedInitialized = true;
            }      
            return cachedStatus;
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy trạng thái giải đấu: " + e.getMessage(),e);
        }
    }
    
    public boolean markTournamentCreated(){
        ConfigDAO cf = new ConfigDAO();
        try{
            cachedStatus = true;
            cachedInitialized = true;
            return cf.setTournamentExists("true");
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập trạng thái giải đấu: " + e.getMessage(), e);
        }
    }
    
    public boolean resetTournamentStatus(){
        ConfigDAO cf = new ConfigDAO();
        try{
            cachedStatus = false;
            cachedInitialized = true;
            return cf.setTournamentExists("false");
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập trạng thái giải đấu: " + e.getMessage(), e);
        }
    }
    
    public void reloadCache(){
        ConfigDAO cf = new ConfigDAO();
        try{
            cachedStatus = cf.getTournamentExists();
            cachedInitialized = true;    
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy trạng thái giải đấu: " + e.getMessage(),e);
        }
    }
    
    public String getTournamentName(){
        ConfigDAO cf = new ConfigDAO();
        try{
            return cf.getTournamentName();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy tên giải đấu: " + e.getMessage(),e);
        }
    }
    
    public boolean setTournamentName(String tournamentName){
        
        try {
            NameValidationChecking.check(tournamentName, "Tên giải đấu");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        try{
            ConfigDAO cf = new ConfigDAO();
            return cf.setTournamentName(tournamentName);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể đặt tên giải đấu: " + e.getMessage(),e);
        }
    }

    private void deleteAllTournamentData(){
        TeamDAO tDAO = new TeamDAO();
        PlayerDAO pDAO = new PlayerDAO();
        RefereeDAO rDAO = new RefereeDAO();
        MatchDAO mDAO = new MatchDAO();
        MatchTeamDAO mtDAO = new MatchTeamDAO();
        MatchPlayerDAO mpDAO = new MatchPlayerDAO();
        MatchEventDAO meDAO = new MatchEventDAO();
        
        try{
            mtDAO.deleteAll();
            mpDAO.deleteAll();
            meDAO.deleteAll();
            pDAO.deleteAll();
            mDAO.deleteAll();
            rDAO.deleteAll();
            tDAO.deleteAll();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể xóa dữ liệu giải đấu: " + e.getMessage(),e);
        }
    }
    
    public void resetTournamentData(){
        try{
            deleteAllTournamentData();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể reset dữ liệu giải đấu: " + e.getMessage(),e);
        }
    }
    
    public void createNewTournament(String tournamentName){
        try {
            NameValidationChecking.check(tournamentName, "Tên giải đấu");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }

        try{
            deleteAllTournamentData();
            setTournamentName(tournamentName);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể tạo mới giải đấu: " + e.getMessage(),e);
        }
    }
    
    public void deleteTournament(){
        try{
            deleteAllTournamentData();
            resetTournamentStatus();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể xóa giải đấu: " + e.getMessage(),e);
        }
    }
    
    public List<Team> readTeamsFromExcel(String filePath) {
        
        List<Team> teams = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // đọc sheet đầu tiên

            // Bỏ dòng tiêu đề (row index = 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Team team = new Team();

                // Cột 0: team_name
                Cell cell0 = row.getCell(0);
                if (cell0 != null) {
                    team.setTeamName(cell0.getStringCellValue().trim());
                }

                // Cột 1: department
                Cell cell1 = row.getCell(1);
                if (cell1 != null) {
                    team.setDepartment(cell1.getStringCellValue().trim());
                }

                // Cột 2: coach_name
                Cell cell2 = row.getCell(2);
                if (cell2 != null) {
                    team.setCoachName(cell2.getStringCellValue().trim());
                }

                teams.add(team);
            }

        } catch (IOException e) {
            throw new ServiceException("Không thể đọc danh sách đội bóng từ file Excel : " + e.getMessage(), e);
        }

        return teams;
    }
    
    public List<Player> readPlayersFromExcel(String filePath) {
        List<Player> players = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Bắt đầu từ dòng 1 (bỏ dòng tiêu đề)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue; // dòng trống

                Player p = new Player();

                // ==== 1. player_name ====
                Cell c1 = row.getCell(0);
                if(c1 != null){
                    p.setPlayerName(c1.getStringCellValue().trim());
                }

                // ==== 2. number ====
                Cell c2 = row.getCell(1);
                if(c2 != null){
                    p.setDob(new Date(c2.getDateCellValue().getTime()));
                }

                // ==== 3. position ====
                Cell c3 = row.getCell(2);
                if(c3 != null){
                    p.setJerseyNumber((int) c3.getNumericCellValue());
                }

                // ==== 4. birth_date ====
                Cell c4 = row.getCell(3);
                if(c4 != null){
                    p.setPosition(PlayerPosition.fromString(c4.getStringCellValue().trim()));
                }

                // ==== 5. team_id ====
                TeamService tService = new TeamService();
                Map<String, Integer> teamName = tService.getAllTeam().stream().collect(Collectors.toMap(Team -> Team.getTeamName(), Team -> Team.getTeamId()));
                
                Cell c5 = row.getCell(4);
                if(c5 != null){
                    p.setTeamId(teamName.get(c5.getStringCellValue()));
                }

                players.add(p);
            }

        } catch (IOException e) {
            throw new ServiceException("Không thể đọc danh sách đội bóng từ file Excel : " + e.getMessage(), e);
        }

        return players;
    }
    
    public List<Referee> readRefereesFromExcel(String filePath) {
        List<Referee> referees = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Bắt đầu từ dòng 1 (bỏ dòng tiêu đề)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue; // dòng trống

                Referee r = new Referee();

                // ==== 1. player_name ====
                Cell c1 = row.getCell(0);
                if(c1 != null){
                    r.setRefereeName(c1.getStringCellValue().trim());
                }
                Cell c2 = row.getCell(1);
                if(c1 != null){
                    r.setPhoneNumber(c2.getStringCellValue().trim());
                }
                
                referees.add(r);
            }

        } catch (IOException e) {
            throw new ServiceException("Không thể đọc danh sách đội bóng từ file Excel : " + e.getMessage(), e);
        }

        return referees;
    }
    


}
