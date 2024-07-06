package com.alexkim.powerliftingperformancetrackerv2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class RecordLiftsController {
    @FXML
    private ListView<Workout> sessionListView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField exerciseInput, setsInput, repsInput, weightInput;
    @FXML
    private Alert errorAlert;


    private Map<LocalDate, List<Workout>> workoutMap = new TreeMap<>(Collections.reverseOrder());
    private HelloController helloController;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML
    public void initialize() {
        // Set the cell factory for the ListView
        setCellFactoryForListView();
    }

    @FXML public void switchToRecordLifts(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToRecordLifts(event);
    }

    @FXML public void switchToMainMenu(ActionEvent event) throws IOException {
        //switchTo(event, "hello-view.fxml");
        if (helloController != null) helloController.switchToMainMenu(event);
    }

    public void setCellFactoryForListView() {
        if (sessionListView != null) {
            sessionListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Workout workout, boolean empty) {
                    super.updateItem(workout, empty);
                    if (empty || workout == null) {
                        setText(null);
                    } else {
                        LocalDate date = workout.getDate();
                        int index = workoutMap.get(date).indexOf(workout) + 1;
                        setText(String.format("%d. %s - %s", index, date, workout));
                    }
                }
            });
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
        try {
            int numOfSets = Integer.parseInt(setsAmount);
            int numOfReps = Integer.parseInt(repsAmount);
            double numOfWeight = Double.parseDouble(weightAmount);
            Workout workout = new Workout(workoutDate, user);
            workout.enterExercise(exerciseName, numOfSets, numOfReps, numOfWeight);

            workoutMap.putIfAbsent(workoutDate, new ArrayList<>());

            // Add the new workout to the list of workouts for workoutDate.
            workoutMap.get(workoutDate).add(workout);
            exerciseInput.clear();
            setsInput.clear();
            repsInput.clear();
            weightInput.clear();

            updateListView();
        } catch (NumberFormatException e) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input is invalid");
            errorAlert.setContentText("Please enter a number.");
            errorAlert.showAndWait();
        }

    }

    private void updateListView() {
        ObservableList<Workout> items = FXCollections.observableArrayList();

        // Iterate over the workoutMap and add all workouts to the items list.
        workoutMap.forEach((date, workouts) -> items.addAll(workouts));

        // Set the items of the ListView to the updated list.
        sessionListView.setItems(items);
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

    public void deleteLift(ActionEvent event) throws IOException {
        Workout selectedWorkout = sessionListView.getSelectionModel().getSelectedItem();

        if (selectedWorkout != null) {
            System.out.println(selectedWorkout);
        } else if (selectedWorkout == null) {
            errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("You have not selected a workout to delete");
            errorAlert.showAndWait();
        }
    }
}
