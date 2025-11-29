/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.FinishedMatchDetailsDAO;
import N23DCCN058.fm.dao.TeamDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.model.FinishedMatchDetails;
import N23DCCN058.fm.model.LeagueTableRow;
import N23DCCN058.fm.model.Team;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
public class LeagueTableService {
    
    private LeagueTableRow updateRowWithMatchDetails(LeagueTableRow row, int ourGoal, int otherGoal){
        row.setPlayed(row.getPlayed() + 1);
        if(ourGoal > otherGoal) row.setWins(row.getWins() + 1);
        else if(ourGoal == otherGoal) row.setDraws(row.getDraws() + 1);
        else row.setLosses(row.getLosses() + 1);
        row.setGoalsFor(row.getGoalsFor() + ourGoal);
        row.setGoalsAgainst(row.getGoalsAgainst() + otherGoal);
        return row;
    }
    
    public List<LeagueTableRow> calculateLeagueTable(){
        List<FinishedMatchDetails> list = null;
        List<Team> teams = null;
        
        try {
            FinishedMatchDetailsDAO fmdDAO = new FinishedMatchDetailsDAO();
            list = fmdDAO.getAll();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy dữ liệu trận đấu :" + e.getMessage(), e);
        }
        
        TeamService service = new TeamService();
        teams = service.getAllTeam();
        
        Map<Integer, LeagueTableRow> data = new HashMap<>();
        
        for(Team team : teams) 
            data.put(team.getTeamId(), new LeagueTableRow(team.getTeamId(), team.getTeamName(), 0, 0, 0, 0, 0, 0, 0, 0, 0));
        
        for(FinishedMatchDetails match : list){
            int homeTeam = match.getHomeTeam();
            int awayTeam = match.getAwayTeam();
            int homeGoals = match.getHomeGoals();
            int awayGoals = match.getAwayGoals();
            
            String homeName = service.getTeamById(homeTeam).getTeamName();
            String awayName = service.getTeamById(awayTeam).getTeamName();
            
            LeagueTableRow homeRow = data.getOrDefault(homeTeam, new LeagueTableRow(homeTeam, homeName, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            updateRowWithMatchDetails(homeRow, homeGoals, awayGoals);
            data.put(homeTeam, homeRow);
            
            LeagueTableRow awayRow = data.getOrDefault(awayTeam, new LeagueTableRow(awayTeam, awayName, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            this.updateRowWithMatchDetails(awayRow, awayGoals, homeGoals);
            data.put(awayTeam, awayRow);
        }
        
        List<LeagueTableRow> ranking = new ArrayList<>(data.values());
        
        for(LeagueTableRow row : ranking){
            row.setGoalDifference(row.getGoalsFor() - row.getGoalsAgainst());
            row.setPoints(row.getWins() * 3 + row.getDraws());
        }
        
        ranking.sort((x, y) -> {
            if(Integer.compare(x.getPoints(), y.getPoints()) != 0) 
                return Integer.compare(y.getPoints(), x.getPoints());
            else if(Integer.compare(x.getGoalDifference(), y.getGoalDifference()) != 0)
                return Integer.compare(y.getGoalDifference(), x.getGoalDifference());
            else 
                return Integer.compare(y.getGoalsFor(), x.getGoalsFor());
        });
        
        for(int i = 1; i <= ranking.size(); i++){
            LeagueTableRow row = ranking.get(i-1);
            row.setRank(i);
        }
        
        return ranking;
    }
    
    public void exportRankingToExcel(String folderPath) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<LeagueTableRow> ranking = calculateLeagueTable();
            
            TeamService teamService = new TeamService();
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "Ranking" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Ranking");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tên đội");
            header.createCell(1).setCellValue("Số trận đã chơi");
            header.createCell(2).setCellValue("Số trận thắng");
            header.createCell(3).setCellValue("Số trận hòa");
            header.createCell(4).setCellValue("Số trận thua");
            header.createCell(5).setCellValue("Số bàn thắng");
            header.createCell(6).setCellValue("Số bàn thua");
            header.createCell(7).setCellValue("Hiệu số");
            header.createCell(8).setCellValue("Tổng điểm");
            header.createCell(9).setCellValue("Xếp hạng");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (LeagueTableRow r : ranking) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(r.getTeamName());
                row.createCell(1).setCellValue(r.getPlayed());
                row.createCell(2).setCellValue(r.getWins());
                row.createCell(3).setCellValue(r.getDraws());
                row.createCell(4).setCellValue(r.getLosses());
                row.createCell(5).setCellValue(r.getGoalsFor());
                row.createCell(6).setCellValue(r.getGoalsAgainst());
                row.createCell(7).setCellValue(r.getGoalDifference());
                row.createCell(8).setCellValue(r.getPoints());
                row.createCell(9).setCellValue(r.getRank());
            }

            // Auto-size cột
            for (int i = 0; i < 10; i++) {
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
