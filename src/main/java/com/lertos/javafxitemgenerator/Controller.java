package com.lertos.javafxitemgenerator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Button btnSave;

    @FXML
    protected void onSaveButtonClick() {
        System.out.println("Test");
    }
}