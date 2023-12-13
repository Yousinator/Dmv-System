/**
 * * DatabaseInitializer class for the Car DMV System.
 *
 * *Database Design:
 * ! - 'users' Table: Stores user information. Each user has a unique ID, username, password, and userType (customer, admin, root).
 * !- 'cars' Table: Contains car details, including make, model, year, color, VIN, engine type, fuel type, and license plate. Each car is linked to a user ID from the 'users' table.
 *
 * !This class is responsible for setting up and initializing the database, including creating tables and inserting initial data.
 */

package com.yousinator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yousinator.car.Car;
import com.yousinator.users.Admin;
import com.yousinator.users.Customer;
import com.yousinator.users.Root;
import com.yousinator.users.Users;

public class DatabaseInitializer {
    private static Car[] cars = {
            new Car("Toyota", "Camry", "2018", "Red", "9278364728", "I4", "Petrol", "2212Y"),
            new Car("Cheverolet", "Malibu", "2019", "Blue", "2373840291", "I4", "Diesel", "7832A"),
            new Car("Ferrari", "F40", "1985", "Yellow", "2734649302", "V8", "Petrol", "5432B"),
            new Car("Dodge", "Charger SRT8", "2014", "Grey", "2836514253", "V8", "Petrol", "SRT8"),
            new Car("BMW", "M5", "2022", "Green", "8163534206", "V8TT", "Petrol", "1831K")
    };
    private static Customer[] customers = {
            new Customer("Omar", 2378, cars[0]),
            new Customer("Ahmad", 7236, cars[1]),
            new Customer("Noor", 2389, cars[2]),
            new Customer("Amro", 2398, cars[3]),
            new Customer("Qais", 1267, cars[4])
    };
    private static final String DB_URL = "jdbc:sqlite:dmv.db"; // Database file path

    /**
     * Creates necessary tables in the database.
     * ! Important: This method must be executed if the dmv.db file is empty or
     * doesn't exist.
     */

    private static void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {

            // SQL statement to create the 'users' table
            String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL, " +
                    "password INTEGER NOT NULL, " +
                    "userType TEXT NOT NULL" + // Removed the comma here
                    ");";

            // SQL statement to create the 'cars' table
            String createCarTable = "CREATE TABLE IF NOT EXISTS cars (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "make TEXT NOT NULL, " +
                    "model TEXT NOT NULL, " +
                    "year TEXT, " +
                    "color TEXT, " +
                    "vin TEXT, " +
                    "engineType TEXT, " +
                    "fuelType TEXT, " +
                    "licensePlate TEXT, " +
                    "ownerId INTEGER, " +
                    "FOREIGN KEY (ownerId) REFERENCES users(id)" +
                    ");";

            stmt.execute(createUserTable);
            stmt.execute(createCarTable);
        } catch (SQLException e) {
            System.out.println("Database table creation error: " + e.getMessage());
        }
    }

    /**
     * Inserts predefined customers and cars into the database.
     * ! Use this method only for initial data loading.
     */
    private static void insertCustomersAndCars() {
        // Assuming 'customers' and 'cars' arrays are as provided

        // Insert customers and store their generated IDs
        int[] customerIds = new int[customers.length];
        for (int i = 0; i < customers.length; i++) {
            customerIds[i] = insertUser(customers[i], "customer");
        }

        // Insert cars with the corresponding customer IDs
        for (int i = 0; i < cars.length; i++) {
            insertCar(cars[i], customerIds[i]);
        }
    }

    private static int insertUser(Customer customer, String userType) {
        String sql = "INSERT INTO users (username, password, userType) VALUES (?, ?, ?)";
        int userId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getUsername());
            pstmt.setInt(2, customer.getPassword());
            pstmt.setString(3, userType);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    private static int insertUser(Users user, String userType) {
        String sql = "INSERT INTO users (username, password, userType) VALUES (?, ?, ?)";
        int userId = -1;

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, user.getPassword());
            pstmt.setString(3, userType);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    private static void insertCar(Car car, int ownerId) {
        String sql = "INSERT INTO cars (make, model, year, color, vin, engineType, fuelType, licensePlate, ownerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getBrand());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getYear());
            pstmt.setString(4, car.getColor());
            pstmt.setString(5, car.getVin());
            pstmt.setString(6, car.getEngineType());
            pstmt.setString(7, car.getFuelType());
            pstmt.setString(8, car.getLicensePlate());
            pstmt.setInt(9, ownerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertInitialData() {
        insertUser(new Admin("Saud", 2211), "admin");
        insertUser(new Admin("Nizar", 9261), "admin");
        insertUser(new Root("Yousef", 2004), "root");
        insertCustomersAndCars();

    }
    // Additional helper methods (insertUser, insertCar)...
    // These methods handle the insertion of individual records into the database.

    /**
     * Initializes the database by creating tables and inserting initial data.
     * ! Call this method only once to set up the database. Duplicate calls can
     * create redundant data.
     */
    public static void initializeDatabase() {
        createTables();
        // insertInitialData(); Use this only once, if the DB is empty

    }

    /**
     * Establishes a connection to the database.
     * 
     * @return Connection object or null if connection fails.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dmv.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
