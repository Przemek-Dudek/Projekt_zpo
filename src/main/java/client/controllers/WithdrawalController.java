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
 * Klasa WithdrawalController jest kontrolerem obsługującym operacje związane z wypłatą gotówki.
 * Zawiera metody umożliwiające wprowadzenie kwoty do wypłaty, wypłatę gotówki oraz powrót do poprzedniego widoku.
 */
public class WithdrawalController {

    @FXML
    private TextField amount;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metoda obsługująca operację wypłaty gotówki.
     * Jeżeli operacja wypłaty zakończy się sukcesem, użytkownik jest przekierowywany do widoku z informacją o pomyślnej wypłacie.
     * W przeciwnym razie, użytkownik jest przekierowywany do widoku z informacją o błędzie.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void withdrawalButton(ActionEvent event) throws IOException {


        boolean res = ConnectionManager.withdraw(amount.getText());

        if (res == true) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info-end-view.fxml"));
            root = loader.load();
            InfoEndController controller = loader.getController();

            controller.setInfoText("Odbierz swoją gotówkę");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
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
     * Metoda obsługująca operację powrotu do poprzedniego widoku.
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
     * Metoda inicjalizująca kontroler. Dodaje listenera do pola tekstowego, który sprawdza, czy wprowadzane dane są liczbami.
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
