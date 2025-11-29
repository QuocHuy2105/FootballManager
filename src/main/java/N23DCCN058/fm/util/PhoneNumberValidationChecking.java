/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

/**
 *
 * @author WokWee
 */
public class PhoneNumberValidationChecking {
    
    private static final String PHONE_REGEX = "^0\\d{9}$";

    public static void check(String phoneNumber, String colName) throws Exception{
        if (phoneNumber == null || phoneNumber.trim().isEmpty())
            throw new Exception(colName + " không được để trống!");

        if (!phoneNumber.matches(PHONE_REGEX))
            throw new Exception(colName + " phải bắt đầu bằng số 0 và gồm đúng 10 chữ số!");
    }
    
}
