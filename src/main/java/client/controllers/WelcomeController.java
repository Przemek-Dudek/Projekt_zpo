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
 * Klasa WelcomeController jest kontrolerem obsługującym operacje związane z ekranem powitalnym.
 * Zawiera metody umożliwiające wprowadzenie numeru karty oraz inicjalizację kontrolera.
 */
public class WelcomeController {

    @FXML
    private TextField cardNumber;

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Metoda obsługująca wprowadzenie numeru karty przez użytkownika.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void insertCard(ActionEvent event) throws IOException {

        String cardNum = cardNumber.getText();

        boolean res = ConnectionManager.veryfiyCardNumber(cardNum);


        if (res == true) {
            root = FXMLLoader.load(getClass().getResource("/views/pin-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-end-view.fxml"));
            root = loader.load();
            InfoEndController controller = loader.getController();

            controller.setInfoText("Niepoprawna karta!");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    /**
     * Metoda inicjalizująca kontroler. Dodaje listenera do pola tekstowego, który sprawdza, czy wprowadzane dane są liczbami.
     */
    @FXML
    public void initialize() {
        cardNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
                int maxLength = 16;
                if (cardNumber.getText().length() > maxLength) {
                    String s = cardNumber.getText().substring(0, maxLength);
                    cardNumber.setText(s);
                }

            }
        });
    }
}