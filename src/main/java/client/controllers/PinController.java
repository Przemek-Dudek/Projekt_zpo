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

/**
 * Klasa PinController jest kontrolerem obsługującym operacje związane z wprowadzaniem numeru PIN przez użytkownika.
 * Zawiera metody umożliwiające wprowadzenie numeru PIN oraz inicjalizację kontrolera.
 */
public class PinController {

    @FXML
    private TextField pinNumber;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metoda obsługująca wprowadzenie numeru PIN przez użytkownika.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void insertPin(ActionEvent event) throws IOException {

        String pinNum = pinNumber.getText();

        boolean res = ConnectionManager.veryfiyPin(pinNum);


        if (res == true) {
            root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-end-view.fxml"));
            root = loader.load();
            InfoEndController controller = loader.getController();

            controller.setInfoText("Kody PIN jest błędny!");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    /**
     * Metoda inicjalizująca kontroler. Dodaje listenera do pola tekstowego, który sprawdza, czy wprowadzane dane są liczbami oraz ogranicza długość wprowadzanego PINu do 4 cyfr.
     */
    @FXML
    public void initialize() {
        pinNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pinNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
                int maxLength = 4;
                if (pinNumber.getText().length() > maxLength) {
                    String s = pinNumber.getText().substring(0, maxLength);
                    pinNumber.setText(s);
                }

            }
        });
    }

}