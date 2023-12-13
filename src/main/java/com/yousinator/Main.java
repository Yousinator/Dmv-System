/*
    Writer's notes
    ======================
    ! It is recommended to have the "Better Comments" extension while reading this file.
    ! Maven is used for dependency management
    ! The Database system used is SQLite
    ! If you want to add static entries to the database refer to DatabaseInitializer.java
    ! The User and Car information are all stored in dmv.db

*/

/*
    Explaining Car DMV (Department of Motor Vehicles)
    ======================
    ? 1 > This program has three different user types of users: customers, admins, and roots, with root being most privileged.
    ? 2 > Each customer owns a car while admins and roots just manage the system.
    ? 3 > The system requires username and password authentication as privileges vary and data has to be secure.
    ? 4 > All users are in object form.
    ? 5 > All user and car info are stored in the database
    ? 6 > Each user type has its own UI.
    ? 7 > This uses the card Layout and each user type has its own layout.
    ? 8 > At passwords text fields only accept numbers as input.

*/

/*
    Explaining the code
    ======================
    ! 1 > Create and fill the database (If the DB doesn't exist already).
    ! 2 > we create a recurring main menu for the user to choose from.
    ! 3 > Provide a list of all the users and create a switch for each user.
    ! 4 > Customer:
        * 5 > Check for the users's credentials if true return the customer's index else reutrn -1.
        * 6 > If number is greater than -1 display the info for the customer.
    ! 7 > Admin:
        * 8 > Check for the users's credentials if true return the user's index.
        * 9 > If number is greater than -1 let him choose from the admin menu.
        * 10 > If admin chooses searching:
            ? 11 > Let the admin choose from the customers.
            ? 12 > Repeat 6.
        * 13 > If admin chooses change:
            ? 13 > Let the admin choose from customer.
            ? 14 > After choosing the customer, let the admin choose the attribute to be changed.
            ? 15 > Change the attribute based on the admin's input.
        * 16 > If the admin chooses add customer:
            ? 17 > Prompt the user to add customer attributes.
            ? 18 > The database modified using a function that changes the attribute using a query.
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.yousinator.car.Car;
import com.yousinator.users.*;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public int validityCheck = 2;
    public int userChoice = -1;

    // !--------------------- Main and App --------------------------------

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
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
        mainPanel.setBackground(Color.darkGray); // Set main panel background to dark gray

        // Create the screens with a dark theme
        JPanel mainMenu = mainMenuPanel();
        stylePanel(mainMenu, Color.darkGray, Color.white); // Style panel for dark theme

        JPanel customerPanel = customerPanel();
        stylePanel(customerPanel, Color.darkGray, Color.white);

        JPanel adminPanel = adminPanel();
        stylePanel(adminPanel, Color.darkGray, Color.white);

        JPanel rootPanel = rootPanel();
        stylePanel(rootPanel, Color.darkGray, Color.white);

        // Wrapper panels
        JPanel mainMenuWrapper = createWrapperPanel(mainMenu, 400, 200);
        JPanel customerWrapper = createWrapperPanel(customerPanel, 500, 400);
        JPanel adminWrapper = createWrapperPanel(adminPanel, 500, 600);
        JPanel rootWrapper = createWrapperPanel(rootPanel, 500, 680);

        mainPanel.add(mainMenuWrapper, "Main Menu");
        mainPanel.add(customerWrapper, "Customer");
        mainPanel.add(adminWrapper, "Admin");
        mainPanel.add(rootWrapper, "Root");

        add(mainPanel); // Add the mainPanel to the JFrame
        setVisible(true);
    }

    private void stylePanel(JPanel panel, Color bgColor, Color fgColor) {
        panel.setBackground(bgColor);
        for (Component comp : panel.getComponents()) {
            comp.setBackground(bgColor);
            comp.setForeground(fgColor);

            // Style buttons
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(Color.gray); // Dark background for buttons
                button.setForeground(Color.white); // Light text
                button.setFocusPainted(false); // Optional: removes focus outline
            }

            // Style text fields
            if (comp instanceof JTextField) {
                JTextField textField = (JTextField) comp;
                textField.setBackground(Color.darkGray); // Dark background for text fields
                textField.setForeground(Color.white); // Light text
                textField.setCaretColor(Color.white); // Cursor color in text fields
            }

            // Style panels and apply recursively
            if (comp instanceof JPanel) {
                stylePanel((JPanel) comp, bgColor, fgColor);
            }

            // Style bordered components
            if (comp instanceof JComponent && ((JComponent) comp).getBorder() instanceof TitledBorder) {
                TitledBorder border = (TitledBorder) ((JComponent) comp).getBorder();
                border.setTitleColor(fgColor); // Set title color of the border
            }
        }
    }

    private JPanel createWrapperPanel(JPanel innerPanel, int width, int height) {
        JPanel wrapper = new JPanel();
        wrapper.setPreferredSize(new Dimension(width, height));
        wrapper.add(innerPanel);
        wrapper.setBackground(Color.darkGray); // Set wrapper background to dark gray
        return wrapper;
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
        JPanel loginPanel = userLogin("customer");
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
        JPanel loginPanel = userLogin("admin");
        JPanel chooserPanel = chooseCustomer();
        JPanel searchPanel = searchCustomer();
        JPanel changePanel = changeCustomer();
        JPanel addCustomerPanel = addCustomer();
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
        JPanel loginPanel = userLogin("root");
        JPanel choserPanel = chooseCustomer();
        JPanel searchPanel = searchCustomer();
        JPanel changePanel = changeCustomer();
        JPanel headPanel = new JPanel(new BorderLayout()), middlePanel = new JPanel(new BorderLayout()),
                tailPanel = new JPanel(new BorderLayout());
        JPanel addCustomerPanel = addCustomer();
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

    private JPanel userLogin(String userType) {
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
                String username = loginField.getText();
                String password = passwordField.getText(); // Assuming password is stored as an integer

                // Check password against the database for 'admin' userType
                int adminId = checkPassword(username, password, userType);
                if (adminId == -1) {
                    passwordNotificationLabel.setText("Wrong Username or Password");
                    passwordNotificationLabel.setForeground(Color.RED);
                } else {
                    passwordNotificationLabel.setText("Welcome Back " + username);
                    validityCheck = 2;
                    passwordNotificationLabel.setForeground(Color.BLACK);
                    userChoice = adminId; // Store admin ID
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

        List<String> customerUsernames = Customer.fetchAllCustomerUsernames();
        for (String name : customerUsernames) {
            modelSearchBox.addElement(name);
        }
        searchBox.setModel(modelSearchBox);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelSearchBox.removeAllElements();
                List<String> customerUsernames = Customer.fetchAllCustomerUsernames(); // Fetch usernames from database
                for (String username : customerUsernames) {
                    modelSearchBox.addElement(username);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = (String) searchBox.getSelectedItem();
                userChoice = Customer.fetchCustomerIdByUsername(selectedUsername); // Fetch customer ID from database
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
                String searchCriteria = (String) searchBox.getSelectedItem();
                if (userChoice > -1) { // Assuming userChoice stores the customer's ID
                    String result = Customer.getCarInfo(userChoice, searchCriteria); // Implement this method in
                                                                                     // Customer class
                    resultField.setText(result);
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
                String attributeToChange = (String) searchBox.getSelectedItem();
                String newValue = changeField.getText();

                if (userChoice > -1) { // Assuming userChoice holds the customer's ID
                    Car.updateCarDetails(userChoice, attributeToChange, newValue);
                    // Show update status to the user, possibly using a status label
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

    JPanel addCustomer() {
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
                    List<String> customerUsernames = Customer.fetchAllCustomerUsernames();

                    for (String username : customerUsernames) {
                        if (name.equals(username)) {
                            result = false;
                        }
                    }

                    if (!name.equals("") && !brand.equals("") && !model.equals("")
                            && !engine.equals("") && !fuel.equals("") && !vin.equals("") && result
                            && !color.equals("") && !year.equals("") && !plate.equals("") && password != 0) {

                        boolean added = Customer.addNewCustomerWithCar(name, password, brand, model, engine, fuel, vin,
                                color, year, plate);
                        if (added) {
                            statusLabel1.setText("        Customer added");
                            statusLabel2.setText(" successfully");
                            statusLabel1.setForeground(Color.GREEN);
                            statusLabel2.setForeground(Color.GREEN);
                        } else if (!added) {
                            statusLabel1.setText("                           Unknown");
                            statusLabel2.setText(" Error");
                            statusLabel1.setForeground(Color.RED);
                            statusLabel2.setForeground(Color.RED);
                        }
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

                    List<String> customerUsernames = Root.fetchAllAdminUsernames();

                    for (String username : customerUsernames) {
                        if (name.equals(username)) {
                            result = false;
                        }
                    }
                    if (!name.equals("") && password != 0 && result) {
                        boolean addResult = Root.addNewAdmin(name, password);
                        if (addResult) {
                            statusLabel1.setText("              Admin added");
                            statusLabel2.setText(" successfully");
                            statusLabel1.setForeground(Color.GREEN);
                            statusLabel2.setForeground(Color.GREEN);
                        } else {
                            statusLabel1.setText("                           Unknown");
                            statusLabel2.setText(" Error");
                            statusLabel1.setForeground(Color.RED);
                            statusLabel2.setForeground(Color.RED);
                        }

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

    public int checkPassword(String username, String password, String userType) {
        // Example SQL query
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND userType = ?";

        try (Connection conn = DatabaseInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, userType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // Return user ID if authentication succeeds
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Return -1 if authentication fails
    }

}
