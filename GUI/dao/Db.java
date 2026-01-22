/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Database utilities for establishing and managing PostgreSQL connections used by the Panda Express POS system.
 *
 * Author: Team 42
 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final String URL = getenvOr("DB_URL", "url");
    private static final String USER = getenvOr("DB_USER", "user");
    private static final String PASSWORD = getenvOr("DB_PASSWORD", "password");

    /**
     * Gets database address, username and password
     *
     * @param var var relating to actually information
     * @param defaultValue information for wanted var
     * @return correct string
     */

    public static String getenvOr(String var, String defaultValue) {
        String val = System.getenv(var);
        return (val == null || val.isEmpty()) ? defaultValue : val;
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}