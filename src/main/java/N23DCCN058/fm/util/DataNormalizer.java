/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import java.sql.Date;

/**
 *
 * @author WokWee
 */
public class DataNormalizer {
    
    public static String normalizeWord(String word){
        word = word.trim().toLowerCase();
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
    
    public static String normalizeName(String name){
        name = name.trim();
        if("".equals(name)) return "";
        String[] words = name.split("\\s+");
        StringBuilder sb = new StringBuilder("");
        for(String x : words){
            if(!sb.isEmpty()) sb.append(" ");
            sb.append(normalizeWord(x));
        }
        return sb.toString();
    }
    
    public static Date normalizeDate(String str){
        return Date.valueOf(str);
    }
            
    
}
