/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import java.sql.SQLException;

/**
 *
 * @author WokWee
 */
public class DBErrorTranslator {
    
    public static String translate(SQLException e) {
        int code = e.getErrorCode();

        return switch (code) {
            case 1062 -> "Dữ liệu đã tồn tại (vi phạm ràng buộc duy nhất)!";// Duplicate entry
            case 1451 -> "Không thể xóa bản ghi — dữ liệu đang được tham chiếu!";// Cannot delete or update a parent row
            case 1452 -> "Không thể thêm/sửa — dữ liệu tham chiếu không tồn tại!";// Cannot add or update a child row
            case 1048 -> "Một số trường bắt buộc đang để trống!";// Column cannot be null
            default -> "Lỗi cơ sở dữ liệu không xác định (mã: " + code + ")";
        }; 
    }

    private DBErrorTranslator() {} // Ngăn tạo instance
}
