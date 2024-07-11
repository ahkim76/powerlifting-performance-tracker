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
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                System.out.println("hi");
                return resultSet.next(); // Return true if a matching user is found

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addUser(String username, String password, double[] sbd, double bodyweight, int gender) {
        String queryDataSQL = "INSERT INTO users (username, password, squat, bench, deadlift, bodyweight, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setDouble(3, sbd[0]);
            ps.setDouble(4, sbd[1]);
            ps.setDouble(5, sbd[2]);
            ps.setDouble(6, bodyweight);
            ps.setInt(7, gender);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printDatabase() {
        String queryDataSQL = "SELECT * FROM users";
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double squat = resultSet.getDouble("squat");
                double bench = resultSet.getDouble("bench");
                double deadlift = resultSet.getDouble("deadlift");
                double bodyweight = resultSet.getDouble("bodyweight");
                int gender = resultSet.getInt("gender");

                System.out.printf("ID: %d, Username: %s, Password: %s, Squat: %.2f, Bench: %.2f, Deadlift: %.2f, Bodyweight: %.2f, Gender: %d%n",
                        id, username, password, squat, bench, deadlift, bodyweight, gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String username, Double squat, Double bench, Double deadlift, Double bodyweight) {
        StringBuilder queryDataSQL = new StringBuilder("UPDATE users SET ");
        boolean first = true;
        if (squat != null) {
            queryDataSQL.append("squat = ?");
            first = false;
        }
        if (bench != null) {
            if (!first) queryDataSQL.append(", ");
            queryDataSQL.append("bench = ?");
            first = false;
        }
        if (deadlift != null) {
            if (!first) queryDataSQL.append(", ");
            queryDataSQL.append("deadlift = ?");
            first = false;
        }
        if (bodyweight != null) {
            if (!first) queryDataSQL.append(", ");
            queryDataSQL.append("bodyweight = ?");
            first = false;
        }

        queryDataSQL.append(" WHERE username = ?");
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL.toString())) {
            int index = 1;
            if(squat != null) ps.setDouble(index++, squat);
            if(bench != null) ps.setDouble(index++, bench);
            if(deadlift != null) ps.setDouble(index++, deadlift);
            if(bodyweight != null) ps.setDouble(index++, bodyweight);
            ps.setString(index, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
