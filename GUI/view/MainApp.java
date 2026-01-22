package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            URL location = getClass().getResource("/view/Main_Screen.fxml");
            
            if (location == null) {
                System.err.println("Error: Cannot find FXML file. Make sure Main_Screen.fxml is in the correct package in your 'bin' folder.");
                return;
            }

            Parent root = FXMLLoader.load(location);
            Scene scene = new Scene(root);
            primaryStage.setTitle("Meal Menu System");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

