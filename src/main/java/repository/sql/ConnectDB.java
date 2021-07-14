package repository.sql;

import java.sql.*;

public class ConnectDB {

    private static PreparedStatement preparedStatement;
    private static Connection connection;
    private static final String DATABASE_URL = "jdbc:mysql://localhost/crud";
    private static final String USER = "root";
    private static final String PASSWORD = "1111";


    private ConnectDB(){}

    public static synchronized PreparedStatement getPreparedStatement(String SQLTeam){
        try {
            if(connection == null){
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            }
            // если сделаем preparedStatement сингелтоном то не сможем менять sql команды
            preparedStatement = connection.prepareStatement(SQLTeam,Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    public static void close(){
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
