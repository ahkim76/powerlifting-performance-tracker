package com.alexkim.powerliftingperformancetrackerv2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;


public class DatabaseImplementation {
    private static final String JDBC_URL = "jdbc:sqlite:mydatabase.db";
    private Connection connection;
    private Statement statement;

    public void connect() {
        try {
            // creating database connection
            connection = DriverManager.getConnection(JDBC_URL);
            if (connection != null) {
                System.out.println("Connected to the SQLite database: " + connection.isValid(0));
                executeSQLFile("src/main/resources/users.sql");
            }



        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the SQLite database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeSQLFile(String filePath) throws IOException, SQLException {
        String sql = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] sqlStatements = sql.split(";"); // Split by semicolon to handle multiple statements

        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(20); // 20-second timeout

            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    statement.execute(sqlStatement.trim());
                }
            }
        }
    }

    public boolean verifyUser(String username, String password) {
        String queryDataSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDataSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("hi");
                return resultSet.next(); // Return true if a matching user is found

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
