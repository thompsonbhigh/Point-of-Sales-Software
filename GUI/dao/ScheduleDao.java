/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for employee schedules, shifts, and related planning data.
 *
 * Author: Team 42
 */

package dao;

import model.Schedule; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;

public class ScheduleDao {
    private static final String TABLE = "schedule";
    private static final String COL_DATE = "scheduledate";
    private static final String COL_SHIFT = "shift";
    private static final String COL_POSITION = "position";
    private static final String COL_ID = "employeeid";

    /**
     * Finds all schedule entries in database.
     *
     * @return list of all schedule items
     * @throws SQLException if an error occurs
     */

    public static List<Schedule> findAll() throws SQLException{
        String sql = "SELECT "  + COL_DATE + ", " + COL_SHIFT + ", " + COL_POSITION + ", " + COL_ID +
                     " FROM " + TABLE + " ORDER BY " + COL_DATE;

        List<Schedule> out = new ArrayList<>();
        try (Connection c = Db.connect();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql)) {
        while (r.next()) {
            out.add(new Schedule(
                r.getDate(COL_DATE),
                r.getString(COL_SHIFT),
                r.getString(COL_POSITION),
                r.getInt(COL_ID)
            ));
        }
        }
    return out;
    }

    /**
     * Inserts a new shift into schedule.
     *
     * @param date date of shift
     * @param shift type of shift
     * @param position position of shift
     * @param ID employee id
     * @return successful insert
     * @throws SQLException if an error occurs
     */

    public static int insert(Date date, String shift, String position, int ID) throws SQLException{
        String sql = "INSERT INTO " + TABLE + " (" + COL_DATE + ", " + COL_SHIFT + ", " + COL_POSITION 
                     + ", " + COL_ID +") VALUES (?, ?, ?, ?)";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, date);
            ps.setString(2, shift);
            ps.setString(3, position);
            ps.setInt(4, ID);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates type of shift
     *
     * @param date date of shift
     * @param shift new type of shift
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateShift(Date date, String shift) throws SQLException{
        String sql = "UPDATE " + TABLE + " SET " + COL_SHIFT + "=? WHERE " + COL_DATE + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, shift);
            ps.setDate(2, date);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates position of shift
     *
     * @param date date of shift
     * @param position new position of shift
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updatePosition(Date date, String position) throws SQLException{
        String sql = "UPDATE " + TABLE + " SET " + COL_POSITION + "=? WHERE " + COL_DATE + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, position);
            ps.setDate(2, date);
            return ps.executeUpdate();
        }

    }

    /**
     * Updates employee id of a shift.
     * 
     * @param date date of shift
     * @param ID new employee id
     * @return successful update
     * @throws SQLException if an error occurs
     */
    public static int updateID(Date date, int ID) throws SQLException{ // there could be a case where an employee might have to change ID idk
        String sql = "UPDATE " + TABLE + " SET " + COL_ID + "=? WHERE " + COL_DATE + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ID);
            ps.setDate(2, date);
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes a shift from the database.
     *
     * @param date date of shift
     * @param id employee id
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public static int deleteShift(Date date, int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_DATE + " = ? AND " + COL_ID + "=?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, date);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes a shift by date.
     *
     * @param date date of shift
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public static int delete(Date date) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_DATE + " = ?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, date);
            return ps.executeUpdate();
        }
    }

}