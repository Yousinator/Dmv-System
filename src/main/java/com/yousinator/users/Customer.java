package com.yousinator.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yousinator.DatabaseInitializer;
import com.yousinator.car.Car;

public class Customer {
    private String username;
    private int password;
    private Car car = new Car();

    public Customer() {
    }

    public Customer(String username, int password, Car car) {
        setUsername(username);
        setPassword(password);
        setCar(car);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Boolean authintecate(String username, int password) {
        if (username.equals(this.getUsername())) {
            if (password == this.getPassword()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getCarInfo(int customerId, String searchCriteria) {
        String result = "";
        String sql = "SELECT * FROM cars WHERE ownerId = ?";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                switch (searchCriteria) {
                    case "Make":
                        result = rs.getString("make");
                        break;
                    case "Model":
                        result = rs.getString("model");
                        break;
                    case "Color":
                        result = rs.getString("color");
                        break;
                    case "Engine":
                        result = rs.getString("engineType");
                        break;
                    case "VIN":
                        result = rs.getString("vin");
                        break;
                    case "License Plate":
                        result = rs.getString("licensePlate");
                        break;
                    case "Fuel Type":
                        result = rs.getString("fuelType");
                        break;
                    case "Year":
                        result = rs.getString("year");
                        break;
                    default:
                        result = "Invalid search criteria";
                        break;
                }
            } else {
                result = "No car found for the given customer ID";
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            result = "Error fetching car data";
        }

        return result;
    }

    public static List<String> fetchAllCustomerUsernames() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE userType = 'customer'";

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

    public static String searchCarInfo(int customerId, String searchCriteria) {
        String result = "";
        String sql = buildSearchQuery(customerId, searchCriteria); // You need to implement this method based on your
                                                                   // criteria

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString(searchCriteria); // Assuming the column name matches the criterion
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static String buildSearchQuery(int id, String searchCriteria) {
        // Implementation depends on your database schema and search requirements
        return "SELECT " + searchCriteria + " FROM cars WHERE ownerId = " + id;
    }

    public static int fetchCustomerIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ? AND userType = 'customer'";
        int customerId = -1;

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerId;
    }

    public static boolean addNewCustomerWithCar(String name, int password, String brand, String model, String engine,
            String fuel, String vin, String color, String year, String plate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dmv.db");
            conn.setAutoCommit(false); // Start transaction

            // Insert customer
            String insertUserSql = "INSERT INTO users (username, password, userType) VALUES (?, ?, 'customer')";
            pstmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setInt(2, password);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            int customerId;
            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                customerId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

            // Insert car
            String insertCarSql = "INSERT INTO cars (make, model, engineType, fuelType, vin, color, year, licensePlate, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertCarSql);
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setString(3, engine);
            pstmt.setString(4, fuel);
            pstmt.setString(5, vin);
            pstmt.setString(6, color);
            pstmt.setString(7, year);
            pstmt.setString(8, plate);
            pstmt.setInt(9, customerId);
            pstmt.executeUpdate();

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (generatedKeys != null)
                    generatedKeys.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
