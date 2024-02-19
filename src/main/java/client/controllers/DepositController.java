package client.controllers;

import client.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DepositController {

    @FXML
    private TextField amount;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void depositButton(ActionEvent event) throws IOException {


        System.out.println(amount.getText());

        boolean res = ConnectionManager.deposit(amount.getText());

        if(res == true) {
            // switch scene to pin-view.fxml
            // switch scene to pin-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-end-view.fxml"));
            root = loader.load();
            InfoEndController controller = loader.getController();

            controller.setInfoText("Dziękujemy za wpłatę!");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }  else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-back-view.fxml"));
            root = loader.load();
            InfoBackController controller = loader.getController();

            controller.setInfoText("Kwota jest nieprawidłowa");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    @FXML
    protected void cancel(ActionEvent event) throws IOException {

        // switch scene
        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void initialize() {
        amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    amount.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });
    }

}
