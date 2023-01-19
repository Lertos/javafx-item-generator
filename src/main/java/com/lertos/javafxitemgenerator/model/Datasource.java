package com.lertos.javafxitemgenerator.model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Datasource {

    private final String DB_NAME = "items.db";
    private final File temp = new File(DB_NAME);
    private final String CONNECTION_STRING = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");

    private final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS items (" +
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

    public static final String COLUMN_ITEM_ID = "id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_RARITY = "rarity";
    public static final String COLUMN_ITEM_TYPE = "type";
    public static final String COLUMN_ITEM_BUY_PRICE = "buy_price";
    public static final String COLUMN_ITEM_SELL_PRICE = "sell_price";
    public static final String COLUMN_ITEM_DESCRIPTION = "description";
    public static final String COLUMN_ITEM_CLASS_REQ = "class_req";
    public static final String COLUMN_ITEM_LEVEL_REQ = "level_req";
    public static final String COLUMN_ITEM_ONE_HANDED = "one_handed";
    public static final String COLUMN_ITEM_EQUIP_SLOT = "equip_slot";
    public static final String COLUMN_ITEM_DMG_MIN = "dmg_min";
    public static final String COLUMN_ITEM_DMG_MAX = "dmg_max";
    public static final String COLUMN_ITEM_ARMOR = "armor";
    public static final String COLUMN_ITEM_HEALTH = "health";


    public static final String QUERY_ITEM_INFO =
            " SELECT " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_ID + ", " +
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