/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.base.passwordmanager;

/**
 *
 * @author DELL
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class PasswordManager extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";

    private List<PasswordEntry> passwordEntries = new ArrayList<>();
    private JTable table;

    public PasswordManager() {
        setTitle("Password Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Initialize database connection
        initializeDatabase();

        // Create UI components
        JPanel panel = createPanel();
        add(panel);

        // Set the frame to be visible
        setVisible(true);
    }

    private void initializeDatabase() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();

            // Create a table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS passwords (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "website VARCHAR(255) NOT NULL," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "google_account VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createTableSQL);

            // Fetch existing passwords from the database
            String selectQuery = "SELECT * FROM passwords";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String website = resultSet.getString("website");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String googleAccount = resultSet.getString("google_account");

                PasswordEntry entry = new PasswordEntry(id, website, username, password, googleAccount);
                passwordEntries.add(entry);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create table model
        AbstractTableModel tableModel = new PasswordTableModel(passwordEntries);


        // Create JTable
        table = new JTable(tableModel);

        // Create scroll pane and add JTable to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create buttons
        JButton addButton = new JButton("Add Password");
        JButton searchButton = new JButton("Search Passwords");
        JButton refreshButton = new JButton("Refresh Table");
        JButton deleteButton = new JButton("Delete Password");

        // Add buttons to a separate panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        // Add components to the main panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set actions for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassword();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPasswords();
            }
        });
        
         refreshButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
        });
         
         deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePassword();
            }
        });


        return panel;
    }

    private void addPassword() {
    // Implement the logic to add a password to the database and update the table
    // For example, you can use JOptionPane for user input:
    String website = JOptionPane.showInputDialog("Enter website:");
    String username = JOptionPane.showInputDialog("Enter username:");
    String password = JOptionPane.showInputDialog("Enter password:");
    String googleAccount = JOptionPane.showInputDialog("Enter Google account:");

    // Now, you need to insert this data into the database (use PreparedStatement):
    try {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        String insertQuery = "INSERT INTO passwords (website, username, password, google_account) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, website);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, googleAccount);

            preparedStatement.executeUpdate();
        }

        connection.close();

        // After adding the password, you may want to refresh the table:
        refreshTable();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void searchPasswords() {
    // Implement the logic to search passwords in the database and update the table
    // For example, you can use JOptionPane for user input:
    String searchCriteria = JOptionPane.showInputDialog("Enter website to search:");

    // Now, you need to query the database for matching entries (use PreparedStatement):
    try {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        String searchQuery = "SELECT * FROM passwords WHERE website LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, "%" + searchCriteria + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            // Clear existing entries and add the search results to the list
            passwordEntries.clear();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String website = resultSet.getString("website");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String googleAccount = resultSet.getString("google_account");

                PasswordEntry entry = new PasswordEntry(id, website, username, password, googleAccount);
                passwordEntries.add(entry);
            }

            connection.close();

            // After searching, update the table:
            updateTable();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    private void updateTable() {
    // Notify the table model that the data has changed
    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    table.clearSelection();
    
}

    private void deletePassword() {
    // Get the selected row index
    int selectedRow = table.getSelectedRow();

    if (selectedRow != -1) {
        // Get the password entry from the selected row
        PasswordEntry selectedEntry = passwordEntries.get(selectedRow);

        // Implement logic to delete the password from the database
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String deleteQuery = "DELETE FROM passwords WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, selectedEntry.getId());
                preparedStatement.executeUpdate();
            }

            connection.close();

            // After deleting the password, you may want to refresh the table:
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
private void refreshTable() {
    passwordEntries.clear();
    // Fetch all passwords from the database and update the table
    initializeDatabase();
    updateTable();
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Example: Authenticate user before launching the PasswordManager
    String username = JOptionPane.showInputDialog("Enter your username:");
    String password = JOptionPane.showInputDialog("Enter your password");

    if (UserAuthentication.authenticateUser(username, password)) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordManager();
            }
        });
    } else {
        JOptionPane.showMessageDialog(null, "Invalid username or password. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0); // Exit the application if authentication fails
    }
                new PasswordManager();
            }
        });
    }
}

class PasswordTableModel extends AbstractTableModel {
    private List<PasswordEntry> data;
    private String[] columns = {"Website", "Username", "Password", "Google Account"};

    public PasswordTableModel(List<PasswordEntry> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PasswordEntry entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return entry.getWebsite();
            case 1:
                return entry.getUsername();
            case 2:
                return entry.getPassword();
            case 3:
                return entry.getGoogleAccount();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}

class PasswordEntry {
    private int id;
    private String website;
    private String username;
    private String password;
    private String googleAccount;

    public PasswordEntry(int id, String website, String username, String password, String googleAccount) {
        this.id = id;
        this.website = website;
        this.username = username;
        this.password = password;
        this.googleAccount = googleAccount;
    }

    public int getId() {
        return id;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }
}

