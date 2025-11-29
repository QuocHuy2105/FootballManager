/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.MatchDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.Match;
import N23DCCN058.fm.model.MatchStatus;
import N23DCCN058.fm.model.Team;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class MatchService {
    
    public List<Match> getAllMatch(){
        try{
            MatchDAO dao = new MatchDAO();
            return dao.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách trận đấu: " + e.getMessage(), e);
        }
    }
    
    public boolean addMatch(Match match){
        if(match == null)
            throw new ValidationException("Trận đấu không hợp lệ!");
        if(match.getRefereeId() == null)
            throw new ValidationException("Id trọng tài không được để trống!");
        if(match.getMatchDate() == null)
            throw new ValidationException("Ngày diễn ra trận đấu không hợp lệ hoặc đang trống!");
        if(match.getMatchStatus() == null)
            throw new ValidationException("Trạng thái trận đấu không được để trống!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.add(match);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm trận đấu: " + e.getMessage(), e);
        }
        
    }
    
    public boolean updateMatch(Match match){
        if(match == null)
            throw new ValidationException("Trận đấu không hợp lệ!");
        if(match.getRefereeId() == null)
            throw new ValidationException("Id trọng tài không được để trống!");
        if(match.getMatchDate() == null)
            throw new ValidationException("Ngày diễn ra trận đấu không hợp lệ hoặc đang trống!");
        if(match.getMatchStatus() == null)
            throw new ValidationException("Trạng thái trận đấu không được để trống!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.updateById(match);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập trận đấu: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteMatchById(int matchId){
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu truyền vào không hợp lệ!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.deleteById(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public List<Match> getAllFinishedMatch(){
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.getAllFinishedMatches();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách trận đấu đã diễn ra: " + e.getMessage(), e);
        }
        
    }
    
    public List<Match> getAllUnfinishedMatch(){
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.getAllUnfinishedMatches();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách trận đấu chưa diễn ra:" + e.getMessage(), e);
        }
        
    }
    
    public List<Match> searchMatchByDate(Date date){
        if(date == null)
            throw new ValidationException("Ngày diễn ra trận đấu không hợp lệ hoặc đang trống!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.searchMatchByDate(date);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể tìm thấy danh sách trận đấu diễn ra trong ngày " + date.toString() + ": " + e.getMessage(), e);
        }
        
    }
    
    public List<Match> searchMatchByRefereeId(int refereeId){
        if(refereeId <= 0)
            throw new ValidationException("Id trọng tài không hợp lệ!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.searchMatchByRefereeId(refereeId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể tìm thấy danh sách trận đấu của trọng tài " + refereeId + ": " + e.getMessage(), e);
        }
        
    }
    
    public boolean isMatchFinished(int matchId){
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.isFinished(matchId);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể kiểm tra trạng thái trận đấu: " + e.getMessage(), e);
        }
    }
    
    
    public List<Match> getAllReadyMatch(){
        try {
            MatchDAO dao = new MatchDAO();
            return dao.getAllReadyMatch();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách trận đấu đủ điều kiện để ghi sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean isReady(int matchId){
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchDAO dao = new MatchDAO();
            return dao.isMatchReady(matchId);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể kiểm tra trạng thái trận đấu: " + e.getMessage(), e);
        }
    }
    
    public void exportUnfinishedToExcel(String folderPath) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<Match> matches = getAllUnfinishedMatch();
            
            RefereeService rService = new RefereeService();
            Map<Integer, String> refereeName = rService.getAllReferee()
                    .stream()
                    .collect(Collectors.toMap(
                            Referee -> Referee.getRefereeId(),
                            Referee -> Referee.getRefereeName()
                    ));
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "matches_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Matches");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Id trận");
            header.createCell(1).setCellValue("Tên trọng tài");
            header.createCell(2).setCellValue("Ngày thi đấu");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (Match m : matches) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(m.getMatchId());
                row.createCell(1).setCellValue(refereeName.get(m.getRefereeId()));
                row.createCell(2).setCellValue(m.getMatchDate().toString());
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
