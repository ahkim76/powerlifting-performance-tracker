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
public class MainMenuController {
    @FXML private Label squatLabel, benchLabel, deadliftLabel, bodyweightLabel;
    private HelloController helloController;
    public void setUserInformation(User user) {
        squatLabel.setText("Squat PR: " + user.getSquatPR()+"lbs");
        benchLabel.setText("Bench PR: " + user.getBenchPR()+"lbs");
        deadliftLabel.setText("Deadlift PR: " + user.getDeadliftPR()+"lbs");
        bodyweightLabel.setText("Bodyweight: " + user.getBodyweight()+"lbs");

    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML public void switchToStarterScreen(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToStarterScreen(event);
    }

    @FXML public void switchToRecordLifts(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToRecordLifts(event);
    }

    @FXML public void switchToMeetPreparationTool(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToMeetPreparationTool(event);
    }

    @FXML public void switchToOneRepMaxCalculator(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToOneRepMaxCalculator(event);
    }

    @FXML public void switchToViewProgress(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToViewProgress(event);
    }

    @FXML public void switchToPersonalCoach(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToPersonalCoach(event);
    }

}

