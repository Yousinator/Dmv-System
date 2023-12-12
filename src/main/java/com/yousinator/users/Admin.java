package com.yousinator.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yousinator.DatabaseInitializer;

public class Admin extends Users {
    public Admin() {
    }

    public Admin(String adminName, int adminPassword) {
        super(adminName, adminPassword);
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO users (username, password, userType) VALUES (?, ?, 'admin')";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.getUsername());
            pstmt.setInt(2, this.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Fetch an admin from the database
    public static Admin fetchFromDatabase(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND userType = 'admin'";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin(rs.getString("username"), rs.getInt("password"));
                return admin;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Update admin in the database
    public void updateInDatabase() {
        String sql = "UPDATE users SET password = ? WHERE username = ? AND userType = 'admin'";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.getPassword());
            pstmt.setString(2, this.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete admin from the database
    public void deleteFromDatabase() {
        String sql = "DELETE FROM users WHERE username = ? AND userType = 'admin'";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
