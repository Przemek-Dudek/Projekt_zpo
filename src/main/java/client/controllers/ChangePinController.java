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
 * Klasa ChangePinController jest kontrolerem obsługującym zmianę kodu PIN przez użytkownika.
 * Zawiera metody umożliwiające wprowadzenie nowego kodu PIN, potwierdzenie zmiany oraz anulowanie operacji.
 */
public class ChangePinController {

    @FXML
    private TextField newPin1;
    @FXML
    private TextField newPin2;
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metoda obsługująca potwierdzenie zmiany kodu PIN przez użytkownika.
     * Jeśli wprowadzone kody PIN są takie same, wyświetla komunikat o pomyślnej zmianie kodu PIN.
     * W przeciwnym razie wyświetla komunikat o błędzie.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void confirmNewPin(ActionEvent event) throws IOException {

        boolean res = ConnectionManager.changePin(newPin1.getText(), newPin2.getText());

        if(res == true) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-end-view.fxml"));
            root = loader.load();
            InfoEndController controller = loader.getController();

            controller.setInfoText("Kod PIN został zmieniony!");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }  else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-back-view.fxml"));
            root = loader.load();
            InfoBackController controller = loader.getController();

            controller.setInfoText("Kody PIN nie są takie same!");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    /**
     * Metoda obsługująca anulowanie operacji zmiany kodu PIN przez użytkownika.
     * Przenosi użytkownika z powrotem do widoku z opcjami.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void cancel(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda inicjalizująca kontroler.
     * Dodaje listenery do pól tekstowych, które sprawdzają, czy wprowadzony kod PIN jest poprawny.
     */
    @FXML
    public void initialize() {
        newPin1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    newPin1.setText(newValue.replaceAll("[^\\d]", ""));
                }
                int maxLength = 4;
                if (newPin1.getText().length() > maxLength) {
                    String s = newPin1.getText().substring(0, maxLength);
                    newPin1.setText(s);
                }

            }
        });

        newPin2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    newPin2.setText(newValue.replaceAll("[^\\d]", ""));
                }
                int maxLength = 4;
                if (newPin2.getText().length() > maxLength) {
                    String s = newPin2.getText().substring(0, maxLength);
                    newPin2.setText(s);
                }

            }
        });
    }


}
