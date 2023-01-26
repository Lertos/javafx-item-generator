package com.lertos.javafxitemgenerator.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleStringProperty classReq;
    private final SimpleIntegerProperty levelReq;
    private final SimpleIntegerProperty dmgMin;
    private final SimpleIntegerProperty dmgMax;

    public Item() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.classReq = new SimpleStringProperty("");
        this.levelReq = new SimpleIntegerProperty(-1);
        this.dmgMin = new SimpleIntegerProperty(-1);
        this.dmgMax = new SimpleIntegerProperty(-1);
    }

    public String getId() { return id.get(); }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getClassReq() {
        return classReq.get();
    }

    public void setClassReq(String classReq) {
        this.classReq.set(classReq);
    }

    public int getLevelReq() {
        return levelReq.get();
    }

    public void setLevelReq(int levelReq) {
        this.levelReq.set(levelReq);
    }

    public int getDmgMin() { return dmgMin.get(); }

    public void setDmgMin(int dmgMin) { this.dmgMin.set(dmgMin); }

    public int getDmgMax() { return dmgMax.get(); }

    public void setDmgMax(int dmgMax) { this.dmgMax.set(dmgMax); }

    @Override
    public String toString() {
        return "Item {" + " id=" + getId() + ", name=" + getName() + ", type=" + getType() + " }";
    }
}
