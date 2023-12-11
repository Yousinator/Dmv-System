package com.yousinator.car;

public class Car {
    private String brand = null;
    private String model = null;
    private String year = null;
    private String color;
    private String vin = null;
    private String engineType;
    private String fuelType;
    private String licensePlate;

    public Car(String brand, String model, String year, String color, String vin, String engineType,
            String fuelType, String licensePlate) {
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

    public void setColor(String color) {
        this.color = color;
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
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
