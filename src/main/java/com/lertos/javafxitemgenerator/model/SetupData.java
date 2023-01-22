package com.lertos.javafxitemgenerator.model;

import com.lertos.javafxitemgenerator.model.enums.Classes;
import com.lertos.javafxitemgenerator.model.enums.EquipmentSlots;
import com.lertos.javafxitemgenerator.model.enums.ItemTypes;
import com.lertos.javafxitemgenerator.model.enums.Rarities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetupData {

    private List<String> itemTypes = new ArrayList<>();
    private List<String> rarities = new ArrayList<>();
    private List<String> classes = new ArrayList<>();
    private List<String> equipmentSlots = new ArrayList<>();

    public SetupData() {
        setupList(itemTypes, ItemTypes.values());
        setupList(rarities, Rarities.values());
        setupList(classes, Classes.values());
        setupList(equipmentSlots, EquipmentSlots.values());
    }

    public List<String> getItemTypes() { return Collections.unmodifiableList(itemTypes); }

    public List<String> getRarities() {
        return Collections.unmodifiableList(rarities);
    }

    public List<String> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<String> getEquipmentSlots() {
        return Collections.unmodifiableList(equipmentSlots);
    }

    private <T extends Enum> void setupList(List list, T[] values) {
        for (T value : values)
            list.add(value.name());
    }

}
