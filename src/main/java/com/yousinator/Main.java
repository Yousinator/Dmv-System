/*
    Writer's notes
    ======================
    ! It is recommended to have the "Better Comments" extension while reading this file.
    ! This only the Event-driven part of the code.

*/

/*
    Explaining Car DMV (Department of Motor Vehicles)
    ======================
    ? 1 > This program has three different user types of users: customers, admins, and roots, with root being most privileged.
    ? 2 > Each customer owns a car while admins and roots just manage the system.
    ? 3 > The system requires username and password authentication as privileges vary and data has to be secure.
    ? 4 > All users are in object form.
    ? 5 > Each user type has it's own array.
    ? 6 > Each user type has its own UI.
    ? 7 > This uses the card Layout and each user type has its own layout.
    ? 8 > At passwords text fields don't accept numbers as input.

*/

/*
    Explaining the code
    ======================
    ! 1 > Create and fill all the needed arrays.
    ! 2 > we create a recurring main menu for the user to choose from.
    ! 3 > Provide a list of all the users and create a switch for each user.
    ! 4 > Customer:
        * 5 > Check for the users's credentials if true return the customer's index else reutrn -1.
        * 6 > If number is greater than -1 display the info for the customer.
    ! 7 > Admin:
        * 8 > Check for the users's credentials if true return the user's index.
        * 9 > If number is greater than -1 let him choose from the admin menu.
        * 10 > If admin chooses searching:
            ? 11 > Let the admin choose from the customers list.
            ? 12 > Repeat 6.
        * 13 > If admin chooses change:
            ? 13 > Let the admin choose from customer list.
            ? 14 > After choosing the customer, let the admin choose the attribute to be changed.
            ? 15 > Change the attribute based on the admin's input.
        * 16 > If the admin chooses add customer:
            ? 17 > Prompt the user to add customer attributes.
            ? 18 > Arrays are modified using a function that changes the array in to arraylist and back.
    ! 19 > Root:
        * 20 > Repeat steps 8 through 18.
        * 21 > If the root chooses add admin:
            ? 22 > Prompt the user to enter admin attributes.
            ? 23 > Repeat 18. 
    
*/

