package com.lertos.javafxitemgenerator;

import com.lertos.javafxitemgenerator.model.Datasource;
import com.lertos.javafxitemgenerator.model.Item;
import com.lertos.javafxitemgenerator.model.SetupData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    @FXML
    private TextField tfItemId;
    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfDescription;
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
    private TableView itemTable;

    public void listItems() {
        Task<ObservableList<Item>> task = new GetAllItemsTask();
        itemTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    public void loadSetupData() {
        SetupData setupData = new SetupData();

        cbItemTypes.getItems().addAll(setupData.getItemTypes());
        cbWeaponClasses.getItems().addAll(setupData.getClasses());

        cbItemTypes.getSelectionModel().select(0);
        cbWeaponClasses.getSelectionModel().select(0);
    }

    @FXML
    protected void onSaveButtonClick() {
        String type = cbItemTypes.getValue().toString();
        String errorMessage = validateBaseInfo();

        if (errorMessage.isEmpty() && type.equalsIgnoreCase("WEAPON"))
            errorMessage = validateWeaponInfo();

        if (!errorMessage.isEmpty()) {
            showDialog(errorMessage);
            return;
        }

        Item item = new Item();

        item.setId(tfItemId.getText());
        item.setName(tfItemName.getText());
        item.setType(cbItemTypes.getValue().toString());
        item.setDescription(tfDescription.getText());

        if (type.equalsIgnoreCase("WEAPON")) {
            item.setClassReq(cbWeaponClasses.getValue().toString());
            item.setLevelReq(Integer.parseInt(tfLevelReqWeapon.getText()));
            item.setDmgMin(Integer.parseInt(tfDmgMin.getText()));
            item.setDmgMax(Integer.parseInt(tfDmgMax.getText()));
        }

        try {
            Datasource.getInstance().insertNewItem(item);
        } catch (SQLException e) {
            showDialog("ERROR - Record could not be added:\n\n" + e.getMessage());
            return;
        }
        showDialog("Your item has been successfully added");

        //Clear all fields
        onClearButtonClick();
        //Repopulate the item table list
        listItems();
    }

    public static void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        ButtonType buttonType = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.showAndWait();
    }

    private String validateBaseInfo() {
        String errorMessage = "";

        if (tfItemId.getText().isEmpty())
            errorMessage = "The Item ID field must contain a value";
        else if (tfItemName.getText().isEmpty())
            errorMessage = "The Item Name field must contain a value";
        else if (tfDescription.getText().isEmpty())
            errorMessage = "The Description field must contain a value";

        return errorMessage;
    }

    private String validateWeaponInfo() {
        String errorMessage = "";

        if (tfLevelReqWeapon.getText().isEmpty())
            errorMessage = "The Level Req field must contain a value";
        else if (tfDmgMin.getText().isEmpty())
            errorMessage = "The Dmg Min field must contain a value";
        else if (tfDmgMax.getText().isEmpty())
            errorMessage = "The Dmg Max field must contain a value";

        if (!errorMessage.isEmpty())
            return errorMessage;

        if (!isValidInt(tfLevelReqWeapon.getText()))
            errorMessage = "The Level Req field must contain a valid integer";
        else if (!isValidInt(tfDmgMin.getText()))
            errorMessage = "The Dmg Min field must contain a valid integer";
        else if (!isValidInt(tfDmgMax.getText()))
            errorMessage = "The Dmg Max field must contain a valid integer";

        return errorMessage;
    }

    private boolean isValidInt(String stringToCheck) {
        try {
            Integer.parseInt(stringToCheck);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @FXML
    protected void onClearButtonClick() {
        //Chose not to reset dropdowns as they will most likely want to add multiple of the same type at the same time
        tfItemId.setText("");
        tfItemName.setText("");
        tfDescription.setText("");

        tfLevelReqWeapon.setText("");
        tfDmgMin.setText("");
        tfDmgMax.setText("");
        tfItemId.setText("");
    }

    @FXML
    protected void onDeleteButtonClick() {
        List<Item> items = Datasource.getInstance().queryAllItems();

        for (Item item : items)
            System.out.println(item.toString());

        System.out.println("Test Delete");
    }
}

class GetAllItemsTask extends Task {
    @Override
    public ObservableList<Item> call()  {
        return FXCollections.observableArrayList(Datasource.getInstance().queryAllItems());
    }
}