package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TransactionListController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ListView<String> transactionList;

    public void setTransactionList(List<String> transactions) {
        transactionList.getItems().addAll(transactions);
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {

        // switch scene to pin-view.fxml
        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}