//! ------------------------------------------------------ The Code ------------------------------------------------------ //
package com.yousinator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.yousinator.car.Car;
import com.yousinator.users.*;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public int validityCheck = -1;
    public int userChoice = -1;

    // ! ------------------------- Cars and Objects ------------------------

    private static Car[] cars = {
            new Car("Toyota", "Camry", "2018", "Red", "9278364728", "I4", "Petrol", "2212Y"),
            new Car("Cheverolet", "Malibu", "2019", "Blue", "2373840291", "I4", "Diesel", "7832A"),
            new Car("Ferrari", "F40", "1985", "Yellow", "2734649302", "V8", "Petrol", "5432B"),
            new Car("Dodge", "Charger SRT8", "2014", "Grey", "2836514253", "V8", "Petrol", "SRT8"),
            new Car("BMW", "M5", "2022", "Green", "8163534206", "V8TT", "Petrol", "1831K")
    };
    private static Admin[] admins = {
            new Admin("Saud", 2211),
            new Admin("Nizar", 9261)
    };
    private static Customer[] customers = {
            new Customer("Omar", 2378, cars[0]),
            new Customer("Ahmad", 7236, cars[1]),
            new Customer("Noor", 2389, cars[2]),
            new Customer("Amro", 2398, cars[3]),
            new Customer("Qais", 1267, cars[4])
    };
    private static Root[] roots = { new Root("Yousef", 2004) };

    // !--------------------- Main and App --------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        setTitle("DMV");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(500, 150);
        getContentPane().setBackground(Color.black);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create the screens
        JPanel mainMenu = mainMenuPanel();
        JPanel customerPanel = customerPanel();
        JPanel adminPanel = adminPanel();
        JPanel rootPanel = rootPanel();

        JPanel mainMenuWrMainer = new JPanel();
        mainMenuWrMainer.setPreferredSize(new Dimension(400, 200));
        mainMenuWrMainer.add(mainMenu);

        JPanel customerWrMainer = new JPanel();
        customerWrMainer.setPreferredSize(new Dimension(500, 400));
        customerWrMainer.add(customerPanel);

        JPanel adminWrMainer = new JPanel();
        adminWrMainer.setPreferredSize(new Dimension(500, 600));
        adminWrMainer.add(adminPanel);

        JPanel rootWrMainer = new JPanel();
        rootWrMainer.setPreferredSize(new Dimension(500, 680));
        rootWrMainer.add(rootPanel);

        mainPanel.add(mainMenuWrMainer, "Main Menu");
        mainPanel.add(customerWrMainer, "Customer");
        mainPanel.add(adminWrMainer, "Admin");
        mainPanel.add(rootWrMainer, "Root");

        add(mainPanel); // Add the mainPanel to the JFrame
        setVisible(true);
    }

    // ! -------------------- Main Panels ---------------------------------
    private JPanel mainMenuPanel() {
        setBackground(Color.BLACK);
        JPanel mainMenuPanel = new JPanel(new GridLayout(2, 5));
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JPanel titlePanel = titlePanel("Jordan DMV");
        JButton customerButton = new JButton("Customer");
        JButton adminButton = new JButton("Admin");
        JButton rootButton = new JButton("Root"), exitButton = new JButton("Exit DMV");

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(500, 260);
                cardLayout.show(mainPanel, "Customer");

            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(500, 630);
                cardLayout.show(mainPanel, "Admin");
            }
        });
        rootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(500, 710);
                cardLayout.show(mainPanel, "Root");
            }
        });
        exitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(customerButton);
        buttonsPanel.add(adminButton);
        buttonsPanel.add(rootButton);
        buttonsPanel.add(exitButton);

        mainMenuPanel.add(titlePanel);
        mainMenuPanel.add(buttonsPanel);

        return mainMenuPanel;
    }

    private JPanel customerPanel() {
        JPanel customerPanel = new JPanel(new BorderLayout());
        JPanel upperPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = titlePanel("Customer DMV");
        JPanel loginPanel = customerLogin();
        JPanel searchPanel = searchCustomer();
        JButton exitButton = new JButton("Exit Customer Mode");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = -1;
                validityCheck = -1;
                setSize(500, 150);
                cardLayout.show(mainPanel, "Main Menu");
            }
        });

        upperPanel.add(loginPanel, BorderLayout.NORTH);
        upperPanel.add(searchPanel, BorderLayout.CENTER);

        customerPanel.add(titlePanel, BorderLayout.NORTH);
        customerPanel.add(upperPanel, BorderLayout.CENTER);
        customerPanel.add(exitButton, BorderLayout.SOUTH);
        return customerPanel;
    }

    private JPanel adminPanel() {
        JPanel adminPanel = new JPanel(new BorderLayout());
        JPanel headPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel titlePanel = titlePanel("Admin");
        JPanel loginPanel = userLogin(admins);
        JPanel chooserPanel = chooseCustomer();
        JPanel searchPanel = searchCustomer();
        JPanel changePanel = changeCustomer();
        JPanel addCustomerPanel = addCustomer(admins);
        JButton exitButton = new JButton("Exit Admin Mode");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = -1;
                validityCheck = -1;
                setSize(500, 150);
                cardLayout.show(mainPanel, "Main Menu");
            }
        });

        // ! ---------------- HEAD ----------------
        headPanel.add(titlePanel, BorderLayout.NORTH);
        headPanel.add(loginPanel, BorderLayout.CENTER);
        headPanel.add(chooserPanel, BorderLayout.SOUTH);
        // ! ---------------- MIDDLE ---------------
        middlePanel.add(searchPanel, BorderLayout.NORTH);
        middlePanel.add(changePanel, BorderLayout.CENTER);
        middlePanel.add(addCustomerPanel, BorderLayout.SOUTH);
        // ! ---------------- Main ----------------
        adminPanel.add(headPanel, BorderLayout.NORTH);
        adminPanel.add(middlePanel, BorderLayout.CENTER);
        adminPanel.add(exitButton, BorderLayout.SOUTH);

        return adminPanel;
    }

    private JPanel rootPanel() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = titlePanel("Root");
        JPanel loginPanel = userLogin(roots);
        JPanel choserPanel = chooseCustomer();
        JPanel searchPanel = searchCustomer();
        JPanel changePanel = changeCustomer();
        JPanel headPanel = new JPanel(new BorderLayout()), middlePanel = new JPanel(new BorderLayout()),
                tailPanel = new JPanel(new BorderLayout());
        JPanel addCustomerPanel = addCustomer(roots);
        JPanel addAdminPanel = addAdminPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = -1;
                validityCheck = -1;
                setSize(500, 150);
                cardLayout.show(mainPanel, "Main Menu");
            }
        });

        // ! ---------------- HEAD ----------------
        headPanel.add(titlePanel, BorderLayout.NORTH);
        headPanel.add(loginPanel, BorderLayout.CENTER);
        headPanel.add(choserPanel, BorderLayout.SOUTH);
        // ! ---------------- MIDDLE ----------------
        middlePanel.add(searchPanel, BorderLayout.NORTH);
        middlePanel.add(changePanel, BorderLayout.CENTER);
        middlePanel.add(addCustomerPanel, BorderLayout.SOUTH);
        // ! ---------------- Tail ----------------
        tailPanel.add(addAdminPanel, BorderLayout.NORTH);
        tailPanel.add(exitButton, BorderLayout.CENTER);
        // ! ---------------- Main ----------------
        rootPanel.add(headPanel, BorderLayout.NORTH);
        rootPanel.add(middlePanel, BorderLayout.CENTER);
        rootPanel.add(tailPanel, BorderLayout.SOUTH);

        return rootPanel;
    }

    // !------------------ Side Panels -----------------------------------------

    private JPanel titlePanel(String title) {
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel customerLogin() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel secondaryPanel = new JPanel();
        JLabel passwordNotificationLabel = new JLabel("");
        JTextField loginField = new JTextField(10);
        JTextField passwordField = new JTextField(10);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (!Character.isDigit(character)) {
                    e.consume(); // Ignore the input by consuming the event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used in this example
            }
        });

        JButton loginButton = new JButton("Login");
        TitledBorder loginBorder = BorderFactory.createTitledBorder("Login");

        loginBorder.setTitleJustification(TitledBorder.LEFT);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validityCheck = -1;
                int password = 0;

                password = Integer.parseInt(passwordField.getText());

                validityCheck = checkPassword(customers, loginField.getText(), password);
                if (validityCheck == -1) {
                    passwordNotificationLabel.setText("Wrong Username or Password");
                    passwordNotificationLabel.setForeground(Color.RED);

                } else {
                    passwordNotificationLabel.setText("Welcome Back " + customers[validityCheck].getUsername());
                    passwordNotificationLabel.setForeground(Color.BLACK);
                }
                userChoice = validityCheck;
            }
        });

        secondaryPanel.add(new JLabel("Username:"));
        secondaryPanel.add(loginField);
        secondaryPanel.add(new JLabel("Password:"));
        secondaryPanel.add(passwordField);
        secondaryPanel.add(loginButton);
        loginPanel.setBorder(loginBorder);
        loginPanel.add(secondaryPanel, BorderLayout.NORTH);
        passwordNotificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(passwordNotificationLabel, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel userLogin(Users[] users) {
        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel secondaryPanel = new JPanel();
        JLabel passwordNotificationLabel = new JLabel("");
        JTextField loginField = new JTextField(10);
        JTextField passwordField = new JTextField(10);
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (!Character.isDigit(character)) {
                    e.consume(); // Ignore the input by consuming the event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used in this example
            }
        });
        JButton loginButton = new JButton("Login");
        TitledBorder loginBorder = BorderFactory.createTitledBorder("Login");
        loginBorder.setTitleJustification(TitledBorder.LEFT);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validityCheck = -1;
                int password = 0;

                password = Integer.parseInt(passwordField.getText());

                validityCheck = checkPassword(users, loginField.getText(), password);
                if (validityCheck == -1) {
                    passwordNotificationLabel.setText("Wrong Username or Password");
                    passwordNotificationLabel.setForeground(Color.RED);
                    password = 0;

                } else if (validityCheck != 1) {
                    if (users[0].equals(admins[0]) && users[1].equals(admins[1])) {
                        passwordNotificationLabel.setText("Welcome Back " + admins[validityCheck].getUsername());
                        passwordNotificationLabel.setForeground(Color.BLACK);
                    } else {
                        passwordNotificationLabel.setText("Welcome Back " + roots[validityCheck].getUsername());
                        passwordNotificationLabel.setForeground(Color.BLACK);
                    }
                }

            }
        });

        secondaryPanel.add(new JLabel("Username:"));
        secondaryPanel.add(loginField);
        secondaryPanel.add(new JLabel("Password:"));
        secondaryPanel.add(passwordField);
        secondaryPanel.add(loginButton);
        loginPanel.setBorder(loginBorder);
        loginPanel.add(secondaryPanel, BorderLayout.NORTH);
        passwordNotificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(passwordNotificationLabel, BorderLayout.CENTER);
        return loginPanel;
    }

    private JPanel chooseCustomer() {
        JPanel chooseUserPanel = new JPanel();
        DefaultComboBoxModel<String> modelSearchBox = new DefaultComboBoxModel<>();
        JComboBox<String> searchBox = new JComboBox<String>();
        JButton searchButton = new JButton("Choose");
        JButton refreshButton = new JButton("Refresh Customers");
        TitledBorder chooseBorder = BorderFactory.createTitledBorder("Choose a Customer");
        chooseBorder.setTitleJustification(TitledBorder.LEFT);

        chooseUserPanel.add(new JLabel("Choose a Customer:"));

        for (int i = 0; i < customers.length; i++) {
            modelSearchBox.addElement(customers[i].getUsername());
        }
        searchBox.setModel(modelSearchBox);

        refreshButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modelSearchBox.removeAllElements();
                        for (int i = 0; i < customers.length; i++) {
                            modelSearchBox.addElement(customers[i].getUsername());
                        }
                        searchBox.setModel(modelSearchBox);

                    }
                });

        searchButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String search = (String) searchBox.getSelectedItem();
                        for (int i = 0; i < customers.length; i++) {
                            if (customers[i].getUsername().equals(search)) {
                                userChoice = i;
                            }
                        }
                    }
                });

        chooseUserPanel.add(searchBox);
        chooseUserPanel.setBorder(chooseBorder);
        chooseUserPanel.add(searchButton);
        chooseUserPanel.add(refreshButton);

        return chooseUserPanel;

    }

    private JPanel searchCustomer() {
        JPanel searchMainPanel = new JPanel(new GridLayout(2, 5));
        JPanel buttonPanel = new JPanel();
        JPanel searchPanel = new JPanel();
        String[] searchList = { "Make", "Model", "Color", "Engine", "VIN", "License Plate", "Fuel Type", "Year" };
        JComboBox<String> searchBox = new JComboBox<String>(searchList);
        JTextField resultField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        TitledBorder searchBorder = BorderFactory.createTitledBorder("Search Customers");

        resultField.setEditable(false);
        searchBorder.setTitleJustification(TitledBorder.LEFT);
        searchMainPanel.setBorder(searchBorder);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = (String) searchBox.getSelectedItem();

                if (userChoice > -1 && validityCheck > -1) {
                    resultField.setText(customers[userChoice].searchInfo(search));
                }
            }
        });

        searchPanel.add(new JLabel("Choose one of the following:"));
        searchPanel.add(searchBox);
        searchPanel.add(new JLabel("Result:"));
        searchPanel.add(resultField);

        buttonPanel.add(searchButton);

        searchMainPanel.add(searchPanel);
        searchMainPanel.add(buttonPanel);

        return searchMainPanel;
    }

    private JPanel changeCustomer() {
        JPanel changeCustomerPanel = new JPanel(new GridLayout(3, 5));
        JPanel changePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        String[] changeList = { "Color", "Engine", "Fuel", "Plate" };
        JComboBox<String> searchBox = new JComboBox<String>(changeList);
        JTextField changeField = new JTextField(10);
        TitledBorder changeBorder = BorderFactory.createTitledBorder("Change Customer Information");
        JButton changeButton = new JButton("Change");

        changeBorder.setTitleJustification(TitledBorder.LEFT);
        changeCustomerPanel.setBorder(changeBorder);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = (String) searchBox.getSelectedItem();
                Car car = new Car();

                if (userChoice > -1 && validityCheck > -1) {
                    car = customers[userChoice].getCar();
                    switch (search) {

                        case "Color":
                            car.setColor(changeField.getText());
                            break;
                        case "Plate":
                            car.setLicensePlate(changeField.getText());
                            break;
                        case "Fuel":
                            car.setFuelType(changeField.getText());
                            break;
                        case "Engine":
                            car.setEngineType(changeField.getText());
                            break;
                    }
                    customers[userChoice].setCar(car);
                }

            }

        });

        changePanel.add(new JLabel("Choose one of the following:"));
        changePanel.add(searchBox);
        changePanel.add(new JLabel("Enter the new:"));
        changePanel.add(changeField);

        buttonPanel.add(changeButton, BorderLayout.SOUTH);

        changeCustomerPanel.add(changePanel);
        changeCustomerPanel.add(buttonPanel);

        return changeCustomerPanel;
    }

    JPanel addCustomer(Users[] user) {
        JPanel addCustomerPanel = new JPanel(new GridLayout(6, 4));
        JLabel statusLabel1 = new JLabel("");
        JLabel statusLabel2 = new JLabel("");
        JTextField nameField = new JTextField(10);
        JTextField passwordField = new JTextField(10);
        JTextField brandField = new JTextField(10);
        JTextField modelField = new JTextField(10);
        JTextField engineField = new JTextField(10);
        JTextField fuelField = new JTextField(10);
        JTextField vinField = new JTextField(10);
        JTextField colorField = new JTextField(10);
        JTextField yearField = new JTextField(10);
        JTextField plateField = new JTextField(10);
        JButton addButton = new JButton("Add User");
        TitledBorder addBorder = BorderFactory.createTitledBorder("Add Customer");

        addBorder.setTitleJustification(TitledBorder.LEFT);
        addCustomerPanel.setBorder(addBorder);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (!Character.isDigit(character)) {
                    e.consume(); // Ignore the input by consuming the event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used in this example
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int password = 0;
                Boolean result = true;
                if (validityCheck > -1) {

                    password = Integer.parseInt(passwordField.getText());

                    String name = nameField.getText(), brand = brandField.getText(), model = modelField.getText(),
                            engine = engineField.getText(), fuel = fuelField.getText(), vin = vinField.getText(),
                            color = colorField.getText(), year = yearField.getText(), plate = plateField.getText();

                    for (Customer customer : customers) {
                        if (name.equals(customer.getUsername())) {
                            result = false;
                        }
                    }

                    if (!name.equals("") && !brand.equals("") && !model.equals("")
                            && !engine.equals("") && !fuel.equals("") && !vin.equals("") && result
                            && !color.equals("") && !year.equals("") && !plate.equals("") && password != 0) {
                        customers = user[validityCheck].addCustomer(customers, name, password, brand, model, engine,
                                fuel,
                                vin, color, year, plate);
                        statusLabel1.setText("        Customer added");
                        statusLabel2.setText(" successfully");
                        statusLabel1.setForeground(Color.GREEN);
                        statusLabel2.setForeground(Color.GREEN);
                    } else if (result) {
                        statusLabel1.setText("                           Fill all");
                        statusLabel2.setText(" fields!!");
                        statusLabel1.setForeground(Color.RED);
                        statusLabel2.setForeground(Color.RED);
                    } else if (!result) {
                        statusLabel1.setText("                             User");
                        statusLabel2.setText(" Exists!!");
                        statusLabel1.setForeground(Color.RED);
                        statusLabel2.setForeground(Color.RED);
                    }
                }

            }
        });

        addCustomerPanel.add(new JLabel("   Name"));
        addCustomerPanel.add(nameField);
        addCustomerPanel.add(new JLabel("   Password"));
        addCustomerPanel.add(passwordField);
        addCustomerPanel.add(new JLabel("   Brand"));
        addCustomerPanel.add(brandField);
        addCustomerPanel.add(new JLabel("   Model"));
        addCustomerPanel.add(modelField);
        addCustomerPanel.add(new JLabel("   Engine"));
        addCustomerPanel.add(engineField);
        addCustomerPanel.add(new JLabel("   Fuel"));
        addCustomerPanel.add(fuelField);
        addCustomerPanel.add(new JLabel("   VIN"));
        addCustomerPanel.add(vinField);
        addCustomerPanel.add(new JLabel("   Color"));
        addCustomerPanel.add(colorField);
        addCustomerPanel.add(new JLabel("   Year"));
        addCustomerPanel.add(yearField);
        addCustomerPanel.add(new JLabel("   License Plate"));
        addCustomerPanel.add(plateField);
        addCustomerPanel.add(new JLabel(""));
        addCustomerPanel.add(addButton);
        addCustomerPanel.add(statusLabel1);
        addCustomerPanel.add(statusLabel2);

        return addCustomerPanel;
    }

    JPanel addAdminPanel() {
        JPanel addCustomerPanel = new JPanel(new GridLayout(2, 4));
        JTextField nameField = new JTextField(10);
        JTextField passwordField = new JTextField(10);
        JLabel statusLabel1 = new JLabel("");
        JLabel statusLabel2 = new JLabel("");
        JButton addButton = new JButton("Add Admin");
        TitledBorder addBorder = BorderFactory.createTitledBorder("Add Admin");
        addBorder.setTitleJustification(TitledBorder.LEFT);
        addCustomerPanel.setBorder(addBorder);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (!Character.isDigit(character)) {
                    e.consume(); // ! Ignore the input by consuming the event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used in this example
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int password = 0;
                Boolean result = true;
                if (validityCheck > -1) {

                    password = Integer.parseInt(passwordField.getText());

                    String name = nameField.getText();

                    for (Admin admin : admins) {
                        if (name.equals(admin.getUsername())) {
                            result = false;
                        }
                    }
                    if (!name.equals("") && password != 0 && result) {
                        admins = roots[validityCheck].addAdmin(admins, name, password);
                        statusLabel1.setText("              Admin added");
                        statusLabel2.setText(" successfully");
                        statusLabel1.setForeground(Color.GREEN);
                        statusLabel2.setForeground(Color.GREEN);
                    } else if (result) {
                        statusLabel1.setText("                           Fill all");
                        statusLabel2.setText(" fields!!");
                        statusLabel1.setForeground(Color.RED);
                        statusLabel2.setForeground(Color.RED);
                    } else if (!result) {
                        statusLabel1.setText("                             User");
                        statusLabel2.setText(" Exists!!");
                        statusLabel1.setForeground(Color.RED);
                        statusLabel2.setForeground(Color.RED);
                    }
                }

            }
        });

        addCustomerPanel.add(new JLabel("   Name"));
        addCustomerPanel.add(nameField);
        addCustomerPanel.add(new JLabel("   Password"));
        addCustomerPanel.add(passwordField);
        addCustomerPanel.add(new JLabel(""));
        addCustomerPanel.add(addButton);
        addCustomerPanel.add(statusLabel1);
        addCustomerPanel.add(statusLabel2);

        return addCustomerPanel;
    }

    // ! ------------------- Non-Panel Methods -------------------

    public static int checkPassword(Customer[] customers, String username, int password) {
        int i = 0;
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                if (customers[i].authintecate(username, password)) {
                    return i;

                }

            } else {
                ++i;
            }
        }

        return -1;
    }

    public int checkPassword(Users[] users, String username, int password) {
        int i = 0;
        if (users[0].equals(admins[0]) && users[1].equals(admins[1])) {
            for (Admin admin : admins) {
                if (admin.getUsername().equals(username)) {
                    if (admins[i].authintecate(username, password)) {
                        return i;

                    }

                } else {
                    ++i;
                }
            }
        } else {
            for (Root root : roots) {
                if (root.getUsername().equals(username)) {
                    if (roots[i].authintecate(username, password)) {
                        return i;

                    }

                } else {
                    ++i;
                }
            }
        }
        return -1;
    }

}
