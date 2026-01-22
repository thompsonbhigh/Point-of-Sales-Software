/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Generates and displays sales summaries and performance analytics.
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalesReportController {

    @FXML
    private TextField fromDateField;
    @FXML
    private TextField toDateField;

    @FXML
    private Text totalSalesLabel;
    
    @FXML
    private TableView<SalesReportItem> salesReportTable;

    @FXML
    private TableColumn<SalesReportItem, String> itemCol;

    @FXML
    private TableColumn<SalesReportItem, Integer> qtyCol;   

    public static class SalesReportItem {
        private final String menu_item;
        private final int quantitySold;

      /**
       * Represents a single entry in the sales report table. 
       * Each entry corresponds to a menu item and the total quantity sold 
       * within a given time period.
       *
       * @param menu_item the name of the menu item
       * @param quantitySold the number of times the menu item was sold
       */

        public SalesReportItem(String menu_item, int quantitySold) {
            this.menu_item = menu_item;
            this.quantitySold = quantitySold;
        }

        public String getMenu_item() { return menu_item; }
        public int getQuantitySold() { return quantitySold; }
    }

    public static class SalesReportService {

        /**
         * Service class responsible for querying the database and generating 
         * an observable list of sales report entries. It executes an SQL query 
         * that joins the orders, order entries, and menu item tables to compute 
         * total quantities sold per menu item within the specified date range.
         *
         * @param from_date the start date of the report range (inclusive)
         * @param to_date the end date of the report range (inclusive)
         * @return an ObservableList of {@link SalesReportItem} objects representing sales data
         */

        public ObservableList<SalesReportItem> generateSalesReport(String from_date, String to_date) {
            ObservableList<SalesReportItem> data = FXCollections.observableArrayList();
                
            String query = "SELECT " +
               "    mi.menuitem_name, " +
               "    COUNT(*) AS quantity " +
               "FROM orders o " +
               "JOIN orderentry oe ON o.order_id = oe.order_id " +
               "JOIN menuitem mi ON oe.menuitem_id = mi.menuitem_id " +
               "WHERE DATE(time_stamp) >= DATE '" + from_date + "' " +
               "AND DATE(time_stamp) <= DATE '" + to_date + "' " +
               "GROUP BY mi.menuitem_name, mi.category " +
               "ORDER BY mi.menuitem_name;";

            try (Connection conn = Db.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String itemName = rs.getString("menuitem_name");
                    int qty = rs.getInt("quantity");
                    data.add(new SalesReportItem(itemName, qty));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return data;
        }

        public double generateTotalCost(String from_date, String to_date) throws SQLException {
            double totalCost;

            String sql = "SELECT SUM(total_cost) FROM orders WHERE time_stamp BETWEEN '" + from_date + "' AND '" + to_date + "'";

            try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
                ResultSet r = ps.executeQuery();
                if (r.next()) {
                    return r.getDouble(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0.0;
        }
    }

    private SalesReportService reportService = new SalesReportService();


    /**
     * Handles the logic for generating the sales report when the user triggers 
     * the “Generate Report” action. It validates date input fields, retrieves 
     * the corresponding sales data, and populates the table view with the results.
     *
     * @param event the event triggered by the user (e.g., button click)
     * @throws IOException if an error occurs while accessing FXML resources
     */
    @FXML
    private void onGenerateChart(ActionEvent event) throws IOException {
        itemCol.setCellValueFactory(new PropertyValueFactory<>("menu_item"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));

        String fromDate = fromDateField.getText();
        String toDate = toDateField.getText();

        ObservableList<SalesReportItem> data = reportService.generateSalesReport(fromDate, toDate);
        salesReportTable.setItems(data);

        try {
            double total = reportService.generateTotalCost(fromDate, toDate);
            totalSalesLabel.setText(String.format("Total Sales: $%.2f", total));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Navigates the user back to the Manager Screen interface from the Sales Report view.  
     * This enables users to return to the main management dashboard after reviewing sales data.
     *
     * @param event the event triggered by user interaction (e.g., button click)
     * @throws IOException if the FXML file for the manager view cannot be loaded
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
