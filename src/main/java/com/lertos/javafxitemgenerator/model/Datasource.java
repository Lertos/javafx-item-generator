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

    private final String DELETE_ALL_ITEMS =
            " DELETE FROM " + TABLE_ITEMS + " WHERE 1=1;";

    private final String INSERT_ITEM =
            " INSERT INTO " + TABLE_ITEMS + " ( " +
                    COLUMN_ITEM_NAME + ", " +
                    COLUMN_ITEM_RARITY + ", " +
                    COLUMN_ITEM_TYPE + ", " +
                    COLUMN_ITEM_BUY_PRICE + ", " +
                    COLUMN_ITEM_SELL_PRICE + ", " +
                    COLUMN_ITEM_DESCRIPTION + ", " +
                    COLUMN_ITEM_CLASS_REQ + ", " +
                    COLUMN_ITEM_LEVEL_REQ + ", " +
                    COLUMN_ITEM_ONE_HANDED + ", " +
                    COLUMN_ITEM_EQUIP_SLOT + ", " +
                    COLUMN_ITEM_DMG_MIN + ", " +
                    COLUMN_ITEM_DMG_MAX + ", " +
                    COLUMN_ITEM_ARMOR + ", " +
                    COLUMN_ITEM_HEALTH + " ) " +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

    private PreparedStatement psInsertNewItem;

    private Connection conn;

    private static Datasource instance = new Datasource();

    private Datasource() {}

    public static Datasource getInstance() { return instance; }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            //Set up the initial state of the database
            createTables();

            //Set up prepared statements
            psInsertNewItem = conn.prepareStatement(INSERT_ITEM);

            //DEBUG: Statement to delete all items for a fresh start
            deleteAllItems();

            //DEBUG: Testing item creation
            insertNewItem("Item 1", "common", "weapon", 12, 4, "A big item.", "", -1, -1, "", -1, -1, -1, -1);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            //Close all prepared statements
            if (psInsertNewItem != null)
                psInsertNewItem.close();

            //Close the connection to the database
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private void createTables() {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_ITEMS_TABLE);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    private void deleteAllItems() {
        try (Statement statement = conn.createStatement()) {
            statement.execute(DELETE_ALL_ITEMS);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void insertNewItem(
            String name, String rarity, String type, int buyPrice, int sellPrice, String description, String classReq,
            int levelReq, int oneHanded, String equipSlot, int dmgMin, int dmgMax, int armor, int health
            ) {

        try {
            conn.setAutoCommit(false);

            //Info that all items must have
            psInsertNewItem.setString(1, name);
            psInsertNewItem.setString(2, rarity);
            psInsertNewItem.setString(3, type);
            psInsertNewItem.setInt(4, buyPrice);
            psInsertNewItem.setInt(5, sellPrice);
            psInsertNewItem.setString(6, description);

            //Other optional info based on type
            if ((classReq == ""))
                psInsertNewItem.setNull(7, Types.NULL);
            else
                psInsertNewItem.setString(7, classReq);

            if ((levelReq == -1))
                psInsertNewItem.setNull(8, Types.NULL);
            else
                psInsertNewItem.setInt(8, levelReq);

            if ((oneHanded == -1))
                psInsertNewItem.setNull(9, Types.NULL);
            else
                psInsertNewItem.setInt(9, oneHanded);

            if ((equipSlot == ""))
                psInsertNewItem.setNull(10, Types.NULL);
            else
                psInsertNewItem.setString(10, equipSlot);

            if ((dmgMin == -1))
                psInsertNewItem.setNull(11, Types.NULL);
            else
                psInsertNewItem.setInt(11, dmgMin);

            if ((dmgMax == -1))
                psInsertNewItem.setNull(12, Types.NULL);
            else
                psInsertNewItem.setInt(12, dmgMax);

            if ((armor == -1))
                psInsertNewItem.setNull(13, Types.NULL);
            else
                psInsertNewItem.setInt(13, armor);

            if ((health == -1))
                psInsertNewItem.setNull(14, Types.NULL);
            else
                psInsertNewItem.setInt(14, health);

            int affectedRows = psInsertNewItem.executeUpdate();

            if (affectedRows == 1) {
                conn.commit();
            } else {
                throw new SQLException("The item insert failed");
            }

        } catch(Exception e) {
            System.out.println("Insert item exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Rollback failed: " + e2.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Resetting auto-commit failed: " + e.getMessage());
            }
        }
    }
}