/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package N23DCCN058.fm.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author WokWee
 */
public class DBConnectionPool {
    private static HikariDataSource dataSource;
    
    static {
        try (InputStream input = DBConnectionPool.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Không tìm thấy file application.properties trong classpath!");
            }

            Properties props = new Properties();
            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            // Các tham số HikariCP
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "10")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimumIdle", "2")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("hikari.connectionTimeout", "30000")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("hikari.idleTimeout", "600000")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("hikari.maxLifetime", "1800000")));

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi khởi tạo HikariCP: " + e.getMessage(), e);
        }
    }
    
    //Hàm lấy kết nối
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    //Hàm đóng pool khi thoát ứng dụng 
    public static void close(){
        if(dataSource != null) 
            dataSource.close();
    }
}
