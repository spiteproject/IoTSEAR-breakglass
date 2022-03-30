package playground.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlTest {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:mysql://localhost:3306/hcbgsystem";
            // create a connection to the database
            conn = DriverManager.getConnection(url, "root", "");

            System.out.println("Connection to MySQL has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
