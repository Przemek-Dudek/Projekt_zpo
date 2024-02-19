package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClientApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        ConnectionManager.connect();

        if (!ConnectionManager.isConnected()) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/connection-error-view.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bankomat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/welcome-view.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bankomat");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}

