/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.Main;

import N23DCCN058.fm.ui.frame.MainFrame;
import N23DCCN058.fm.ui.frame.ScreenManager;

/**
 *
 * @author WokWee
 */
public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            ScreenManager.init(frame);
            frame.setVisible(true);  // QUAN TRỌNG: Phải có dòng này!
        });
    }
}
