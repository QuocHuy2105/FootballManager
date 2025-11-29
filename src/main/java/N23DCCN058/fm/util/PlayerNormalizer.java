/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.util;

import N23DCCN058.fm.model.Player;

/**
 *
 * @author WokWee
 */
public class PlayerNormalizer {
   
    public static Player normalize(Player p){
        if(p.getPlayerName() != null) p.setPlayerName(DataNormalizer.normalizeName(p.getPlayerName()));
        return p;
    }
    
}
