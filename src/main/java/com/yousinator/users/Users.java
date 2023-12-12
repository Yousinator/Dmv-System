package com.yousinator.users;

import com.yousinator.car.Car;

import java.util.*;

public class Users {
    private String username;
    private int password;

    public Users() {
    }

    public Users(String username, int password) {
        setUsername(username);
        setPassword(password);
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

    public String searchInfo(Customer customer, String switchCase) {
        Car car = customer.getCar();

        switch (switchCase) {
            case "Make":
                return car.getBrand();

            case "Model":
                return car.getModel();

            case "Color":
                return car.getColor();

            case "Engine":
                return car.getEngineType();

            case "VIN":
                return car.getVin();

            case "License Plate":
                return car.getLicensePlate();

            case "Fuel Type":
                return car.getFuelType();

            case "Year":
                return car.getYear();

        }
        return null;
    }

    public Customer[] addCustomer(Customer[] customers, String name, int password, String brand, String model,
            String engine,
            String fuel, String vin, String color, String year, String plate) {
        Car car = new Car();
        Customer customer = new Customer();
        customer.setUsername(name);
        customer.setPassword(password);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setColor(color);
        car.setVin(vin);
        car.setEngineType(engine);
        car.setFuelType(fuel);
        car.setLicensePlate(plate);
        customer.setCar(car);
        ArrayList<Customer> customersList = new ArrayList<Customer>(Arrays.asList(customers));
        customersList.add(customer);
        return customers = customersList.toArray(customers);
    }
}
