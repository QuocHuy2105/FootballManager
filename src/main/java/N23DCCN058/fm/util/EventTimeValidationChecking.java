/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import N23DCCN058.fm.exception.ValidationException;

/**
 *
 * @author WokWee
 */
public class EventTimeValidationChecking {
    
    public static final int START_MATCH = 0;
    public static final int END_MATCH = 60;
    
    public static void check(Integer time, String columnName) throws ValidationException{
        if(time == null)
            throw new ValidationException(columnName + " không được để trống!");
        if(time < 0 || time > 60)
            throw new ValidationException(columnName + " phải có giá trị từ 0->60!");
    }
    
}
