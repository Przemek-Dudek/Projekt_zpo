package client.controllers;

import client.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrentBalanceController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label currentBalance;

    public void setCurrentBalance(String balance) {
        currentBalance.setText(balance);
    }
    @FXML
    protected void backToOptions(ActionEvent event) throws IOException {

        // switch scene to pin-view.fxml
        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}