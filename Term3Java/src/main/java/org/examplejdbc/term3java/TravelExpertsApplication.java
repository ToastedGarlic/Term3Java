package org.example.jdbc.term3java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class TravelExpertsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/org/example/jdbc/term3java/MainView.fxml"));
        BorderPane root = mainLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Travel Experts Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}