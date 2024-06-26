package com.alexkim.powerliftingperformancetrackerv2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // JAVAFX STUFF
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Powerlifting Performance Tracker");
        stage.setScene(scene);
        stage.show();


        // Start any additional components after the UI is visible
        PowerliftingPerformanceTracker pl = new PowerliftingPerformanceTracker();
        pl.run(); // Consider using Platform.runLater if this needs to interact with the UI
        //Platform.runLater(() -> {
          //  PowerliftingPerformanceTracker pl = new PowerliftingPerformanceTracker();
            //pl.run();
        //});
    }

    public static void main(String[] args) {
        launch();
    }
}
