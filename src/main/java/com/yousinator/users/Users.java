package com.yousinator.users;

import com.yousinator.car.Car;

import java.util.*;

public class Users {
    private String username;
    private int password;
    private static Scanner sc = new Scanner(System.in);

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

    public void printAllInfo(Customer customer) {
        Car car = customer.getCar();
        System.out.println("\nCar Make: " + car.getBrand());
        System.out.println("Car Model: " + car.getModel());
        System.out.println("Car Year: " + car.getYear());
        System.out.println("Car Color: " + car.getColor());
        System.out.println("Car VIN: " + car.getVin());
        System.out.println("Car Engine: " + car.getEngineType());
        System.out.println("Car Fuel: " + car.getFuelType());
        System.out.println("Car Plate: " + car.getLicensePlate());
    }

    public void changeInfo(Customer customer) {
        Car car = customer.getCar();
        int attributeEditChoice = 0;
        do {
            System.out.println(
                    "1- Color  2- Engine  3- Fuel  4- Plate  5- Exit");
            attributeEditChoice = sc.nextInt();
            switch (attributeEditChoice) {
                case 1:
                    System.out.println("Enetr New car color: ");
                    car.setColor(sc.next());
                    System.out.println("Color changed succefully to --> " + car.getColor());
                    break;
                case 2:
                    System.out.println("Enter New car Engine: ");
                    car.setEngineType(sc.next());
                    System.out.println("Engine type changed succefully to --> " + car.getEngineType());

                    break;
                case 3:
                    System.out.println("Enter New car Fuel: ");
                    car.setFuelType(sc.next());
                    System.out.println("Fuel type changed succefully to --> " + car.getFuelType());
                    break;
                case 4:
                    System.out.println("Enter New car plate: ");
                    car.setLicensePlate(sc.next());
                    System.out.println("License plate changed succefully to --> " + car.getLicensePlate());
                    break;
                case 5:
                    System.out.println("Exiting Editing mode...");
                    break;
                default:
                    System.out.println("Choice unavailable");
                    break;
            }
        } while (attributeEditChoice != 5);
        customer.setCar(car);

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
