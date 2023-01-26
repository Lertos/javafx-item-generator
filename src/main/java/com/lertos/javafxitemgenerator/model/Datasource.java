package com.lertos.javafxitemgenerator.model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    private final String DB_NAME = "items.db";
    private final File temp = new File(DB_NAME);
    private final String CONNECTION_STRING = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");

    private final String TABLE_ITEMS = "items";

    private final String COLUMN_ITEM_ID = "item_id";
    private final String COLUMN_ITEM_NAME = "name";
    private final String COLUMN_ITEM_TYPE = "type";
    private final String COLUMN_ITEM_DESCRIPTION = "desc";
    private final String COLUMN_ITEM_CLASS_REQ = "class_req";
    private final String COLUMN_ITEM_LEVEL_REQ = "level_req";
    private final String COLUMN_ITEM_DMG_MIN = "dmg_min";
    private final String COLUMN_ITEM_DMG_MAX = "dmg_max";

    //-------------------------
    // QUERIES
    //-------------------------
    private final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS + " (" +
            COLUMN_ITEM_ID + " TEXT NOT NULL, " +
            COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
            COLUMN_ITEM_TYPE + " TEXT NOT NULL, " +
            COLUMN_ITEM_DESCRIPTION + " TEXT NULL, " +
            COLUMN_ITEM_CLASS_REQ + " TEXT NULL, " +
            COLUMN_ITEM_LEVEL_REQ + " INT NULL, " +
            COLUMN_ITEM_DMG_MIN + " INT NULL, " +
            COLUMN_ITEM_DMG_MAX + " INT NULL, " +
            "CONSTRAINT constraint_unique_id primary key(" + COLUMN_ITEM_ID + ")," +
            "CONSTRAINT constraint_unique_item UNIQUE (" + COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_TYPE + ")" +
            ");";

    private final String QUERY_ITEM_INFO =
            " SELECT " + COLUMN_ITEM_ID + ", " + COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_TYPE +
            " FROM " + TABLE_ITEMS;

    private final String QUERY_ITEM_INFO_SINGLE =
            " SELECT " +
                    COLUMN_ITEM_ID + ", " +
                    COLUMN_ITEM_NAME + ", " +
                    COLUMN_ITEM_TYPE + ", " +
                    COLUMN_ITEM_DESCRIPTION + ", " +
                    COLUMN_ITEM_CLASS_REQ + ", " +
                    COLUMN_ITEM_LEVEL_REQ + ", " +
                    COLUMN_ITEM_DMG_MIN + ", " +
                    COLUMN_ITEM_DMG_MAX +
            " FROM " + TABLE_ITEMS +
            " WHERE " + COLUMN_ITEM_ID + " = ?;";

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

    private final String UPDATE_ITEM =
            " UPDATE " + TABLE_ITEMS + " SET " +
                    COLUMN_ITEM_NAME + " = ?, " +
                    COLUMN_ITEM_TYPE + " = ?, " +
                    COLUMN_ITEM_DESCRIPTION + " = ?, " +
                    COLUMN_ITEM_CLASS_REQ + " = ?, " +
                    COLUMN_ITEM_LEVEL_REQ + " = ?, " +
                    COLUMN_ITEM_DMG_MIN + " = ?, " +
                    COLUMN_ITEM_DMG_MAX + " = ? " +
            " WHERE " + COLUMN_ITEM_ID + " = " + " ? ";

    private PreparedStatement psQuerySingleItem;
    private PreparedStatement psInsertNewItem;
    private PreparedStatement psUpdateExistingItem;

    private Connection conn;

    private static Datasource instance = new Datasource();

    private Datasource() {}

    public static Datasource getInstance() { return instance; }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            //For debugging where the DB file is created
            System.out.println(CONNECTION_STRING.replace("\\\\", "\\"));

            //Set up the initial state of the database
            createTables();

            //Set up prepared statements
            psQuerySingleItem = conn.prepareStatement(QUERY_ITEM_INFO_SINGLE);
            psInsertNewItem = conn.prepareStatement(INSERT_ITEM);
            psUpdateExistingItem = conn.prepareStatement(UPDATE_ITEM);

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
            if (psQuerySingleItem != null)
                psQuerySingleItem.close();

            if (psInsertNewItem != null)
                psInsertNewItem.close();

            if (psUpdateExistingItem != null)
                psUpdateExistingItem.close();

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

    public List<Item> queryAllItems() {
        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery(QUERY_ITEM_INFO);

            List<Item> items = new ArrayList<>();
            while (results.next()) {
                Item item = new Item();

                item.setId(results.getString(COLUMN_ITEM_ID));
                item.setName(results.getString(COLUMN_ITEM_NAME));
                item.setType(results.getString(COLUMN_ITEM_TYPE));

                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return null;
    }

    public Item querySingleItem(String itemId) throws SQLException {
        try {
            psQuerySingleItem.setString(1, itemId);
            ResultSet results = psQuerySingleItem.executeQuery();

            while(results.next()) {
                Item item = new Item();

                item.setId(results.getString(COLUMN_ITEM_ID));
                item.setName(results.getString(COLUMN_ITEM_NAME));
                item.setType(results.getString(COLUMN_ITEM_TYPE));
                item.setDescription(results.getString(COLUMN_ITEM_DESCRIPTION));

                if (results.getString(COLUMN_ITEM_TYPE).equalsIgnoreCase("WEAPON")) {
                    item.setClassReq(results.getString(COLUMN_ITEM_CLASS_REQ));
                    item.setLevelReq(results.getInt(COLUMN_ITEM_LEVEL_REQ));
                    item.setDmgMin(results.getInt(COLUMN_ITEM_DMG_MIN));
                    item.setDmgMax(results.getInt(COLUMN_ITEM_DMG_MAX));
                }

                return item;
            }
        } catch (SQLException sqlException) {
            throw new SQLException(sqlException.getMessage());
        } catch(Exception e) {}
        return null;
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

    public void updateExistingItem(Item item) throws SQLException {
        try {
            //Info that all items must have
            psUpdateExistingItem.setString(1, item.getName());
            psUpdateExistingItem.setString(2, item.getType());
            psUpdateExistingItem.setString(3, item.getDescription());

            //Other optional info based on type
            if ((item.getClassReq() == ""))
                psUpdateExistingItem.setNull(4, Types.NULL);
            else
                psUpdateExistingItem.setString(4, item.getClassReq());

            if ((item.getLevelReq() == -1))
                psUpdateExistingItem.setNull(5, Types.NULL);
            else
                psUpdateExistingItem.setInt(5, item.getLevelReq());

            if ((item.getDmgMin() == -1))
                psUpdateExistingItem.setNull(6, Types.NULL);
            else
                psUpdateExistingItem.setInt(6, item.getDmgMin());

            if ((item.getDmgMax() == -1))
                psUpdateExistingItem.setNull(7, Types.NULL);
            else
                psUpdateExistingItem.setInt(7, item.getDmgMax());

            psUpdateExistingItem.setString(8, item.getId());

            int affectedRows = psUpdateExistingItem.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("No records were updated");
        } catch (SQLException sqlException) {
            throw new SQLException(sqlException.getMessage());
        } catch (Exception e) {
        }
    }
}