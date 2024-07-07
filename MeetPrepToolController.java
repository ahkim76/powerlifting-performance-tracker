package com.alexkim.powerliftingperformancetrackerv2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.io.IOException;
import java.util.*;

public class MeetPrepToolController {
    @FXML ListView sessionListView;

    private Alert errorAlert;
    private HelloController helloController;
    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML public void initialize() {

    }

    @FXML public void switchToMeetPreparationTool(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToRecordLifts(event);
    }

    @FXML public void switchToMainMenu(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToMainMenu(event);
    }

    public void startMeetPrep(ActionEvent event) throws IOException {

    }
}
