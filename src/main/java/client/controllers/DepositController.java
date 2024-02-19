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
 * Klasa DepositController jest kontrolerem obsługującym operacje wpłaty na konto użytkownika.
 * Zawiera metody umożliwiające wprowadzenie kwoty wpłaty, potwierdzenie wpłaty oraz anulowanie operacji.
 */
public class DepositController {

    @FXML
    private TextField amount;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metoda obsługująca potwierdzenie wpłaty przez użytkownika.
     * Jeśli wpłata jest prawidłowa, wyświetla komunikat o pomyślnej wpłacie.
     * W przeciwnym razie wyświetla komunikat o błędzie.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void depositButton(ActionEvent event) throws IOException {


        boolean res = ConnectionManager.deposit(amount.getText());

        if(res == true) {

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

    /**
     * Metoda obsługująca anulowanie operacji wpłaty przez użytkownika.
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
     * Dodaje listener do pola tekstowego, który sprawdza, czy wprowadzona kwota jest poprawna.
     */
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
