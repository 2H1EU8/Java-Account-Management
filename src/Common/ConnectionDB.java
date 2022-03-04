package Common;

import java.sql.*;

public class ConnectionDB {
    private static java.sql.Connection connection;
    public static java.sql.Connection getConnection() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=AccountManagement;";
        String username = "sa";
        String password = "sa";
        java.sql.Connection connection = DriverManager.getConnection(dbURL,username,password);

        return connection;
    }

}
