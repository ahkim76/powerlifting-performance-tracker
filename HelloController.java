package com.alexkim.powerliftingperformancetrackerv2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.Objects;
import javafx.scene.image.Image;

public class HelloController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane focusPane;

    @FXML
    private CheckBox femaleCheckBox;

    @FXML
    private CheckBox maleCheckBox;

    @FXML
    private AnchorPane registerPane;

    /*
    @FXML
    private RadioButton maleRadioBtn;

    @FXML
    private RadioButton femaleRadioBtn;

    private ToggleGroup genderToggleGroup; */

    @FXML
    public void initialize() {
/*
        genderToggleGroup = new ToggleGroup();
        maleRadioBtn = new RadioButton();
        femaleRadioBtn = new RadioButton();
        maleRadioBtn.setToggleGroup(genderToggleGroup);
        femaleRadioBtn.setToggleGroup(genderToggleGroup);
        genderToggleGroup.selectToggle(maleRadioBtn);*/



        maleCheckBox = new CheckBox();
        femaleCheckBox = new CheckBox();
        maleCheckBox.setOnAction(event -> {
            if (maleCheckBox.isSelected()) {
                femaleCheckBox.setSelected(false);
            } else {
                maleCheckBox.setSelected(true); // Ensure at least one is selected
            }
        });

        femaleCheckBox.setOnAction(event -> {
            if (femaleCheckBox.isSelected()) {
                maleCheckBox.setSelected(false);
            } else {
                femaleCheckBox.setSelected(true); // Ensure at least one is selected
            }
        });
    }


    public void switchToLoginPage(ActionEvent event) throws IOException {
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
}
