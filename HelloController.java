package com.alexkim.powerliftingperformancetrackerv2;

import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import javafx.util.Duration;
import java.util.Objects;

public class HelloController {
    private static HelloController instance;

    public static HelloController getInstance() {
        return instance;
    }

    public HelloController() {
        instance = this;
    }

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane focusPane;
    @FXML
    private TextField firstNameField, usernameField, passwordField;
    @FXML
    private TextField squatPRField, benchPRField, deadliftPRField, bodyweightField;
    @FXML
    private TextField weightLifted, repsPerformed;
    @FXML
    private TextField exerciseInput, weightInput, setsInput, repsInput;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label resultLabel, recentActivityLabel;
    @FXML
    private AnchorPane registerPane;
    @FXML
    private Alert errorAlert;
    @FXML
    private RadioButton maleRadioBtn;
    @FXML
    private RadioButton femaleRadioBtn;
    private ToggleGroup genderToggleGroup;
    @FXML
    private Button button;
    // private boolean registered = false;
    private HelloController helloController;

    private Queue<String> recentActivityQueue = new LinkedList<>();

    @FXML
    public void initialize() {
        /*
        genderToggleGroup = new ToggleGroup();
        maleRadioBtn.setToggleGroup(genderToggleGroup);
        femaleRadioBtn.setToggleGroup(genderToggleGroup);*/
    }

    public void setToggle(ActionEvent event) throws IOException {
        genderToggleGroup = new ToggleGroup();
        maleRadioBtn.setToggleGroup(genderToggleGroup);
        femaleRadioBtn.setToggleGroup(genderToggleGroup);
    }

    @FXML
    public void showRecentActivity() {
        if (recentActivityQueue.isEmpty()) return;
        else {
            recentActivityLabel.setText(recentActivityQueue.poll());
        }
    }

    // ALL SWITCHING SCREEN METHODS //


    private void switchTo(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = fxmlLoader.load();
            scene = new Scene(root, 711, 585);
            String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
            if (fxmlFile.equals("MainMenu.fxml")) {
                MainMenuController mainMenuController = fxmlLoader.getController();
                mainMenuController.setHelloController(this);
                mainMenuController.setUserInformation();
                /*
                Button mainMenuButton = (Button) scene.lookup("#mainMenuButton");
                if (mainMenuButton != null) {
                    // Create the animation timeline
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(mainMenuButton.styleProperty(), "-fx-background-position: 0 0;")),
                            new KeyFrame(Duration.seconds(2), new KeyValue(mainMenuButton.styleProperty(), "-fx-background-position: 200px 0;"))
                    );
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.setAutoReverse(false);

                    mainMenuButton.setOnMouseEntered(e -> timeline.play());
                    mainMenuButton.setOnMouseExited(e -> timeline.stop());
                }*/


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToRegisterPage(ActionEvent event) throws IOException {
        switchTo(event, "Register.fxml");
    }

    @FXML
    public void switchToLoginPage(ActionEvent event) throws IOException {
        switchTo(event, "Login.fxml");
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        switchTo(event, "MainMenu.fxml");

    }

    @FXML
    public void switchToMeetPreparationTool(ActionEvent event) throws IOException {
        switchTo(event, "MeetPreparationTool.fxml");
    }

    @FXML
    public void switchToStarterScreen(ActionEvent event) throws IOException {
        System.out.println("Switching to Starter Screen!");
        switchTo(event, "hello-view.fxml");
        //if (helloController != null) helloController.switchToStarterScreen(event);
    }

    @FXML
    public void switchToOneRepMaxCalculator(ActionEvent event) throws IOException {
        switchTo(event, "OneRepMaxCalculator.fxml");
    }

    @FXML
    public void switchToPersonalCoach(ActionEvent event) throws IOException {
        switchTo(event, "PersonalCoach.fxml");
    }

    @FXML
    public void switchToRecordLifts(ActionEvent event) throws IOException {
        switchTo(event, "RecordLifts.fxml");
    }

    @FXML
    public void switchToViewProgress(ActionEvent event) throws IOException {
        switchTo(event, "ViewProgress.fxml");
    }

    // END OF SWITCH SCREEN METHODS //


    @FXML
    public void createAccount(ActionEvent event) throws IOException {
        // Validate inputs
        String name = firstNameField.getText();
        String userID = usernameField.getText();
        String password = passwordField.getText();
        String squatPRStr = squatPRField.getText();
        String benchPRStr = benchPRField.getText();
        String deadliftPRStr = deadliftPRField.getText();
        String bodyweightStr = bodyweightField.getText();

        checkEmptyField(name);
        checkEmptyField(userID);
        checkEmptyField(password);
        checkEmptyField(squatPRStr);
        checkEmptyField(benchPRStr);
        checkEmptyField(deadliftPRStr);
        checkEmptyField(bodyweightStr);
        if (!maleRadioBtn.isSelected() && !femaleRadioBtn.isSelected()) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Please selected a gender");
            errorAlert.showAndWait();
            return;
        }
        boolean isMale = maleRadioBtn.isSelected();

        try {
            double squatPR = Double.parseDouble(squatPRStr);
            double benchPR = Double.parseDouble(benchPRStr);
            double deadliftPR = Double.parseDouble(deadliftPRStr);
            double[] sbd = {squatPR, benchPR, deadliftPR};
            double bodyweight = Double.parseDouble(bodyweightStr);
            String firstName = name.substring(0, 1).toUpperCase() + name.substring(1);

            User newUser = new User(firstName, userID, sbd, bodyweight, isMale);
            System.out.println(newUser);
            System.out.println("~~~");
            UserSession.getInstance().setCurrentUser(newUser);

            // IT WORKS!!!!!!

            switchToMainMenu(event);

            PowerliftingPerformanceTracker pl = new PowerliftingPerformanceTracker();
            pl.run(newUser);

        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input is invalid");
            errorAlert.setContentText("Name must be a valid String. PRs and bodyweight must be valid numbers.");
            errorAlert.showAndWait();
        }

    }

    private void checkEmptyField(String str) {
        if (str == null || str.trim().isEmpty()) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input is invalid");
            errorAlert.setContentText("You have either not entered all the fields.");
            errorAlert.showAndWait();
            return;
        }
    }

