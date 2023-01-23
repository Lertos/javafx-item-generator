package com.lertos.javafxitemgenerator;

import com.lertos.javafxitemgenerator.model.SetupData;
import com.lertos.javafxitemgenerator.model.enums.ItemTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField tfItemId;
    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfLevelReqWeapon;
    @FXML
    private TextField tfDmgMin;
    @FXML
    private TextField tfDmgMax;
    @FXML
    private ChoiceBox cbItemTypes;
    @FXML
    private ChoiceBox cbWeaponClasses;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDelete;

    public void loadSetupData() {
        SetupData setupData = new SetupData();

        cbItemTypes.getItems().addAll(setupData.getItemTypes());
        cbWeaponClasses.getItems().addAll(setupData.getClasses());

        cbItemTypes.getSelectionModel().select(0);
        cbWeaponClasses.getSelectionModel().select(0);
    }

    @FXML
    protected void onSaveButtonClick() {
        System.out.println("Test Save");
    }

    @FXML
    protected void onClearButtonClick() {
        System.out.println("Test Clear");
    }

    @FXML
    protected void onDeleteButtonClick() {
        System.out.println("Test Delete");
    }
}