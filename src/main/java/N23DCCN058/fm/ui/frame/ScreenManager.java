/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.ui.frame;

import java.util.Stack;

/**
 *
 * @author WokWee
 */
public class ScreenManager {
    private static ScreenManager instance;
    private final MainFrame mainFrame;
    private static final Stack<String> history = new Stack<>();
    private String current;
    
    private ScreenManager(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public static void init(MainFrame frame){
        if(instance == null){
            instance = new ScreenManager(frame);
        }
    }
    
    public static ScreenManager get(){
        return instance;
    }
    
    public void show(String cardName){
        if(current != null && cardName != null && !cardName.equals(current)){
            history.push(current);
        }
        current = cardName;
        mainFrame.showPanel(cardName);
    }
    
    public void back(){
        if (!history.isEmpty()) {
            String prev = history.pop();
            current = prev;
            mainFrame.showPanel(prev);
        } else {
            mainFrame.showHome();
        }
    }
    
    public String getCurrent() {
        return current;
    }
    
    public void clearHistory(){
        history.clear();
        current = null;
    }
    
}