    // ONE REP MAX FUNCTIONALITY
    public void calculateOneRepMax(ActionEvent event) throws IOException {
        //User user = UserSession.getInstance().getCurrentUser();
        String weightText = weightLifted.getText();
        String repsText = repsPerformed.getText();
        checkEmptyField(weightText);
        checkEmptyField(repsText);
        try {
            double weight = Double.parseDouble(weightText);
            int reps = Integer.parseInt(repsText);
            OneRepMaxCalculator oneRepMax = new OneRepMaxCalculator(weight, reps);
            String max = String.valueOf(oneRepMax.findMax());
            resultLabel.setText("Your one rep max likely is: " + max + "lbs");
            recentActivityQueue.add(oneRepMax.toString());

        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input is invalid");
            errorAlert.setContentText("Please enter a number.");
            errorAlert.showAndWait();
        }

    }

// RECORD LIFTS FUNCTIONALITY

    public void addLift(ActionEvent event) throws IOException {
        User user = UserSession.getInstance().getCurrentUser();
        LocalDate workoutDate = datePicker.getValue();
        String exerciseName = exerciseInput.getText();
        String setsAmount = setsInput.getText();
        String repsAmount = repsInput.getText();
        String weightAmount = weightInput.getText();
        checkEmptyField(exerciseName);
        checkEmptyField(setsAmount);
        checkEmptyField(repsAmount);
        checkEmptyField(weightAmount);
        if(datePicker.getValue() == null) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Please enter a workout date");
            errorAlert.showAndWait();
            return;
        }
        int numOfSets = Integer.parseInt(setsAmount);
        int numOfReps = Integer.parseInt(repsAmount);
        double numOfWeight = Double.parseDouble(weightAmount);
        Workout workout = new Workout(workoutDate, user);
        workout.enterExercise(exerciseName, numOfSets, numOfReps, numOfWeight);
        exerciseInput.clear();
        setsInput.clear();
        repsInput.clear();
        weightInput.clear();
    }

}
