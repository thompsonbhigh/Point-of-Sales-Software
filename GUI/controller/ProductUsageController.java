/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Displays and manages product usage statistics derived from order data and recipes.
 *
 * Author: Team 42
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.Db;
import javafx.stage.Stage;

public class ProductUsageController{

    @FXML
    private TextField fromDateField;
    @FXML
    private TextField toDateField;
    @FXML
    private BarChart<String, Number> usageChart;

    /**
    * Handles the generation of the product usage chart based on user-provided 
    * date inputs. Validates input formats and logical date order before 
    * querying the database for relevant usage data. Displays results as a bar chart.
    */
    @FXML
    private void onGenerateChart() {
        String from = fromDateField.getText();
        String to = toDateField.getText();

        LocalDate fromDate, toDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fromDate = LocalDate.parse(from, formatter);
            toDate = LocalDate.parse(to, formatter);
        } catch (DateTimeParseException e) {
            showError("Invalid Input", "Invalid Input. Please Try Again.");
            return;
        }

        // Validate logical order (from ≤ to)
        if (fromDate.isAfter(toDate)) {
            showError("Invalid date range", "The 'From' date cannot be after the 'To' date.");
            return;
        }

        // (Optional) Prevent future dates
        if (toDate.isAfter(LocalDate.now())) {
            showError("Invalid date", "The 'To' date cannot be in the future.");
            return;
        }

        loadUsageData(fromDate, toDate);
        usageChart.setVisible(true); 
    }


    /**
     * Navigates the user back to the Manager Screen interface.
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

    /**
     * Loads and processes product usage data from the database within the 
     * specified date range. Aggregates total quantities used per inventory item 
     * based on recipe and order data, then populates the bar chart with results.
     *
     * @param from the start date of the query range
     * @param to the end date of the query range
     */

    private void loadUsageData(LocalDate from, LocalDate to) {
        String sql = """
            SELECT 
                i.Item_Name,
                SUM(r.Quantity_Required) AS Total_Used
            FROM Orders o
            JOIN OrderEntry oe ON o.order_id = oe.order_id
            JOIN Recipes r ON oe.MenuItem_ID = r.MenuItem_ID
            JOIN Inventory i ON r.Item_ID = i.Item_ID
            WHERE o.time_stamp BETWEEN ? AND ?
            GROUP BY i.Item_Name;
        """;

        try (Connection conn = Db.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(from.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(to.plusDays(1).atStartOfDay()));

            ResultSet rs = stmt.executeQuery();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Product Used");

            while (rs.next()) {
                String itemName = rs.getString("Item_Name");
                double totalUsed = rs.getDouble("Total_Used");
                series.getData().add(new XYChart.Data<>(itemName, totalUsed));
            }

            usageChart.getData().clear();
            usageChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an error message dialog with a custom title and message.
     * Used to handle invalid input or database query failures gracefully.
     *
     * @param title the title of the error dialog
     * @param message the content message displayed to the user
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}