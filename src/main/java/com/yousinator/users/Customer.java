package com.yousinator.users;

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

    public void printAllInfo() {
        Users user = new Users();

        user.printAllInfo(this);
    }

    public String searchInfo(String switchCase) {
        Users user = new Users();
        return user.searchInfo(this, switchCase);
    }
}
