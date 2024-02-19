package client.controllers;

import client.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PinController {

    @FXML
    private TextField pinNumber;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void insertPin(ActionEvent event) throws IOException {

        String pinNum = pinNumber.getText();

        // print pin
        System.out.println(pinNum);

        // send card number to server
        boolean res = Connector.veryfiyPin(pinNum);


        if(res == true) {
            // switch scene to pin-view.fxml
            root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }  else {
            // show error message
            System.out.println("Card number is not valid");
        }


    }
}