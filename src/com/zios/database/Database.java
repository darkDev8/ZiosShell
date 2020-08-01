package com.zios.database;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements DatabaseListener {

    @Override
    public int getAnimationTime() {

        try {
            int time = 0;

            try (Statement stmt = Conn.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT time FROM settings;")) {

                while (rs.next()) {
                    time = rs.getInt("time");
                }
            }
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean updateAnimationTime(int time) {
        try (PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE settings SET time =?")) {
            pst.setInt(1, time);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
