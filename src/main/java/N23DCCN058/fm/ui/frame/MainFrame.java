
package N23DCCN058.fm.ui.frame;

import N23DCCN058.fm.exception.ServiceException;
import N23DCCN058.fm.service.TournamentService;
import N23DCCN058.fm.ui.panel.CaiDatPanel;
import N23DCCN058.fm.ui.panel.CauThuPanel;
import N23DCCN058.fm.ui.panel.ChiTietTranDauPanel;
import N23DCCN058.fm.ui.panel.KetQuaTranDauPanel;
import N23DCCN058.fm.ui.panel.DoiBongPanel;
import N23DCCN058.fm.ui.panel.GhiSuKienPanel;
import N23DCCN058.fm.ui.panel.GhiSuKienPanel_ChonTranDau;
import N23DCCN058.fm.ui.panel.HomePanel;
import N23DCCN058.fm.ui.panel.TaoGiaiDauPanel;
import N23DCCN058.fm.ui.panel.ThemCauThuPanel;
import N23DCCN058.fm.ui.panel.ThongKePanel;
import N23DCCN058.fm.ui.panel.TongQuanPanel;
import N23DCCN058.fm.ui.panel.TranDauPanel;
import N23DCCN058.fm.ui.panel.TrongTaiPanel;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    public static final String CARD_HOME = "HOME";
    public static final String CARD_TONGQUAN = "TONGQUAN";
    public static final String CARD_DOIBONG = "DOIBONG";
    public static final String CARD_CAUTHU = "CAUTHU";
    public static final String CARD_TRONGTAI = "TRONGTAI";
    public static final String CARD_TRANDAU = "TRANDAU";
    public static final String CARD_CHITIET_TRANDAU = "CHITIET_TRANDAU";
    public static final String CARD_KETQUA_TRANDAU = "KETQUA_TRANDAU";
    public static final String CARD_GHISUKIEN_CHONTRANDAU = "GHISUKIENCHONTRANDAU";
    public static final String CARD_GHISUKIEN = "GHISUKIEN";
    public static final String CARD_THONGKE = "THONGKE";
    public static final String CARD_TAOGIAI = "TAOGIAI";
    public static final String CARD_CAIDAT = "CAIDAT";
    public static final String CARD_THEMCAUTHU = "THEMCAUTHU";
    
    private static boolean isTournamentCreated;
    
    private CardLayout cardLayout;
    
    // Khai báo tất cả các panel
    private HomePanel homePanel;
    private TongQuanPanel tongQuanPanel;
    private DoiBongPanel doiBongPanel;
    private CauThuPanel cauThuPanel;
    private TrongTaiPanel trongTaiPanel;
    private TranDauPanel tranDauPanel;
    private KetQuaTranDauPanel ketQuaTranDauPanel;
    private ChiTietTranDauPanel chiTietTranDauPanel;
    private GhiSuKienPanel_ChonTranDau ghiSuKienPanelChonTranDau;
    private GhiSuKienPanel ghiSuKienPanel;
    private ThongKePanel thongKePanel;
    private TaoGiaiDauPanel taoGiaiDauPanel;
    private CaiDatPanel caiDatPanel;
    private ThemCauThuPanel themCauThu;


    public MainFrame() {
        updateTournamentStatus();
        initComponents();
        setSize(1000,600);
        setLocationRelativeTo(null);
        //setResizable(false); // Không cho phép resize cửa sổ
        
        // Lấy CardLayout từ mainPanel
        cardLayout = (CardLayout)(mainPanel.getLayout());
        
        // Khởi tạo tất cả các panel
        initAllPanels();
        
        // Thêm tất cả panel vào mainPanel
        addAllPanels();
        
        showHome();
        // Force update để đảm bảo hiển thị
        mainPanel.revalidate();
        mainPanel.repaint();
        
    }
    
    public void updateTournamentStatus(){
        TournamentService ts = new TournamentService();
        try {
            isTournamentCreated = ts.hasTournament();
        } catch(ServiceException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void setHomeBtn(){
        homePanel.setBtn();
    }
    
    public static void setTournamentStatus(boolean status){
        isTournamentCreated = status;
    }
    
    public static boolean isTournamentCreated(){
        return isTournamentCreated;
    }
    
    private void initAllPanels() {
        homePanel = new HomePanel();
        tongQuanPanel = new TongQuanPanel();
        doiBongPanel = new DoiBongPanel();
        cauThuPanel = new CauThuPanel();
        trongTaiPanel = new TrongTaiPanel();
        tranDauPanel = new TranDauPanel();
        thongKePanel = new ThongKePanel();
        caiDatPanel = new CaiDatPanel();
        themCauThu = new ThemCauThuPanel();
    }
    
    private void addAllPanels() {
        mainPanel.add(homePanel, CARD_HOME);
        mainPanel.add(tongQuanPanel, CARD_TONGQUAN);
        mainPanel.add(doiBongPanel, CARD_DOIBONG);
        mainPanel.add(cauThuPanel, CARD_CAUTHU);
        mainPanel.add(trongTaiPanel, CARD_TRONGTAI);
        mainPanel.add(tranDauPanel, CARD_TRANDAU);
        mainPanel.add(thongKePanel, CARD_THONGKE);
        mainPanel.add(caiDatPanel, CARD_CAIDAT);
        mainPanel.add(themCauThu, CARD_THEMCAUTHU);
    }
    
    public CauThuPanel getCauThuPanel(){
        return cauThuPanel;
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    // Các method tiện ích để chuyển panel
    public void showHome() {
        showPanel(CARD_HOME);
    }
    
    public void showTongQuan() {
        showPanel(CARD_TONGQUAN);
    }
    
    public void showDoiBong() {
        showPanel(CARD_DOIBONG);
    }
    
    public void showCauThu() {
        showPanel(CARD_CAUTHU);
    }
    
    public void showTrongTai() {
        showPanel(CARD_TRONGTAI);
    }
    
    public void showTranDau() {
        showPanel(CARD_TRANDAU);
    }
    
    public void showChiTietTranDau(int matchId){
        chiTietTranDauPanel = new ChiTietTranDauPanel(matchId);
        mainPanel.add(chiTietTranDauPanel, CARD_CHITIET_TRANDAU);
        ScreenManager.get().show(CARD_CHITIET_TRANDAU);
    }
    
    public void showKetQuaTranDau(int matchId) {
        ketQuaTranDauPanel = new KetQuaTranDauPanel(matchId);
        mainPanel.add(ketQuaTranDauPanel, CARD_KETQUA_TRANDAU);
        ScreenManager.get().show(CARD_KETQUA_TRANDAU);
    }
    
    public void showGhiSuKienChonTranDau() {
        ghiSuKienPanelChonTranDau = new GhiSuKienPanel_ChonTranDau();
        mainPanel.add(ghiSuKienPanelChonTranDau, CARD_GHISUKIEN_CHONTRANDAU);
        ScreenManager.get().show(CARD_GHISUKIEN_CHONTRANDAU);
    }
    
    public void showGhiSuKien(int matchId) {
        ghiSuKienPanel = new GhiSuKienPanel(matchId);
        mainPanel.add(ghiSuKienPanel, CARD_GHISUKIEN);
        ScreenManager.get().show(CARD_GHISUKIEN);
    }
    
    public void showThongKe() {
        showPanel(CARD_THONGKE);
    }
    
    public void showTaoGiai() {
        taoGiaiDauPanel = new TaoGiaiDauPanel();
        mainPanel.add(taoGiaiDauPanel, CARD_TAOGIAI);
        showPanel(CARD_TAOGIAI);
    }
    
    public void showCaiDat() {
        showPanel(CARD_CAIDAT);
    }
    
    public void showThemCauThu() {
        showPanel(CARD_THEMCAUTHU);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setPreferredSize(new java.awt.Dimension(1000, 600));
        setResizable(false);

        mainPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(mainPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}