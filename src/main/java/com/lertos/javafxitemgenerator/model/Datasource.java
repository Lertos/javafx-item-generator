package com.lertos.javafxitemgenerator.model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Datasource {

    private final String DB_NAME = "items.db";
    private final File temp = new File(DB_NAME);
    private final String CONNECTION_STRING = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");

    private final String TABLE_ITEMS = "items";

    private final String COLUMN_ITEM_ID = "id";
    private final String COLUMN_ITEM_NAME = "name";
    private final String COLUMN_ITEM_RARITY = "rarity";
    private final String COLUMN_ITEM_TYPE = "type";
    private final String COLUMN_ITEM_BUY_PRICE = "buy_price";
    private final String COLUMN_ITEM_SELL_PRICE = "sell_price";
    private final String COLUMN_ITEM_DESCRIPTION = "description";
    private final String COLUMN_ITEM_CLASS_REQ = "class_req";
    private final String COLUMN_ITEM_LEVEL_REQ = "level_req";
    private final String COLUMN_ITEM_ONE_HANDED = "one_handed";
    private final String COLUMN_ITEM_EQUIP_SLOT = "equip_slot";
    private final String COLUMN_ITEM_DMG_MIN = "dmg_min";
    private final String COLUMN_ITEM_DMG_MAX = "dmg_max";
    private final String COLUMN_ITEM_ARMOR = "armor";
    private final String COLUMN_ITEM_HEALTH = "health";

    //-------------------------
    // QUERIES
    //-------------------------
    private final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS + " (" +
            "id INT PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "rarity TEXT NOT NULL," +
            "type TEXT NOT NULL," +
            "buy_price INT NOT NULL," +
            "sell_price INT NOT NULL," +
            "description TEXT NULL," +
            "class_req TEXT NULL," +
            "level_req INT NULL," +
            "one_handed INT NULL," +
            "equip_slot TEXT NULL," +
            "dmg_min INT NULL," +
            "dmg_max INT NULL," +
            "armor INT NULL," +
            "health INT NULL," +
            "CONSTRAINT constraint_unique_item UNIQUE (name, rarity, type)" +
            ");";

    private final String QUERY_ITEM_INFO =
            " SELECT " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_TYPE + ", " + COLUMN_ITEM_RARITY +
            " FROM " + TABLE_ITEMS;

    private Connection conn;

    private static Datasource instance = new Datasource();

    private Datasource() {}

    public static Datasource getInstance() { return instance; }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            //Set up the initial state of the database
            createTables();

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private void createTables() {
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery(CREATE_ITEMS_TABLE);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
}