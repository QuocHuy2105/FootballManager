/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import N23DCCN058.fm.model.Referee;

/**
 *
 * @author WokWee
 */
public class RefereeNormalizer {
    
    public static Referee normalize(Referee r){
        if(r.getRefereeName() != null) r.setRefereeName(DataNormalizer.normalizeName(r.getRefereeName()));
        return r;
    }
}
