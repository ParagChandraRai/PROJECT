
package todolist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/univ";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "parag";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
