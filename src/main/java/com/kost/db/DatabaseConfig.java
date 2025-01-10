package com.kost.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config properties file");
                System.exit(1);
            }

            properties.load(input);
        }catch (IOException e) {
            System.err.println("Sorry, unable to find config properties file");
            e.printStackTrace();
        }
    }

    public static String getDBUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDBUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDBPassword() {
        return properties.getProperty("db.password");
    }
}
