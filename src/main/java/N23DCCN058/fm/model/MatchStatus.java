/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package N23DCCN058.fm.model;

/**
 *
 * @author WokWee
 */
public enum MatchStatus {
    CHUA_DIEN_RA,
    DA_DIEN_RA;
    
    public static MatchStatus fromString(String status){
        if(status == null) return null;
        return MatchStatus.valueOf(status.toUpperCase());
    }

    @Override
    public String toString() {
        return "MatchStatus{" + "ordinal=" + ordinal() + ", name=" + name() + '}';
    }
    
    
}
