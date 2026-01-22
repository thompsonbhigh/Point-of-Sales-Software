/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Controller responsible for generating and displaying X and Z financial reports 
 * used for daily sales tracking and end-of-day reconciliation. The X report provides a live 
 * summary of sales data segmented by hourly intervals, while the Z report finalizes the day’s 
 * totals and updates the system timestamp for the next reporting period
 *
 * Author: Team 42
 */

package controller;

import dao.Db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.LocalTime;


public class XAndZReportController {

    @FXML
    private TableView<XReportItem> XAndZReportTable;

    @FXML
    private TableColumn<XReportItem, String> itemCol;

    @FXML
    private TableColumn<XReportItem, Integer> qtyCol;  
    
    @FXML
    private TableColumn<XReportItem, Double> salesCol;  

    public static String latestZReport = "";
    public static Boolean isZReport = false;

    public static class XReportItem {
        private final String hour_range;
        private final int quantitySold;
        private final double totalSales;

      /**
       * Represents a single entry in the X or Z report table.  
       * Each entry contains a time range (hourly interval), the total number of orders placed,  
       * and the total sales revenue for that interval.
       *
       * @param hour_range the time range representing a one-hour period (e.g., "14:00 - 14:59")
       * @param quantitySold the total number of orders processed during that hour
       * @param totalSales the total revenue generated during that hour
       */

        public XReportItem(String hour_range, int quantitySold, double totalSales) {
            this.hour_range = hour_range;
            this.quantitySold = quantitySold;
            this.totalSales = totalSales;
        }

        public String getHour_range() { return hour_range; }
        public int getQuantitySold() { return quantitySold; }
        public double getTotalSales() { return totalSales; }
    }

    public static class XReportService {

        /**
         * Generates an X report summarizing hourly sales and order counts for the current day.  
         * If a previous Z report exists, it uses that timestamp as the starting point  
         * for calculating sales since the last report.
         *
         * @return an ObservableList of {@link XReportItem} containing hourly sales data
         */

        public ObservableList<XReportItem> generateXReport() {
            ObservableList<XReportItem> data = FXCollections.observableArrayList();
                
            LocalDate today = LocalDate.now();
            String todayString = today.toString() + "   ";
            if(!latestZReport.equals(""))
            {
                todayString =  latestZReport;
            }

            String query = "SELECT hour_range, number_of_orders, total_sales FROM ( " +
               "    SELECT " +
               "        TO_CHAR(time_stamp, 'HH24') || ':00 - ' || TO_CHAR(time_stamp, 'HH24') || ':59' AS hour_range, " +
               "        COUNT(order_id) AS number_of_orders, " +
               "        SUM(total_cost) AS total_sales " +
               "    FROM orders " +
               "    WHERE time_stamp >= '" + todayString + "' " +
               "    AND DATE(time_stamp) = DATE '" + todayString + "' " +
               "    GROUP BY TO_CHAR(time_stamp, 'HH24') " +
               "    UNION ALL " +
               "    SELECT " +
               "        'TOTAL' AS hour_range, " +
               "        COUNT(order_id) AS number_of_orders, " +
               "        SUM(total_cost) AS total_sales " +
               "    FROM orders " +
               "    WHERE time_stamp >= '" + todayString + "' " +
               "    AND DATE(time_stamp) = DATE '" + todayString + "' " +
               ") t " +
               "ORDER BY CASE WHEN hour_range = 'TOTAL' THEN 2 ELSE 1 END, hour_range;";

            try (Connection conn = Db.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String itemName = rs.getString("hour_range");
                    int qty = rs.getInt("number_of_orders");
                    double total = rs.getDouble("total_sales");
                    data.add(new XReportItem(itemName, qty, total));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return data;
        }
        
        /**
         * Updates the stored timestamp marking the last Z report generation time.  
         * This timestamp is used to ensure that future X reports only include orders  
         * placed after the most recent Z report.
         */

        public void UpdateTime() {
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.now();
            String todayString = today.toString() + " " + time.toString();
            latestZReport = todayString;
        }
    }

    private XReportService reportService = new XReportService();


    /**
     * Handles the logic for generating and displaying the X or Z report when triggered by the user.  
     * It binds table columns to data fields, executes the report query, and updates the table view.  
     * If a Z report is being generated, the current timestamp is recorded for end-of-day tracking.
     *
     * @param event the event triggered by user interaction (e.g., clicking "Generate Report")
     * @throws IOException if a loading error occurs while processing FXML components
     */
    @FXML
    private void onGenerateChart(ActionEvent event) throws IOException {
        itemCol.setCellValueFactory(new PropertyValueFactory<>("hour_range"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        salesCol.setCellValueFactory(new PropertyValueFactory<>("totalSales"));

        ObservableList<XReportItem> data = reportService.generateXReport();
        if(isZReport)
        {
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.now();
            String todayString = today.toString() + " " + time.toString();
            latestZReport = todayString;
        }
        XAndZReportTable.setItems(data);
    }


    /**
     * Navigates the user back to the Manager Screen from the X/Z Report view.  
     * This allows managers to return to the main dashboard after reviewing sales data.
     *
     * @param event the event triggered by user interaction (e.g., button click)
     * @throws IOException if an error occurs while loading the manager view
     */
    @FXML
    private void navigateToManagerScreen(ActionEvent event) throws IOException {
        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/manager view.fxml"));
        Scene mainScreenScene = new Scene(mainScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }
}