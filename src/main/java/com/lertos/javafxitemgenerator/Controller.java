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

    public void loadSetupData() {
        SetupData setupData = new SetupData();

        cbItemTypes.getItems().addAll(setupData.getItemTypes());
        cbRarities.getItems().addAll(setupData.getRarities());
        cbWeaponClasses.getItems().addAll(setupData.getClasses());
        cbArmorClasses.getItems().addAll(setupData.getClasses());
        cbEquipmentSlots.getItems().addAll(setupData.getEquipmentSlots());
    }

    @FXML
    protected void onSaveButtonClick() {
        System.out.println("Test");
    }
}