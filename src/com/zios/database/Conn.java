package com.zios.database;

import com.zios.root.ToolBox;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class Conn {
    private static Connection connection;
    private static String dbName;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Conn.connection = connection;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        Conn.dbName = dbName;
    }

    static {
        dbName = "config.db";

        if (!connect()) {
            new ToolBox().print("\tUnable to connect to database.", true);
        }
    }

    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:".concat(dbName));
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
}
