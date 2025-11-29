/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.MatchEventDAO;
import N23DCCN058.fm.dao.MatchPlayerDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.EventType;
import static N23DCCN058.fm.model.EventType.SUBSTITUTION_IN;
import static N23DCCN058.fm.model.EventType.SUBSTITUTION_OUT;
import N23DCCN058.fm.model.MatchEvents;
import N23DCCN058.fm.model.MatchPlayers;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.PlayerPosition;
import N23DCCN058.fm.model.PlayerRole;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.EventTimeValidationChecking;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
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
public class MatchEventService {
    
    public List<MatchEvents> getEventsOfTeamInMatch(int matchId, int teamId){
        
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.getAllEventsOfMatchAndTeam(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách sự kiện của đội trong trận: " + e.getMessage(), e);
        }
        
    }
    
    public List<MatchEvents> getEventsOfMatch(int matchId){
        
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.getAllEventsOfMatch(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách sự kiện trong trận: " + e.getMessage(), e);
        }
        
    }
    
    public List<MatchEvents> getAllEvent(){
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách sự kiện: " + e.getMessage(), e);
        }
        
    }
    
    public boolean startMatch(int matchId){
        
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.add(new MatchEvents(matchId, null, EventType.MATCH_START, 0));
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean endMatch(int matchId){
        
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.add(new MatchEvents(matchId, null, EventType.MATCH_END, 60));
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean isEventValid(MatchEvents event){
        
        if(event == null)
            throw new ValidationException("Sự kiện truyền vào không hợp lệ!");
        if(event.getMatchId() == null)
            throw new ValidationException("Id trận đấu không được để trống!");
        if(event.getPlayerId() == null)
            throw new ValidationException("Id cầu thủ không được để trống!");
        if(event.getEventType() == null)
            throw new ValidationException("Loại sự kiện không được để trống!");
        try {
            EventTimeValidationChecking.check(event.getEventTime(), "Thời gian xảy ra sự kiện");
        } catch(ValidationException e){
            throw e;
        }
        
        boolean onField = isPlayerOnField(event.getMatchId(), event.getPlayerId(), event.getEventTime());
        EventType type = event.getEventType();
        
        switch (type) {
            case SUBSTITUTION_IN -> {
                if(onField)
                    throw new ValidationException("Không thể thay vào cầu thủ đang ở trên sân đấu!");
            }
            case SUBSTITUTION_OUT -> {
                if(!onField)
                    throw new ValidationException("Không thể thay ra cầu thủ đang không ở trên sân đấu!");
            }
            default -> {
                if(!onField)
                    throw new ValidationException("Không thể thêm sự kiện cho cầu thủ đang không trên sân đấu");
            }
        }
        
        return true;
    }
    
    public boolean addEvent(MatchEvents event){
        
        if(!isEventValid(event)) return false;
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.add(event);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm sự kiện: " + e.getMessage(), e);
        }
        
    }
    
    public boolean updateEvent(MatchEvents event){
        
        if(!isEventValid(event)) return false;
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.update(event);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhật sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteEventById(int eventId){
        if(eventId <= 0)
            throw new ValidationException("Id sự kiện không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.deleteById(eventId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteAllEventsOfMatch(int matchId){
        if(matchId <= 0)
             throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.deleteAllEventsOfMatch(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa sự kiện của trận: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteAllEvent(){
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.deleteAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa toàn bộ sự kiện: " + e.getMessage(), e);
        }
    }
    
    public boolean isPlayerOnField(int matchId, int playerId, int time) {

        if (matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if (playerId <= 0)
            throw new ValidationException("Id cầu thủ không hợp lệ!");
        try {
            EventTimeValidationChecking.check(time, "Thời gian xảy ra sự kiện");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

        List<MatchEvents> events;
        PlayerRole role;
        try {
            MatchEventDAO dao = new MatchEventDAO();
            events = dao.getEventsOfPlayerInMatch(matchId, playerId);
        } catch (DatabaseException e) {
            throw new ServiceException("Không thể lấy toàn bộ sự kiện của cầu thủ trong trận: " + e.getMessage(), e);
        }
        try {
            MatchPlayerDAO dao = new MatchPlayerDAO();
            role = dao.getPlayerRoleInMatch(matchId, playerId);
        } catch (DatabaseException e) {
            throw new ServiceException("Không thể lấy vai trò của cầu thủ trong trận: " + e.getMessage(), e);
        }

        // Sắp xếp sự kiện theo thời gian tăng dần
        events.sort(Comparator.comparingInt(MatchEvents::getEventTime));

        boolean isOnField = role.equals(PlayerRole.STARTING);
        int yellowCardCount = 0;

        for (MatchEvents event : events) {
            if (event.getEventTime() > time) break;

            switch (event.getEventType()) {
                case SUBSTITUTION_IN -> isOnField = true;
                case SUBSTITUTION_OUT -> isOnField = false;
                case YELLOW_CARD -> {
                    yellowCardCount++;
                    if (yellowCardCount >= 2) {
                        // Nhận 2 thẻ vàng => tương đương thẻ đỏ => rời sân
                        return false;
                    }
                }
                case RED_CARD -> {
                    // Thẻ đỏ trực tiếp => rời sân
                    return false;
                }
                default -> {}
            }
        }

        return isOnField;
    }
    
    public int countTotalPlayedTimeOfPlayer(int playerId){
        try{
            MatchPlayerDAO mpDAO = new MatchPlayerDAO();
            MatchEventDAO meDAO = new MatchEventDAO();
            List<MatchPlayers> matches = mpDAO.getByPlayerId(playerId);
            
            int totalTime = 0;
            final int MATCH_DURATION = 60;
            
            for(MatchPlayers match : matches){
                int matchId = match.getMatchId();
                PlayerRole playerRole = match.getPlayerRole();
                if(playerRole == null) continue;
                
                List<MatchEvents> events = meDAO.getEventsOfPlayerInMatch(matchId, playerId);
                int inTime = -1;
                if(playerRole.equals(PlayerRole.STARTING)) inTime = 0;
                for(MatchEvents event : events){
                    if(event.getEventType().equals(EventType.SUBSTITUTION_IN)) inTime = event.getEventTime();
                    else if(event.getEventType().equals(EventType.SUBSTITUTION_OUT)){
                        totalTime += event.getEventTime() - inTime;
                        inTime = -1;
                    }
                }
                if(inTime != -1) totalTime += MATCH_DURATION - inTime;
                
            }
            return totalTime;
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm thời gian đã chơi của cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public List<MatchEvents> getAllEventOfMatch(int matchId){
        
        if(matchId <= 0)
            throw new ValidationException("Id trận đấu không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.getAllEventsOfMatch(matchId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách sự kiện của trận đấu: " + e.getMessage(), e);
        }
        
    }
    
    public int countGoalsOfTeamInMatch(int matchId,int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");

        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countGoalsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số bàn thắng của đội trong trận: " + e.getMessage(), e);
        }
    }
    
    public int countPenaltyGoalsOfTemInMatch(int matchId,int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");

        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countPenaltyGoalsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số bàn thắng bằng penalty của đội trong trận: " + e.getMessage(), e);
        }
    }
    
    public int countOwnGoalsOfTeamInMatch(int matchId, int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");

        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countOwnGoalsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số bàn phản lưới nhà của đội trong trận: " + e.getMessage(), e);
        }
    }
    
    public int countFoulsOfTeamInMatch(int matchId,int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countFoulsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số lần phạm lỗi của đội trong trận: " + e.getMessage(),e);
        }
    }
    
    public int countYellowCardsOfTeamInMatch(int matchId,int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countYellowCardsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số thẻ vàng của đội trong trận: " + e.getMessage(),e);
        }
    }
    
    public int countRedCardsOfTeamInMatch(int matchId,int teamId){
        if(matchId <= 0) 
            throw new ValidationException("Id trận đấu không hợp lệ!");
        if(teamId <= 0)
            throw new ValidationException("Id đội bóng không hợp lệ!");
        
        try {
            MatchEventDAO dao = new MatchEventDAO();
            return dao.countRedCardsOfTeamInMatch(matchId, teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số thẻ đỏ của đội trong trận: " + e.getMessage(),e);
        }
    }
    
    
    public void exportEventsOfMatchToExcel(String folderPath, int matchId) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<MatchEvents> events = getAllEventOfMatch(matchId);
            
            PlayerService playerService = new PlayerService();
            
            MatchPlayerService mpService = new MatchPlayerService();
            Map<Integer, PlayerPosition> positions = mpService.getAllOfMatch(matchId).stream()
                    .collect(Collectors.toMap(MatchPlayers -> MatchPlayers.getPlayerId(), MatchPlayers -> MatchPlayers.getPosition()));
            
            TeamService teamService = new TeamService();
            Map<Integer, String> teamName = teamService.getAllTeam()
                    .stream()
                    .collect(Collectors.toMap(
                            Team -> Team.getTeamId(),
                            Team -> Team.getTeamName()
                    ));
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "eventss_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Events");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tên cầu thủ");
            header.createCell(1).setCellValue("Vị trí");
            header.createCell(2).setCellValue("Đội");
            header.createCell(3).setCellValue("Sự kiện");
            header.createCell(4).setCellValue("Thời gian");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (MatchEvents e : events) {
                Row row = sheet.createRow(rowIndex++);
                
                if(e.getPlayerId() == null){
                    row.createCell(0).setCellValue("");
                    row.createCell(1).setCellValue("");
                    row.createCell(2).setCellValue("");
                    row.createCell(3).setCellValue(e.getEventType().getVietnamese());
                    row.createCell(4).setCellValue(e.getEventTime());
                    continue;
                }
                
                Player player = playerService.getPlayerById(e.getPlayerId());
                row.createCell(0).setCellValue(player.getPlayerName());
                row.createCell(1).setCellValue(positions.get(player.getPlayerId()).name());
                row.createCell(2).setCellValue(teamName.get(player.getTeamId()));
                row.createCell(3).setCellValue(e.getEventType().getVietnamese());
                row.createCell(4).setCellValue(e.getEventTime());
            }

            // Auto-size cột
            for (int i = 0; i < 5; i++) {
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
