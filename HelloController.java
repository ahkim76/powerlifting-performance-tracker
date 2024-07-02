package com.alexkim.powerliftingperformancetrackerv2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane focusPane;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField squatPRField;

    @FXML
    private TextField benchPRField;

    @FXML
    private TextField deadliftPRField;

    @FXML
    private TextField bodyweightField;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private Alert errorAlert;

    /*
    @FXML
    private ChoiceBox<String> genderBox;

    private String[] genders = {"Male", "Female"};*/

    @FXML
    private RadioButton maleRadioBtn;

    @FXML
    private RadioButton femaleRadioBtn;

    private ToggleGroup genderToggleGroup;

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

    public void switchToRegisterPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 711, 585);

        // Adding CSS to the scene
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();

    }

    public void switchToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 711, 585);
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    public void createAccount(ActionEvent event) throws IOException {


        // Validate inputs
        String name = firstNameField.getText();
        String firstName = name.substring(0,1).toUpperCase()+name.substring(1);
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
        }
        boolean isMale = maleRadioBtn.isSelected();

        try {
            double squatPR = Double.parseDouble(squatPRStr);
            double benchPR = Double.parseDouble(benchPRStr);
            double deadliftPR = Double.parseDouble(deadliftPRStr);
            double[] sbd = {squatPR, benchPR, deadliftPR};
            double bodyweight = Double.parseDouble(bodyweightStr);
            User newUser = new User(firstName, userID, sbd, bodyweight, isMale);
            System.out.println(newUser);
            System.out.println("~~~");
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
