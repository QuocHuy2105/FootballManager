/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.PlayerDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.ErrorDetail;
import N23DCCN058.fm.model.Player;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.NameValidationChecking;
import N23DCCN058.fm.util.PlayerNormalizer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class PlayerService {
    
    public List<Player> getPlayersByTeamID(int teamId){
        if(teamId <= 0)
            throw new ValidationException("Team id không hợp lệ!");
        
        try{
            PlayerDAO dao = new PlayerDAO();
            return dao.getAllByTeamId(teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách cầu thủ của đội bóng:  " + e.getMessage(), e);
        }
    }
    
    public Set<String> getAllPlayerNames(){
        try{
            PlayerDAO dao = new PlayerDAO();
            return dao.getAllPlayerName();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách tên cầu thủ của đội bóng:  " + e.getMessage(), e);
        }
    }
    
    public int countNumberOfPlayerInTeam(int teamId){
        if(teamId <= 0)
            throw new ValidationException("Team id không hợp lệ!");
        
        try{
            PlayerDAO dao = new PlayerDAO();
            return dao.countTotalPlayersOfTeam(teamId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể đếm số cầu thủ của đội bóng: " + e.getMessage(), e);
        }
    }
    
    public List<Player> getAllPlayer(){
        try{
            PlayerDAO dao = new PlayerDAO();
            return dao.getAll();
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public boolean addPlayer(Player player){
        
        PlayerDAO dao = new PlayerDAO();
        
        if(player == null)
            throw new ValidationException("Cầu thủ truyền vào không hợp lệ!");

        try {
            NameValidationChecking.check(player.getPlayerName(), "Tên cầu thủ");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        if(player.getDob() == null)
            throw new ValidationException("Ngày sinh của cầu thủ không hợp lệ hoặc đang trống");
        
        if(player.getJerseyNumber() == null)
            throw new ValidationException("Số áo không hợp lệ hoặc đang trống");
        
        if(player.getJerseyNumber() <= 0 || player.getJerseyNumber() > 99)
            throw new ValidationException("Số áo phải có giá trị từ 1 đến 99");
        
        try {
            if(dao.existsJerseyNumberOfTeam(player.getTeamId(), player.getJerseyNumber()))
                throw new ValidationException("Số áo đã tồn tại trong đội!");
        } catch(DatabaseException e){
            throw new ServiceException("Không thể kiểm tra số áo của cầu thủ: " + e.getMessage(), e);
        }
        
        if(player.getPosition() == null)
            throw new ValidationException("Vị trí của cầu thủ không được để trống!");
        
        if(player.getTeamId() == null)
            throw new ValidationException("Team id của cầu thủ không được để trống!");
        
        player = PlayerNormalizer.normalize(player);
        
        try{
            return dao.add(player);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public void addPlayers(List<Player> players){
        if(players == null)
            throw new ValidationException("Danh sách cầu thủ không được để trống");
        
        try{
            PlayerDAO dao = new PlayerDAO();
            dao.add(players);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể thêm cầu thủ: " + e.getMessage(), e);
        }
        
    }
    
    public boolean updatePlayer(Player player){
        
        PlayerDAO dao = new PlayerDAO();
        
        if(player == null)
            throw new ValidationException("Cầu thủ truyền vào không hợp lệ!");
        
        if(player.getPlayerId() == null || player.getPlayerId() <= 0 )
            throw new ValidationException("Player id không hợp lệ!");
        
//        if(player.getPlayerName() == null || player.getPlayerName().trim().isEmpty())
//            throw new ValidationException("Tên cầu thủ không được để trống!");

        try {
            NameValidationChecking.check(player.getPlayerName(), "Tên cầu thủ");
        } catch (Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        if(player.getDob() == null)
            throw new ValidationException("Ngày sinh của cầu thủ không hợp lệ hoặc đang trống");
        
        if(player.getTeamId() == null)
            throw new ValidationException("Team id của cầu thủ không được để trống!");
        
        if(player.getJerseyNumber() == null)
            throw new ValidationException("Số áo không hợp lệ hoặc đang trống");
        
        if(player.getJerseyNumber() <= 0 || player.getJerseyNumber() > 99)
            throw new ValidationException("Số áo phải có giá trị từ 1 đến 99");
        
        try {
            Player p = dao.getById(player.getPlayerId());
            if(!Objects.equals(p.getJerseyNumber(), player.getJerseyNumber()) && dao.existsJerseyNumberOfTeam(player.getTeamId(), player.getJerseyNumber()))
                throw new ValidationException("Số áo đã tồn tại trong đội!");
        } catch(DatabaseException e){
            throw new ServiceException("Không thể kiểm tra số áo của cầu thủ: " + e.getMessage(), e);
        }
        
        if(player.getPosition() == null)
            throw new ValidationException("Vị trí của cầu thủ không được để trống!");
        
        player = PlayerNormalizer.normalize(player);
        
        try{
            return dao.updateById(player);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập thông tin cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public boolean deletePlayerById(int playerId){
        if(playerId <= 0)
            throw new ValidationException("Player id không hợp lệ!");
        
        try{
            PlayerDAO dao = new PlayerDAO();
            return dao.deleteById(playerId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa cầu thủ: " + e.getMessage(), e);
        }
        
    }
    
    public List<Player> searchPlayer(Player player){
        if(player == null)
            throw new ValidationException("Cầu thủ truyền vào không hợp lệ!");
        
        player = PlayerNormalizer.normalize(player);
        
        try {
            PlayerDAO dao = new PlayerDAO();
            return dao.searchPlayer(player);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể tìm cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public Player getPlayerById(int playerId){
        if(playerId <= 0)
            throw new ValidationException("Player id không hợp lệ!");
        
        try {
            PlayerDAO dao = new PlayerDAO();
            return dao.getById(playerId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public List<ErrorDetail> validatePlayers(List<Player> players) {

        List<ErrorDetail> errors = new ArrayList<>();
        
        List<Player> listPlayer = getAllPlayer();
        
        Map<Integer, Set<Integer>> soAo = new HashMap<>();
        
        TeamService teamService = new TeamService();
        List<Team> teams = teamService.getAllTeam();
        Set<Integer> teamIds = new HashSet<>();
        
        for(Team team : teams){
            soAo.put(team.getTeamId(), new HashSet<Integer>());
            teamIds.add(team.getTeamId());
        }
        
        for(Player player : listPlayer){
            Set<Integer> tmp = soAo.getOrDefault(player.getTeamId(), new HashSet<>());
            tmp.add(player.getJerseyNumber());
            soAo.put(player.getTeamId(), tmp);
        };
        
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            int row = i + 2;
            
            if(player.getPlayerName() == null || player.getPlayerName().isBlank() || player.getPlayerName().length() > 50){
                errors.add(new ErrorDetail(row, "Tên cầu thủ không được để trống và phải có 1->50 kí tự"));
            }
            
            if(player.getDob() == null){
                errors.add(new ErrorDetail(row, "Ngày sinh cầu thủ không được để trống"));
            }
            
            if(player.getJerseyNumber() == null || player.getJerseyNumber() <= 0 || player.getJerseyNumber() > 99)
                errors.add(new ErrorDetail(row, "Số áo cầu thủ đang trống hoặc không hợp lệ (1 -> 99)"));
            
            if(player.getJerseyNumber() != null &&
                    (soAo.containsKey(player.getTeamId()) 
                        && soAo.get(player.getTeamId()).contains(player.getJerseyNumber()))){
                errors.add(new ErrorDetail(row, "Số áo của cầu thủ đã tồn tại trong đội"));
                Set<Integer> tmp = soAo.getOrDefault(player.getTeamId(), new HashSet<>());
                tmp.add(player.getJerseyNumber());
                soAo.put(player.getTeamId(), tmp);
            }
            
            if(player.getPosition() == null){
                errors.add(new ErrorDetail(row, "Vị trí của cầu thủ không được để trống"));
            }
            
            if(player.getTeamId() == null){
                errors.add(new ErrorDetail(row, "Đội của cầu thủ không được để trống"));
            }
            
            if(!teamIds.contains(player.getTeamId())){
                errors.add(new ErrorDetail(row, "Đội bóng không tồn tại"));
            }
        }
        
        return errors;
    }
    
    public void exportPlayersToExcel(String folderPath){ 
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<Player> players = getAllPlayer();
            
            TeamService teamService = new TeamService();
            Map<Integer, String> teamName = teamService.getAllTeam()
                    .stream()
                    .collect(Collectors.toMap(
                            Team -> Team.getTeamId(),
                            Team -> Team.getTeamName()
                    ));
                    
                    
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "players_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Players");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tên cầu thủ");
            header.createCell(1).setCellValue("Ngày sinh");
            header.createCell(2).setCellValue("Số áo");
            header.createCell(3).setCellValue("Vị trí");
            header.createCell(4).setCellValue("Tên đội");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (Player p: players) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(p.getPlayerName());
                row.createCell(1).setCellValue(p.getDob().toString());
                row.createCell(2).setCellValue(p.getJerseyNumber());
                row.createCell(3).setCellValue(p.getPosition().name());
                row.createCell(4).setCellValue(teamName.get(p.getTeamId()));
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
