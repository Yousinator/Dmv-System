package com.yousinator.car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private int id; // Assuming there's an ID field in the database
    private String brand;
    private String model;
    private String year;
    private String color;
    private String vin;
    private String engineType;
    private String fuelType;
    private String licensePlate;

    // Database connection string
    private static final String DB_URL = "jdbc:sqlite:dmv.db";

    public Car(String brand, String model, String year, String color, String vin, String engineType,
            String fuelType, String licensePlate) {
        // Call the setters which now include database code
        setBrand(brand);
        setModel(model);
        setYear(year);
        setColor(color);
        setVin(vin);
        setEngineType(engineType);
        setFuelType(fuelType);
        setLicensePlate(licensePlate);
    }

    public Car() {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        try {
            if (this.getBrand().equals(null)) {
                this.brand = brand;
            } else {
                System.out.println("Cannot change brand");
            }
        } catch (Exception NullPointerException) {
            this.brand = brand;
        }

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        try {
            if (this.model.equals(null)) {
                this.model = model;
            } else {
                System.out.println("\n Cannot change model");
            }
        } catch (Exception NullPointerException) {
            this.model = model;
        }
    }

    public String getYear() {

        return year;
    }

    public void setYear(String year) {
        try {
            if (this.year.equals(null)) {
                this.year = year;
            } else {
                System.out.println("\n Cannot change year");
            }
        } catch (Exception NullPointerException) {
            this.year = year;
        }

    }

    public String getColor() {
        return color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        try {
            if (this.vin.equals(null)) {
                this.vin = vin;
            } else {
                System.out.println("\n Cannot change VIN");
            }
        } catch (Exception NullPointerException) {
            this.vin = vin;
        }

    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
        updateCarInDatabase("engineType", engineType);
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
        updateCarInDatabase("fuelType", fuelType);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        updateCarInDatabase("licensePlate", licensePlate);
    }

    public void setColor(String color) {
        this.color = color;
        updateCarInDatabase("color", color);
    }

    // Utility method to update car information in the database
    private void updateCarInDatabase(String field, String value) {
        String sql = "UPDATE cars SET " + field + " = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database update error: " + e.getMessage());
        }
    }

    // Method to fetch cars for a specific user
    public static List<Car> fetchCarsForUser(int userId) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE ownerId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Car car = new Car();
                car.id = rs.getInt("id");
                car.brand = rs.getString("brand");
                car.model = rs.getString("model");
                car.year = rs.getString("year");
                car.color = rs.getString("color");
                car.vin = rs.getString("vin");
                car.engineType = rs.getString("engineType");
                car.fuelType = rs.getString("fuelType");
                car.licensePlate = rs.getString("licensePlate");
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Database fetch error: " + e.getMessage());
        }

        return cars;
    }

    public static boolean updateCarDetails(int customerId, String attribute, String newValue) {
        String sql = buildUpdateSql(attribute); // Helper method to create SQL based on attribute

        if (sql.isEmpty()) {
            return false; // If the attribute is not valid, return false
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:dmv.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newValue);
            pstmt.setInt(2, customerId);
            int updatedRows = pstmt.executeUpdate();

            return updatedRows > 0; // Return true if the update was successful
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static String buildUpdateSql(String attribute) {
        switch (attribute) {
            case "Color":
                return "UPDATE cars SET color = ? WHERE ownerId = ?";
            case "Engine":
                return "UPDATE cars SET engineType = ? WHERE ownerId = ?";
            case "Fuel":
                return "UPDATE cars SET fuelType = ? WHERE ownerId = ?";
            case "Plate":
                return "UPDATE cars SET licensePlate = ? WHERE ownerId = ?";
            default:
                return ""; // Return empty string for an invalid attribute
        }
    }

}
