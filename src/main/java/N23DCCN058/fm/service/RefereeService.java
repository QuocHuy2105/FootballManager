/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.service;

import N23DCCN058.fm.dao.RefereeDAO;
import N23DCCN058.fm.exception.DatabaseException;
import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.exception.ValidationException;
import N23DCCN058.fm.model.ErrorDetail;
import N23DCCN058.fm.model.Referee;
import N23DCCN058.fm.model.Team;
import N23DCCN058.fm.util.NameValidationChecking;
import N23DCCN058.fm.util.PhoneNumberValidationChecking;
import N23DCCN058.fm.util.RefereeNormalizer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author WokWee
 */
public class RefereeService {
    
    public List<Referee> getAllReferee(){
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.getAll();
        } catch (DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách trọng tài: " + e.getMessage(), e);
        }
    }
    
    public Set<String> getAllPhoneNumber(){
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.getAllPhoneNumber();
        }catch(DatabaseException e){
            throw new ServiceException("Không thể lấy danh sách số điện thoại: " + e.getMessage(), e);
        }
    }
    
    public Referee getRefereeById(int refereeId){
        if(refereeId <= 0)
            throw new ValidationException("Referee id không hợp lệ");
        
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.getById(refereeId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể lấy cầu thủ: " + e.getMessage(), e);
        }
    }
    
    public boolean addReferee(Referee referee){
        if(referee == null)
            throw new ValidationException("Trọng tài truyền vào không hợp lệ!");
        
        try {
            NameValidationChecking.check(referee.getRefereeName(), "Tên trọng tài");
            PhoneNumberValidationChecking.check(referee.getPhoneNumber(), "Số điện thoại");
        } catch(Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        Set<String> phoneNumbers = getAllPhoneNumber();
        
        if(phoneNumbers.contains(referee.getPhoneNumber()))
            throw new ValidationException("Số điện thoại đã tồn tại! Vui lòng kiểm tra lại.");
        
        referee = RefereeNormalizer.normalize(referee);
        
        try{
            RefereeDAO dao = new RefereeDAO();
            return dao.add(referee);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể thêm trọng tài: " + e.getMessage(), e);
        }
    }
    
    public void addReferees(List<Referee> referees){
        try{
            RefereeDAO dao = new RefereeDAO();
            dao.add(referees);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể thêm danh sách trọng tài: " + e.getMessage(), e);
        }
    }
    
    public boolean updateReferee(Referee referee){
        
        if(referee == null)
            throw new ValidationException("Trọng tài truyền vào không hợp lệ!");
        
        if(referee.getRefereeId() == null)
            throw new ValidationException("Referee id không được để trống");
        
        try {
            NameValidationChecking.check(referee.getRefereeName(), "Tên trọng tài");
            PhoneNumberValidationChecking.check(referee.getPhoneNumber(), "Số điện thoại");
        } catch(Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        Set<String> phoneNumbers = getAllPhoneNumber();
        
        if(phoneNumbers.contains(referee.getPhoneNumber())){
            Referee ref = searchRefereeByPhoneNumber(referee.getPhoneNumber());
            
            if(!Objects.equals(ref.getRefereeId(), referee.getRefereeId()))
                throw new ValidationException("Số điện thoại đã tồn tại! Vui lòng kiểm tra lại.");
        }
        
        referee = RefereeNormalizer.normalize(referee);
        
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.updateById(referee);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể cập nhập thông tin trọng tài: " + e.getMessage(), e);
        }
    }
    
    public boolean deleteRefereeById(Integer refereeId){
        if(refereeId <= 0)
            throw new ValidationException("Referee id không hợp lệ!");
        
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.deleteById(refereeId);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể xóa trọng tài: " + e.getMessage(), e);
        }
    }
    
    public Referee searchRefereeByPhoneNumber(String phoneNumber){
        try {
            PhoneNumberValidationChecking.check(phoneNumber, "Số điện thoại");
        } catch(Exception e){
            throw new ValidationException(e.getMessage());
        }
        
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.searchByPhoneNumber(phoneNumber);
        } catch(DatabaseException e){
            throw new ServiceException("Không thể tìm trọng tài bằng số điện thoại: " + e.getMessage(), e);
        }
    }
    
    public List<Referee> searchReferee(Referee referee){
        if(referee == null)
            throw new ValidationException("Trọng tài truyền vào không hợp lệ!");
        
        referee = RefereeNormalizer.normalize(referee);
        
        try {
            RefereeDAO dao = new RefereeDAO();
            return dao.searchReferee(referee);
        } catch (DatabaseException e){
            throw new ServiceException("Không thể tìm trọng tài: " + e.getMessage(), e);
        }
    }
    
    public List<ErrorDetail> validateReferees(List<Referee> refs) {

        List<ErrorDetail> errors = new ArrayList<>();
        Set<String> phoneNumber = getAllPhoneNumber();
        
        for(int i = 0; i < refs.size(); i++){
            
            Referee referee = refs.get(i);
            int row = i + 2;
            
            if(referee.getRefereeName() == null || referee.getRefereeName().isBlank() || referee.getRefereeName().length() > 50)
                errors.add(new ErrorDetail(row, "Tên trọng tài không được để trống và phải có 1->50 kí tự"));
            
            if(referee.getPhoneNumber() == null || referee.getRefereeName().isBlank() || referee.getRefereeName().length() != 10)
                errors.add(new ErrorDetail(row, "Số điện thoại không được để trống và phải có chính xác 10 số"));
            
            
        }
        
        return new ArrayList<>();
    }
    
    
     public void exportTeamsToExcel(String folderPath) {
        try {
            // Tạo file name tự động: teams_yyyyMMdd_HHmmss.xlsx
            List<Referee> referees = getAllReferee();
            
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "referees_" + timestamp + ".xlsx";
            String filePath = folderPath + File.separator + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Teams");

            // Tạo hàng tiêu đề
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tên");
            header.createCell(1).setCellValue("Số điện thoại");

            // Ghi dữ liệu
            int rowIndex = 1;
            for (Referee r : referees) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(r.getRefereeName());
                row.createCell(1).setCellValue(r.getPhoneNumber());
            }

            // Auto-size cột
            for (int i = 0; i < 2; i++) {
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
