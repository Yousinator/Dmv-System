package com.yousinator.users;

import java.util.*;

public class Root extends Users {
    Scanner sc = new Scanner(System.in);

    public Root() {
    }

    public Root(String rootName, int rootPassword) {
        super(rootName, rootPassword);
    }

    public void changeInfo(Customer customer) {
        Admin admin = new Admin();
        admin.changeInfo(customer);
    }

    public Admin[] addAdmin(Admin[] admins, String adminName, int adminPassword) {
        Admin admin = new Admin();

        admin.setUsername(adminName);
        admin.setPassword(adminPassword);

        ArrayList<Admin> adminList = new ArrayList<Admin>(Arrays.asList(admins));
        adminList.add(admin);

        return admins = adminList.toArray(admins);
    }
}
