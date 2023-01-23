package com.lertos.javafxitemgenerator.model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Datasource {

    private final String DB_NAME = "items.db";
    private final File temp = new File(DB_NAME);
    private final String CONNECTION_STRING = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");

    private final String TABLE_ITEMS = "items";

    private final String COLUMN_ITEM_ID = "item_id";
    private final String COLUMN_ITEM_NAME = "name";
    private final String COLUMN_ITEM_TYPE = "type";
    private final String COLUMN_ITEM_DESCRIPTION = "description";
    private final String COLUMN_ITEM_CLASS_REQ = "class_req";
    private final String COLUMN_ITEM_LEVEL_REQ = "level_req";
    private final String COLUMN_ITEM_DMG_MIN = "dmg_min";
    private final String COLUMN_ITEM_DMG_MAX = "dmg_max";

    //-------------------------
    // QUERIES
    //-------------------------
    private final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS + " (" +
            "item_id TEXT NOT NULL," +
            "name TEXT NOT NULL," +
            "type TEXT NOT NULL," +
            "description TEXT NULL," +
            "class_req TEXT NULL," +
            "level_req INT NULL," +
            "dmg_min INT NULL," +
            "dmg_max INT NULL," +
            "CONSTRAINT constraint_unique_id primary key(item_id)," +
            "CONSTRAINT constraint_unique_item UNIQUE (name, type)" +
            ");";

    private final String QUERY_ITEM_INFO =
            " SELECT " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_TYPE +
            " FROM " + TABLE_ITEMS;

    private final String DELETE_ALL_ITEMS =
            " DELETE FROM " + TABLE_ITEMS + " WHERE 1=1;";

    private final String INSERT_ITEM =
            " INSERT INTO " + TABLE_ITEMS + " ( " +
                    COLUMN_ITEM_ID + ", " +
                    COLUMN_ITEM_NAME + ", " +
                    COLUMN_ITEM_TYPE + ", " +
                    COLUMN_ITEM_DESCRIPTION + ", " +
                    COLUMN_ITEM_CLASS_REQ + ", " +
                    COLUMN_ITEM_LEVEL_REQ + ", " +
                    COLUMN_ITEM_DMG_MIN + ", " +
                    COLUMN_ITEM_DMG_MAX +
            " ) VALUES (?,?,?,?,?,?,?,?) ";

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
            Item item = new Item();

            item.setId("axe");
            item.setName("Axe");
            item.setType("WEAPON");
            item.setDescription("A big ol weapon");
            item.setClassReq("WARRIOR");
            item.setLevelReq(1);
            item.setDmgMin(2);
            item.setDmgMax(10);

            insertNewItem(item);

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

    public void insertNewItem(Item item) throws SQLException {
        try {
            conn.setAutoCommit(false);

            //Info that all items must have
            psInsertNewItem.setString(1, item.getId());
            psInsertNewItem.setString(2, item.getName());
            psInsertNewItem.setString(3, item.getType());
            psInsertNewItem.setString(4, item.getDescription());

            //Other optional info based on type
            if ((item.getClassReq() == ""))
                psInsertNewItem.setNull(5, Types.NULL);
            else
                psInsertNewItem.setString(5, item.getClassReq());

            if ((item.getLevelReq() == -1))
                psInsertNewItem.setNull(6, Types.NULL);
            else
                psInsertNewItem.setInt(6, item.getLevelReq());

            if ((item.getDmgMin() == -1))
                psInsertNewItem.setNull(7, Types.NULL);
            else
                psInsertNewItem.setInt(7, item.getDmgMin());

            if ((item.getDmgMax() == -1))
                psInsertNewItem.setNull(8, Types.NULL);
            else
                psInsertNewItem.setInt(8, item.getDmgMax());

            int affectedRows = psInsertNewItem.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("No records were inserted");
        } catch (SQLException sqlException) {
            throw new SQLException(sqlException.getMessage());
        } catch(Exception e) {}
    }
}