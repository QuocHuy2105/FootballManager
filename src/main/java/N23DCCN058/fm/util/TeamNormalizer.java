/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import N23DCCN058.fm.model.Team;

/**
 *
 * @author WokWee
 */
public class TeamNormalizer {
    public static Team normalize(Team t){
        if(t.getTeamName() != null ) t.setTeamName(DataNormalizer.normalizeName(t.getTeamName()));
        if(t.getDepartment() != null ) t.setDepartment(DataNormalizer.normalizeName(t.getDepartment()));
        if(t.getCoachName() != null ) t.setCoachName(DataNormalizer.normalizeName(t.getCoachName()));
        return t;
    }
}
