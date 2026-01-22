/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for overall customer orders, timestamps, and total revenue tracking.
 *
 * Author: Team 42
 */

package dao;

import model.Orders;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class OrdersDao {
    private static final String TABLE = "orders";
    private static final String COL_ORDER_ID = "order_id";
    private static final String COL_EMPLOYEE_ID = "employee_id";
    private static final String COL_TOTAL_COST = "total_cost";
    private static final String COL_TIME_STAMP = "time_stamp";

        /**
         * Gets the last order id in the order table database.
         *
         * @return id of last order
         * @throws SQLException if an error occurs
         */

    public int getLastOrderId() throws SQLException {
      String sql = "SELECT MAX(order_id) AS max_id FROM orders";
      try (Connection c = Db.connect();
          PreparedStatement ps = c.prepareStatement(sql);
          ResultSet r = ps.executeQuery()) {
          if (r.next()) {
              return r.getInt("max_id");
          } else {
            throw new SQLException("No orders found.");
          }
        }
    }

  /**
   * Finds all orders in the database.
   *
   * @return list of all orders
   * @throws SQLException if an error occurs
   */

  public List<Orders> findAll() throws SQLException {
    String sql = "SELECT " + COL_ORDER_ID + ", " + COL_EMPLOYEE_ID + ", " + COL_TOTAL_COST  + ", " + COL_TIME_STAMP +
                 " FROM " + TABLE + " ORDER BY " + COL_ORDER_ID;
    List<Orders> out = new ArrayList<>();
    try (Connection c = Db.connect();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery(sql)) {
      while (r.next()) {
        out.add(new Orders(
            r.getInt(COL_ORDER_ID),
            r.getInt(COL_EMPLOYEE_ID),
            r.getDouble(COL_TOTAL_COST),
            r.getTimestamp(COL_TIME_STAMP)
        ));
      }
    }
    return out;
  }

    /**
     * Finds an order by order id
     *
     * @param order_id order id
     * @return order with matching order id
     * @throws SQLException if an error occurs
     */

    public Orders find(int order_id) throws SQLException {
        String sql = "SELECT " + COL_ORDER_ID + ", " + COL_EMPLOYEE_ID + ", " + COL_TOTAL_COST + ", " + COL_TIME_STAMP +
                " FROM " + TABLE + " WHERE " + COL_ORDER_ID + "=" + order_id;
        List<Orders> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new Orders(
                        r.getInt(COL_ORDER_ID),
                        r.getInt(COL_EMPLOYEE_ID),
                        r.getDouble(COL_TOTAL_COST),
                        r.getTimestamp(COL_TIME_STAMP)
                ));
            }
        }
        return out.get(0);
    }

  /**
   * Inserts an order into the database.
   *
   * @param employeeID employee id
   * @param timeStamp time order was placed
   * @return successful insert
   * @throws SQLException if an error occurs
   */

  public int insert(int employeeID, Timestamp timeStamp) throws SQLException {
    String sql = "INSERT INTO " + TABLE + " (" + COL_EMPLOYEE_ID + ", " + COL_TIME_STAMP +  ") VALUES (?, ?)";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, employeeID);
      ps.setTimestamp(2, timeStamp);
      return ps.executeUpdate(); // rows affected (expect 1)
    }
  }

  /**
   * Updates cost of order.
   *
   * @param orderID order id
   * @param newCost new cost
   * @return successful update
   * @throws SQLException if an error occurs
   */

  public int updateCost(int orderID, double newCost) throws SQLException {
    String sql = "UPDATE " + TABLE + " SET " + COL_TOTAL_COST + "=? WHERE " + COL_ORDER_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setDouble(1, newCost);
      ps.setInt(2, orderID);
      return ps.executeUpdate();
    }
  }

  /**
   * Deletes order by id
   *
   * @param orderID order id
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public int deleteById(int orderID) throws SQLException {
    String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ORDER_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, orderID);
      return ps.executeUpdate();
    }
  }

    /**
     * Deletes the most recent order.
     *
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public int delete() throws SQLException {
    String sql = "DELETE FROM orders WHERE order_id = (SELECT MAX(order_id) FROM orders)";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      return ps.executeUpdate();
    }
  }
}