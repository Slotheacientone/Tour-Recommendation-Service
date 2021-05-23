package edu.hcmuaf.tourrecommendationservice.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database manager.
 *
 * @author Viet-PH
 */
@Component
public class DatabaseManager {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/tourism";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "Ngay22031999";
    private Connection connection;
    private boolean isOpen = false;

    /**
     * Get mysql data source
     *
     * @return data source
     */
    public DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(DatabaseManager.DB_URL);
        dataSource.setUser(DatabaseManager.USER_NAME);
        dataSource.setPassword(DatabaseManager.PASSWORD);
        return dataSource;
    }

    /**
     * Open connection to database.
     *
     * @return connection to database
     */
    public Connection openConnection(){
        try{
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        isOpen = true;
        return connection;
    }

    /**
     * Close connection to database.
     *
     * @throws SQLException sql exception
     */
    public void closeConnection(){
        if(isOpen){
            try {
                connection.close();
                isOpen=false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
