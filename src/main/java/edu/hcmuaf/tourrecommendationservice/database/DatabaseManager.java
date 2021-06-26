package edu.hcmuaf.tourrecommendationservice.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.datasource.url}")
    private String dbURL;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    private Connection connection;
    private boolean isOpen = false;

    /**
     * Get mysql data source
     *
     * @return data source
     */
    public DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(dbURL);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Open connection to database.
     *
     * @return connection to database
     */
    public Connection openConnection(){
        try{
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(dbURL, userName, password);
        } catch (SQLException | ClassNotFoundException throwables) {
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
