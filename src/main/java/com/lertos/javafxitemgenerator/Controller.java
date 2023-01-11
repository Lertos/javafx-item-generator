package com.lertos.javafxitemgenerator;

import com.lertos.javafxitemgenerator.model.SetupData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private ChoiceBox cbItemTypes;
    @FXML
    private ChoiceBox cbRarities;
    @FXML
    private ChoiceBox cbWeaponClasses;
    @FXML
    private ChoiceBox cbArmorClasses;
    @FXML
    private ChoiceBox cbEquipmentSlots;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDelete;

    public void loadSetupData() {
        SetupData setupData = new SetupData();

        cbItemTypes.getItems().addAll(setupData.getItemTypes());
        cbRarities.getItems().addAll(setupData.getRarities());
        cbWeaponClasses.getItems().addAll(setupData.getClasses());
        cbArmorClasses.getItems().addAll(setupData.getClasses());
        cbEquipmentSlots.getItems().addAll(setupData.getEquipmentSlots());

        cbItemTypes.getSelectionModel().select(0);
        cbRarities.getSelectionModel().select(0);
        cbWeaponClasses.getSelectionModel().select(0);
        cbArmorClasses.getSelectionModel().select(0);
        cbEquipmentSlots.getSelectionModel().select(0);
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