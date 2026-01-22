/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for order line-items linking orders to menu items and prices.
 *
 * Author: Team 42
 */

package dao;

import model.OrderEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderEntryDao {

  private static final String TABLE = "orderentry";
  private static final String COL_ENTRY_ID = "entry_id";
  private static final String COL_ORDER_ID = "order_id";
  private static final String COL_MENUITEM_ID = "menuitem_id";
  private static final String COL_SIZE = "size";
  private static final String COL_PRICE = "price";
  
  /**
   * Finds all order entries.
   *
   * @return list of all order entries
   * @throws SQLException if an error occurs
   */

  public List<OrderEntry> findAll() throws SQLException {
    String sql = "SELECT " + COL_ENTRY_ID + ", " + COL_ORDER_ID + ", " + COL_MENUITEM_ID + ", " + COL_SIZE + ", " +
                COL_PRICE + " FROM " + TABLE + " ORDER BY " + COL_ENTRY_ID;
    List<OrderEntry> out = new ArrayList<>();
    try (Connection c = Db.connect();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery(sql)) {
      while (r.next()) {
        out.add(new OrderEntry(
            r.getInt(COL_ENTRY_ID),
            r.getInt(COL_ORDER_ID),
            r.getInt(COL_MENUITEM_ID),
            r.getString(COL_SIZE),
            r.getDouble(COL_PRICE)
        ));
      }
    }
    return out;
  }

  /**
   * Finds an order entry by order id.
   *
   * @param order_id order id
   * @return List of order entries with matching order id
   * @throws SQLException if an error occurs
   */

  public List<OrderEntry> findOrder(int order_id) throws SQLException {
      String sql = "SELECT " + COL_ENTRY_ID + ", " + COL_ORDER_ID + ", " + COL_MENUITEM_ID + ", " + COL_SIZE + ", " +
              COL_PRICE + " FROM " + TABLE + " WHERE " + COL_ORDER_ID + "= ?" + " ORDER BY " + COL_ENTRY_ID;
      List<OrderEntry> out = new ArrayList<>();
      try (Connection c = Db.connect();
           PreparedStatement ps = c.prepareStatement(sql)){
          ps.setInt(1, order_id);
           ResultSet r = ps.executeQuery();
          while (r.next()) {
              out.add(new OrderEntry(
                      r.getInt(COL_ENTRY_ID),
                      r.getInt(COL_ORDER_ID),
                      r.getInt(COL_MENUITEM_ID),
                      r.getString(COL_SIZE),
                      r.getDouble(COL_PRICE)
              ));
          }
      }
      return out;
  }
  
  /**
   * Finds the most common order entry items
   *
   * @return list of commonly ordered items
   * @throws SQLException if an error occurs
   */

  public List<String> findTopCommonOrderFingerprints() throws SQLException {
    String sql = "SELECT " +
                 "  order_items_fingerprint, " +
                 "  COUNT(order_items_fingerprint) AS frequency " +
                 "FROM ( " +
                 "  SELECT " +
                 "    oe.order_id, " +
                 "    STRING_AGG(oe.menuitem_id || ':' || oe.size, ',' ORDER BY oe.menuitem_id, oe.size) AS order_items_fingerprint " +
                 "  FROM " +
                 "    orderentry oe " +
                 "  JOIN " +
                 "    orders o ON oe.order_id = o.order_id " +
                 "  WHERE " +
                 "    o.time_stamp >= (CURRENT_TIMESTAMP - INTERVAL '30 days') " +
                 "  GROUP BY " +
                 "    oe.order_id " +
                 ") AS order_fingerprints " +
                 "WHERE order_items_fingerprint IS NOT NULL " +
                 "GROUP BY " +
                 "  order_items_fingerprint " +
                 "ORDER BY " +
                 "  frequency DESC " +
                 "LIMIT 8;";
    
    List<String> out = new ArrayList<>();
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet r = ps.executeQuery()) {
      while (r.next()) {
        out.add(r.getString("order_items_fingerprint"));
      }
    }
    return out;
  }

  /**
   * Inserts an order entry into the database.
   *
   * @param order_id order id
   * @param menuitem_id menu item id
   * @param size size
   * @return successful insert
   * @throws SQLException if an error occurs
   */

  public static int insert(int order_id, int menuitem_id, String size) throws SQLException {
    String sql = "INSERT INTO " + TABLE + " (" + COL_ORDER_ID + ", " + COL_MENUITEM_ID + ", " + COL_SIZE + ") VALUES (?, ?, ?)";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, order_id);
      ps.setInt(2, menuitem_id);
      ps.setString(3, size);
      return ps.executeUpdate(); // rows affected (expect 1)
    }
  }

  /**
   * Deletes an order entry by entry id.
   *
   * @param entry_id entry id
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public static int delete(int entry_id) throws SQLException {
    String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ENTRY_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, entry_id);
      return ps.executeUpdate();
    }
  }

  /**
   * Deletes order entry by order id
   *
   * @param order_id order id
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public static int deleteByOrder(int order_id) throws SQLException {
    String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ORDER_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, order_id);
      return ps.executeUpdate();
    }
  }

  /**
   * Deletes a menu item from orderentry by order id.
   *
   * @param order_id order id
   * @param menuitem_id menu item id
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public static int deleteItemFromOrder(int order_id, int menuitem_id) throws SQLException {
      String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ORDER_ID + "=? AND " + COL_MENUITEM_ID + "=?";
      try (Connection c = Db.connect();
           PreparedStatement ps = c.prepareStatement(sql)) {
          ps.setInt(1, order_id);
          ps.setInt(2, menuitem_id);
          return ps.executeUpdate();
      }
  }
}