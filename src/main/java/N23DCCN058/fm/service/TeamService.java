/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.DBConnectionPool;
import N23DCCN058.fm.dao.PlayerDAO;
import N23DCCN058.fm.dao.TeamDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.ErrorDetail;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.NameValidationChecking;
import N23DCCN058.fm.util.TeamNormalizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class TeamService {
    
    public List<Team> getAllTeam(){
        try {
            TeamDAO dao = new TeamDAO();
            return dao.getAll();
        }
        catch (DatabaseException de){
            throw new ServiceException("Không thể lấy danh sách đội: " + de.getMessage(), de);
        }
    }
    
    public Set<String> getAllTeamName(){
        try {
            TeamDAO dao = new TeamDAO();
            return dao.getAllTeamName();
        }
        catch (DatabaseException de){
            throw new ServiceException("Không thể lấy danh sách tên đội: " + de.getMessage(), de);
        }
    }
    
    public Team getTeamById(int teamId){
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            TeamDAO dao = new TeamDAO();
            return dao.getById(teamId);
        }catch(DatabaseException e){
            throw new ServiceException("Không thể lấy đội bóng: " + e.getMessage(), e);
        }
    }
    
    public boolean addTeam(Team t){
        if(t == null) 
            throw new ValidationException("Đội bóng không hợp lệ!");

        try {
            NameValidationChecking.check(t.getTeamName(), "Tên đội bóng");
            NameValidationChecking.check(t.getDepartment(), "Khoa");
            NameValidationChecking.check(t.getCoachName(), "Tên huấn luyện viên");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        t = TeamNormalizer.normalize(t);
        
        Set<String> allTeamName = getAllTeamName();
        
        if(allTeamName.contains(t.getTeamName()))
            throw new ValidationException("Tên đội bóng đã tồn tại!");
        
        try{
            TeamDAO dao = new TeamDAO();
            return dao.add(t);
        }
        catch(DatabaseException e){
            throw new ServiceException("Không thể thêm đội bóng: " + e.getMessage(), e);
        }
    }
    
    public boolean updateTeam(Team t){
        if(t == null) 
            throw new ValidationException("Đội bóng không hợp lệ!");
        
        try {
            NameValidationChecking.check(t.getTeamName(), "Tên đội bóng");
            NameValidationChecking.check(t.getDepartment(), "Khoa");
            NameValidationChecking.check(t.getCoachName(), "Tên huấn luyện viên");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        t = TeamNormalizer.normalize(t);
        
        Set<String> allTeamName = getAllTeamName();
        
        Team team = getTeamById(t.getTeamId());
        if(!team.getTeamName().equalsIgnoreCase(t.getTeamName()) && allTeamName.contains(t.getTeamName()))
            throw new ValidationException("Tên đội bóng đã tồn tại!");
        
        try{
            TeamDAO dao = new TeamDAO();
            return dao.updateById(t);
        }
        catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập thông tin đội bóng: " + e.getMessage(), e);
        }
        
    }
    
    public boolean deleteTeamById(int teamId){
        try{
            TeamDAO dao = new TeamDAO();
            return dao.deleteById(teamId);
        }
        catch(DatabaseException e){
            throw new ServiceException("Không thể xóa đội bóng: " + e.getMessage(), e);
        }
    }
    
    public List<Team> searchTeam(Team t){
        if(t == null)
            throw new ValidationException("Đội bóng không hợp lệ!");
        
        try{
            TeamDAO dao = new TeamDAO();
            return dao.searchTeam(t);
        }
        catch(DatabaseException e){
            throw new ServiceException("Không thể tìm đội bóng: " + e.getMessage(), e);
        }
        
    }
    
    public List<Player> getPlayersOfTeam(int teamId){
        PlayerService pService = new PlayerService();
        return pService.getPlayersByTeamID(teamId);
    }
    
    public int countTotalPlayersOfTeam(int teamId){
        PlayerService pService = new PlayerService();
        return pService.countNumberOfPlayerInTeam(teamId);
    }
    
    public void addTeams(List<Team> list){
        
        if(list == null || list.isEmpty())
            throw new ValidationException("Danh sách đội bóng không hợp lệ!");
        
        try {
            TeamDAO tDAO = new TeamDAO();
            tDAO.add(list);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm danh sách đội bóng : " + e.getMessage(), e);
        } 
    }
    
    public List<ErrorDetail> validateTeams(List<Team> teams) {

        List<ErrorDetail> errors = new ArrayList<>();
        Set<String> nameSet = getAllTeamName();

        for (int i = 0; i < teams.size(); i++) {

            Team t = teams.get(i);
            int row = i + 2; // Excel: dòng 2 trở đi

            // 1. Tên đội không được trống
            if (t.getTeamName() == null || t.getTeamName().isBlank()) {
                errors.add(new ErrorDetail(row, "Tên đội bóng không được để trống"));
            }

            // 2. Phòng ban không được trống
            if (t.getDepartment() == null || t.getDepartment().isBlank()) {
                errors.add(new ErrorDetail(row, "Phòng ban không được để trống"));
            }

            // 3. Trùng tên đội trong cùng file
            if (!nameSet.add(t.getTeamName())) {
                errors.add(new ErrorDetail(row, "Tên đội bị trùng"));
            }
            
            // 4/ Tên trọng tài không được để trống
            if (t.getCoachName() == null || t.getCoachName().isBlank()){
                errors.add(new ErrorDetail(row, "Tên trọng tài không được để trống"));
            }
        }

        return errors;
    }
    
    public void exportTeamsToExcel(String folderPath) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<Team> teams = getAllTeam();
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "teams_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Teams");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tên đội");
            header.createCell(1).setCellValue("Khoa");
            header.createCell(2).setCellValue("Huấn luyện viên");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (Team t : teams) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(t.getTeamName());
                row.createCell(1).setCellValue(t.getDepartment());
                row.createCell(2).setCellValue(t.getCoachName());
            }

            // Auto-size cột
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi export Excel");
        }
    }
    
}
