/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for tracking restock events and inventory replenishment operations.
 *
 * Author: Team 42
 */

package dao;

import model.Restock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestockDao {
    private static final String TABLE    = "restock";
    private static final String COL_ID   = "restock_id";
    private static final String COL_ITEM = "item_id";
    private static final String COL_MAX  = "max";
    private static final String COL_MIN  = "min";
    private static final String COL_LIFE = "shelf_life";
    private static final String COL_DATE = "last_restock";

    /**
     * Finds all entries in restock table.
     *
     * @return list of restock entries
     * @throws SQLException if an error occurs
     */

    public static List<Restock> findAll() throws SQLException{
        String sql = "select * from " + TABLE;

        List<Restock> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                out.add(new Restock (
                        r.getInt(COL_ID),
                        r.getInt (COL_ITEM),
                        r.getDouble(COL_MAX),
                        r.getDouble(COL_MIN),
                        r.getInt(COL_LIFE),
                        r.getDate(COL_DATE)
                ));
            }
        }
        return out;
    }

    /**
     * Finds which items need a restock.
     *
     * @return list of items with needed restocks
     * @throws SQLException if an error occurs
     */

    public static List<Restock> findRestocks() throws SQLException {
        //TODO: Change query to search against min
        String sql = "Select * from inventory i JOIN restock r on i.item_id = r.item_id WHERE i.quantity <= r.min";
        List<Restock> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                out.add(new Restock (
                        r.getInt(COL_ID),
                        r.getInt (COL_ITEM),
                        r.getDouble(COL_MAX),
                        r.getDouble(COL_MIN),
                        r.getInt(COL_LIFE),
                        r.getDate(COL_DATE)
                ));
            }
        }
        return out;
    }


    /**
     * Inserts a restock item into the database.
     *
     * @param res restock item
     * @return successful insert
     * @throws SQLException if an error occurs
     */

    public static int insert(Restock res) throws SQLException {
        String sql = "insert into " + TABLE + " (" + COL_ITEM + ", " + COL_MAX + ", " + COL_MIN + ", " + COL_LIFE + ", " + COL_DATE + ") Values (?, ?, ?, ?, ?)";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, res.getItemID());
            ps.setDouble(2, res.getMax());
            ps.setDouble(3, res.getMin());
            ps.setInt(4, res.getShelfLife());
            ps.setDate(5, res.getLastRestock());
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes a restock item by id.
     *
     * @param restockId restock id
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public int delete (int restockId) throws SQLException{
        String sql = "Delete from " + TABLE + " Where " + COL_ID + "=?";
        try(Connection c = Db.connect();
            PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, restockId);
            return ps.executeUpdate();
            }
    }
}