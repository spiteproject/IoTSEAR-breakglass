package be.hcbgsystem.logging;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionFactory {

    private static BasicDataSource dataSource;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/hcbgsystem");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("");
        }
        return dataSource.getConnection();
    }
}