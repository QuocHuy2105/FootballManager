/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.FinishedMatchDetailsDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.model.FinishedMatchDetails;
import N23DCCN058.fm.model.LeagueTableRow;
import N23DCCN058.fm.model.Match;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class FinishedMatchDetailsService {
    
    public FinishedMatchDetails getMatchDetail(int matchId){
        try {
            FinishedMatchDetailsDAO dao = new FinishedMatchDetailsDAO();
            return dao.getDetailsOfMatch(matchId);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy thông tin của trận: " + e.getMessage(), e);
        }
    }
    
    public List<FinishedMatchDetails> getAllMatchDetail(){
        try {
            FinishedMatchDetailsDAO dao = new FinishedMatchDetailsDAO();
            return dao.getAll();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách kết quả trận: " + e.getMessage(), e);
        }
    }
    
    public int countTotalWinGamesOfTeam(int teamId){
        FinishedMatchDetailsDAO fmdDAO = new FinishedMatchDetailsDAO();
        List<FinishedMatchDetails> list = null;
        try {
            list = fmdDAO.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy thông tin các trận đấu đã diễn ra: " + e.getMessage(), e);
        }
        int countWinGames = 0;
        for(FinishedMatchDetails match : list){
            if((match.getHomeTeam() == teamId && match.getHomeGoals() > match.getAwayGoals()) ||
                (match.getAwayTeam() == teamId && match.getAwayGoals() > match.getHomeGoals()))
                    countWinGames++;
        }   
        return countWinGames;
    }
    
    public int countTotalLoseGames(int teamId){
        FinishedMatchDetailsDAO fmdDAO = new FinishedMatchDetailsDAO();
        List<FinishedMatchDetails> list = null;
        try {
            list = fmdDAO.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy thông tin các trận đấu đã diễn ra: " + e.getMessage(), e);
        }
        int countLoseGames = 0;
        for(FinishedMatchDetails match : list){
            if((match.getHomeTeam() == teamId && match.getHomeGoals() < match.getAwayGoals()) ||
                (match.getAwayTeam() == teamId && match.getAwayGoals() < match.getHomeGoals()))
                    countLoseGames++;
        }   
        return countLoseGames;
    }
    
    public int countTotalDrawGames(int teamId){
        FinishedMatchDetailsDAO fmdDAO = new FinishedMatchDetailsDAO();
        List<FinishedMatchDetails> list = null;
        try {
            list = fmdDAO.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy thông tin các trận đấu đã diễn ra: " + e.getMessage(), e);
        }
        int countDrawGames = 0;
        for(FinishedMatchDetails match : list){
            if((match.getHomeTeam() == teamId || match.getAwayTeam() == teamId ) && 
                    Objects.equals(match.getHomeGoals(), match.getAwayGoals()))
                        countDrawGames++;
        }   
        return countDrawGames;
    }
    
    public int getTotalScores(int teamId){
        FinishedMatchDetailsDAO fmdDAO = new FinishedMatchDetailsDAO();
        List<FinishedMatchDetails> list = null;
        try {
            list = fmdDAO.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy thông tin các trận đấu đã diễn ra: " + e.getMessage(), e);
        }
        int totalScores = 0;
        for(FinishedMatchDetails match : list){
            if((match.getHomeTeam() == teamId && match.getHomeGoals() > match.getAwayGoals()) ||
                (match.getAwayTeam() == teamId && match.getAwayGoals() > match.getHomeGoals()))
                    totalScores += 3;
            else if((match.getHomeTeam() == teamId || match.getAwayTeam() == teamId ) && 
                    Objects.equals(match.getHomeGoals(), match.getAwayGoals()))
                    totalScores++;
        }   
        return totalScores;
    }
    
    public int getRank(int teamId){
        LeagueTableService lts = new LeagueTableService();
        int rank = -1;
        for(LeagueTableRow row : lts.calculateLeagueTable()){
            if(Integer.compare(row.getTeamId(), teamId) == 0){
                rank = row.getRank();
                break;
            }
        }
        return rank;
    }
    
    
    public void exportUnfinishedToExcel(String folderPath) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<FinishedMatchDetails> matches = getAllMatchDetail();
            
            RefereeService rService = new RefereeService();
            Map<Integer, String> refereeName = rService.getAllReferee()
                    .stream()
                    .collect(Collectors.toMap(
                            Referee -> Referee.getRefereeId(),
                            Referee -> Referee.getRefereeName()
                    ));
            
            TeamService teamService = new TeamService();
            Map<Integer, String> teamName = teamService.getAllTeam()
                    .stream()
                    .collect(Collectors.toMap(
                            Team -> Team.getTeamId(),
                            Team -> Team.getTeamName()
                    ));
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "finishedMatches_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Matches");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Id trận");
            header.createCell(1).setCellValue("Tên trọng tài");
            header.createCell(2).setCellValue("Ngày thi đấu");
            header.createCell(3).setCellValue("Đội nhà");
            header.createCell(4).setCellValue("Đôi khách");
            header.createCell(5).setCellValue("Số bàn thắng đội nhà");
            header.createCell(6).setCellValue("Số bàn thắng đội khách");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (FinishedMatchDetails m : matches) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(m.getMatchId());
                row.createCell(1).setCellValue(refereeName.get(m.getRefereeId()));
                row.createCell(2).setCellValue(m.getMatchDate().toString());
                row.createCell(3).setCellValue(teamName.get(m.getHomeTeam()));
                row.createCell(4).setCellValue(teamName.get(m.getAwayTeam()));
                row.createCell(5).setCellValue(m.getHomeGoals());
                row.createCell(6).setCellValue(m.getAwayGoals());
            }

            // Auto-size cột
            for (int i = 0; i < 7; i++) {
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
