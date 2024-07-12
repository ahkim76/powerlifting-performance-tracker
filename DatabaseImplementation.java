package com.alexkim.powerliftingperformancetrackerv2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseImplementation {
    private static final String JDBC_URL = "jdbc:sqlite:mydatabase.db";
    private Connection connection;
    private PreparedStatement statement;

    public void connect() {
        try {
            // creating database connection
            connection = DriverManager.getConnection(JDBC_URL);
            if (connection != null) {
                System.out.println("Connected to the SQLite database: " + connection.isValid(0));
                executeSQLFile("src/main/resources/users.sql");

                // Debugging: Verify connection with a simple query
                String testQuery = "SELECT 1";
                try (PreparedStatement ps = connection.prepareStatement(testQuery)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("Test query executed successfully.");
                        }
                    }
                }
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
                    //System.out.println("Executing: " + sqlStatement.trim());
                    statement.executeUpdate(sqlStatement.trim());
                }
            }
        }
    }



    public User verifyUser(String username, String password) {
        String queryDataSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String pass = resultSet.getString("password");
                    double squat = resultSet.getDouble("squat");
                    double bench = resultSet.getDouble("bench");
                    double deadlift = resultSet.getDouble("deadlift");
                    double bodyweight = resultSet.getDouble("bodyweight");
                    int gender = resultSet.getInt("gender");
                    boolean isMale = gender == 1;

                    double[] sbd = {squat, bench, deadlift};
                    return new User(name, username, pass, sbd, bodyweight, isMale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public void deleteUser(String username) {
        String queryDataSQL = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(queryDataSQL)) {
            ps.setString(1, username);
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

    public int addWorkout(String username, LocalDate date) {
        String query = "INSERT INTO workouts (username, date) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, date.toString());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated workout ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addExercise(int workoutId, String name, int sets, int reps, double weight) {
        String query = "INSERT INTO exercises (workout_id, name, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, workoutId);
            ps.setString(2, name);
            ps.setInt(3, sets);
            ps.setInt(4, reps);
            ps.setDouble(5, weight);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExercises(int workoutId) {
        String query = "DELETE FROM exercises WHERE workout_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, workoutId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkout(int workoutId) {
        // First, delete the exercises associated with the workout
        deleteExercises(workoutId);

        // Then, delete the workout itself
        String query = "DELETE FROM workouts WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, workoutId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Workout> getWorkouts(String username) {
        List<Workout> workouts = new ArrayList<>();
        String workoutQuery = "SELECT * FROM workouts WHERE username = ?";
        String exerciseQuery = "SELECT * FROM exercises WHERE workout_id = ?";

        try (PreparedStatement workoutPs = connection.prepareStatement(workoutQuery)) {
            workoutPs.setString(1, username);
            try (ResultSet workoutRs = workoutPs.executeQuery()) {
                while (workoutRs.next()) {
                    int workoutId = workoutRs.getInt("id");
                    LocalDate date = LocalDate.parse(workoutRs.getString("date"));
                    User user = getUserByUsername(username); // Retrieve the User object by username
                    Workout workout = new Workout(date, user);
                    workout.setId(workoutId);

                    try (PreparedStatement exercisePs = connection.prepareStatement(exerciseQuery)) {
                        exercisePs.setInt(1, workoutId);
                        try (ResultSet exerciseRs = exercisePs.executeQuery()) {
                            while (exerciseRs.next()) {
                                String name = exerciseRs.getString("name");
                                int sets = exerciseRs.getInt("sets");
                                int reps = exerciseRs.getInt("reps");
                                double weight = exerciseRs.getDouble("weight");
                                Exercise exercise = new Exercise(name, sets, reps, weight);
                                workout.addExercise(exercise);
                            }
                        }
                    }
                    workouts.add(workout);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workouts;
    }

    private User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("username");
                    String password = rs.getString("password");
                    double squat = rs.getDouble("squat");
                    double bench = rs.getDouble("bench");
                    double deadlift = rs.getDouble("deadlift");
                    double bodyweight = rs.getDouble("bodyweight");
                    boolean isMale = rs.getInt("gender") == 1;
                    double[] sbd = {squat, bench, deadlift};
                    return new User(name, username, password, sbd, bodyweight, isMale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

