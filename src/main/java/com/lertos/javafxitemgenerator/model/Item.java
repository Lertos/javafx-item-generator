package com.lertos.javafxitemgenerator.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty rarity;
    private SimpleStringProperty type;
    private SimpleIntegerProperty buyPrice;
    private SimpleIntegerProperty sellPrice;
    private SimpleStringProperty description;
    private SimpleStringProperty classReq;
    private SimpleIntegerProperty levelReq;
    private SimpleIntegerProperty oneHanded;
    private SimpleStringProperty equipSlot;
    private SimpleIntegerProperty dmgMin;
    private SimpleIntegerProperty dmgMax;
    private SimpleIntegerProperty armor;
    private SimpleIntegerProperty health;

    public Item() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.rarity = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.buyPrice = new SimpleIntegerProperty();
        this.sellPrice = new SimpleIntegerProperty();
        this.description = new SimpleStringProperty();
        this.classReq = new SimpleStringProperty();
        this.levelReq = new SimpleIntegerProperty();
        this.oneHanded = new SimpleIntegerProperty();
        this.equipSlot = new SimpleStringProperty();
        this.dmgMin = new SimpleIntegerProperty();
        this.dmgMax = new SimpleIntegerProperty();
        this.armor = new SimpleIntegerProperty();
        this.health = new SimpleIntegerProperty();
    }

    public int getId() { return id.get(); }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRarity() {
        return rarity.get();
    }

    public void setRarity(String rarity) { this.rarity.set(rarity); }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getBuyPrice() {
        return buyPrice.get();
    }

    public void setBuyPrice(int buyPrice) { this.buyPrice.set(buyPrice); }

    public int getSellPrice() {
        return sellPrice.get();
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice.set(sellPrice);
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

    public int getOneHanded() {
        return oneHanded.get();
    }

    public void setOneHanded(int oneHanded) {
        this.oneHanded.set(oneHanded);
    }

    public String getEquipSlot() {
        return equipSlot.get();
    }

    public void setEquipSlot(String equipSlot) {
        this.equipSlot.set(equipSlot);
    }

    public int getDmgMin() {
        return dmgMin.get();
    }

    public void setDmgMin(int dmgMin) {
        this.dmgMin.set(dmgMin);
    }

    public int getDmgMax() {
        return dmgMax.get();
    }

    public void setDmgMax(int dmgMax) {
        this.dmgMax.set(dmgMax);
    }

    public int getArmor() {
        return armor.get();
    }

    public void setArmor(int armor) {
        this.armor.set(armor);
    }

    public int getHealth() {
        return health.get();
    }

    public void setHealth(int health) {
        this.health.set(health);
    }
}
