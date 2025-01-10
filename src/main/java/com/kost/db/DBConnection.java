package com.kost.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con;

    public static Connection connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String jdbcUrl = DatabaseConfig.getDBUrl();
            String user = DatabaseConfig.getDBUsername();
            String password = DatabaseConfig.getDBPassword();

            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
