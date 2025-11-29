/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

/**
 *
 * @author WokWee
 */
public class NameValidationChecking {
    
    private static final int MAX_LENGTH_OF_NAME = 50;
    
    public static void check(String name, String colName) throws Exception{
        if(name == null || name.trim().isEmpty())
            throw new Exception(colName + " không được để trống!");
        
        if(name.length() > MAX_LENGTH_OF_NAME)
            throw new Exception(colName + " phải có độ dài từ 1->50 kí tự!");
    }
    
}
