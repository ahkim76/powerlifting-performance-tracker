package com.alexkim.powerliftingperformancetrackerv2;

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

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private Pane focusPane;
    @FXML private TextField firstNameField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField squatPRField;
    @FXML private TextField benchPRField;
    @FXML private TextField deadliftPRField;
    @FXML private TextField bodyweightField;
    @FXML private AnchorPane registerPane;
    @FXML private Alert errorAlert;
    @FXML private RadioButton maleRadioBtn;
    @FXML private RadioButton femaleRadioBtn;
    private ToggleGroup genderToggleGroup;
   // private boolean registered = false;
private HelloController helloController;

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

    // ALL SWITCHING SCREEN METHODS //


    private void switchTo(ActionEvent event, String fxmlFile) {
        switchTo(event, fxmlFile, null);
    }
    private void switchTo(ActionEvent event, String fxmlFile, User newUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = fxmlLoader.load();
            scene = new Scene(root, 711, 585);
            String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
            if(fxmlFile.equals("MainMenu.fxml") && newUser != null) {
                MainMenuController mainMenuController = fxmlLoader.getController();
                mainMenuController.setUserInformation(newUser);
                mainMenuController.setHelloController(this);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML public void switchToRegisterPage(ActionEvent event) throws IOException {
        switchTo(event, "Register.fxml");
    }

    @FXML public void switchToLoginPage(ActionEvent event) throws IOException {
        switchTo(event, "Login.fxml");
    }

    @FXML public void switchToMainMenu(ActionEvent event, User newUser) throws IOException {
        switchTo(event, "MainMenu.fxml", newUser);
    }

    @FXML public void switchToMeetPreparationTool(ActionEvent event) throws IOException {
        switchTo(event, "MeetPreparationTool.fxml");
    }

    @FXML public void switchToStarterScreen(ActionEvent event) throws IOException {
        switchTo(event, "hello-view.fxml");
        //if (helloController != null) helloController.switchToStarterScreen(event);
    }

    @FXML public void switchToOneRepMaxCalculator(ActionEvent event) throws IOException {
        switchTo(event, "OneRepMaxCalculator.fxml");
    }

    @FXML public void switchToPersonalCoach(ActionEvent event) throws IOException {
        switchTo(event, "PersonalCoach.fxml");
    }

    @FXML public void switchToRecordLifts(ActionEvent event) throws IOException {
        switchTo(event, "RecordLifts.fxml");
    }

    @FXML public void switchToViewProgress(ActionEvent event) throws IOException {
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
            String firstName = name.substring(0,1).toUpperCase()+name.substring(1);

            User newUser = new User(firstName, userID, sbd, bodyweight, isMale);
            System.out.println(newUser);
            System.out.println("~~~");

            // IT WORKS!!!!!!

            switchToMainMenu(event, newUser);

            PowerliftingPerformanceTracker pl = new PowerliftingPerformanceTracker();
            //pl.run(newUser);

        } catch(NumberFormatException e) {
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




}
