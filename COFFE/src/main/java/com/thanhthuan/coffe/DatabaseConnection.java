package com.thanhthuan.coffe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection databaseLink;

    public static Connection databaseLink() {
        return databaseLink;
    }

    public static Connection getConnection() {
        String databaseName = "pocbase";
        String databaseUser = "thuan";
        String databasePassword = "18112005";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng kết nối ở đây nếu cần thiết
            // Thí dụ: if (databaseLink != null) databaseLink.close();
        }

        return databaseLink;
    }
}
