package com.lertos.javafxitemgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetupData {

    private List<String> itemTypes;
    private List<String> rarities;
    private List<String> classes;
    private List<String> equipmentSlots;

    public SetupData() {
        setupItemTypes();
        setupRarities();
        setupClasses();
        setupEquipmentSlots();
    }

    public List<String> getItemTypes() {
        return Collections.unmodifiableList(itemTypes);
    }

    public List<String> getRarities() {
        return Collections.unmodifiableList(rarities);
    }

    public List<String> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<String> getEquipmentSlots() {
        return Collections.unmodifiableList(equipmentSlots);
    }

    private void setupItemTypes() {
        itemTypes = new ArrayList<>();

        itemTypes.add("base");
        itemTypes.add("weapon");
        itemTypes.add("armor");
        itemTypes.add("quest");
        itemTypes.add("unique");
        itemTypes.add("junk");
    }

    private void setupRarities() {
        rarities = new ArrayList<>();

        rarities.add("common");
        rarities.add("uncommon");
        rarities.add("rare");
        rarities.add("legendary");
    }

    private void setupClasses() {
        classes = new ArrayList<>();

        classes.add("warrior");
        classes.add("archer");
        classes.add("mage");
    }

    private void setupEquipmentSlots() {
        equipmentSlots = new ArrayList<>();

        equipmentSlots.add("helmet");
        equipmentSlots.add("chest");
        equipmentSlots.add("legs");
        equipmentSlots.add("gloves");
        equipmentSlots.add("boots");
    }

}
