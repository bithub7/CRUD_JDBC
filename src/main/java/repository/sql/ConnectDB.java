package repository.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:mysql://localhost/crud";
    private static final String USER = "root";
    private static final String PASSWORD = "1111";


    private ConnectDB(){}

    public static synchronized Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}
