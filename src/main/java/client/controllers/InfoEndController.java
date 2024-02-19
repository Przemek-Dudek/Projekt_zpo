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

public class InfoEndController {

    @FXML
    private Label infoText;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void setInfoText(String text) {
        infoText.setText(text);
    }

    @FXML
    protected void end(ActionEvent event) throws IOException {

            // switch scene
            root = FXMLLoader.load(getClass().getResource("/views/welcome-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

}
