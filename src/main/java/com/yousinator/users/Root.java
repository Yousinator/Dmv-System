package com.yousinator.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.yousinator.DatabaseInitializer;

public class Root extends Users {
    Scanner sc = new Scanner(System.in);

    public Root() {
    }

    public Root(String rootName, int rootPassword) {
        super(rootName, rootPassword);
    }

    public Admin[] addAdmin(Admin[] admins, String adminName, int adminPassword) {
        Admin admin = new Admin();

        admin.setUsername(adminName);
        admin.setPassword(adminPassword);

        ArrayList<Admin> adminList = new ArrayList<Admin>(Arrays.asList(admins));
        adminList.add(admin);

        return admins = adminList.toArray(admins);
    }

    public static List<String> fetchAllAdminUsernames() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE userType = 'admin'";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return usernames;
    }

    public static boolean addNewAdmin(String username, int password) {
        String sql = "INSERT INTO users (username, password, userType) VALUES (?, ?, 'admin')";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:dmv.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, password);

            int insertedRows = pstmt.executeUpdate();
            return insertedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
