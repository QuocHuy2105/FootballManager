/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public enum PlayerPosition {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD;
    
    public static PlayerPosition fromString(String position){
        if(position == null) return null;
        else return PlayerPosition.valueOf(position.toUpperCase());
    }
}
