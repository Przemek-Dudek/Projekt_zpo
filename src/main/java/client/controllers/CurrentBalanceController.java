package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa CurrentBalanceController jest kontrolerem obsługującym wyświetlanie aktualnego stanu konta użytkownika.
 * Zawiera metody umożliwiające ustawienie aktualnego stanu konta oraz powrót do widoku z opcjami.
 */
public class CurrentBalanceController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label currentBalance;

    /**
     * Metoda umożliwiająca ustawienie aktualnego stanu konta użytkownika.
     * @param balance stan konta do wyświetlenia
     */
    public void setCurrentBalance(String balance) {
        currentBalance.setText(balance);
    }

    /**
     * Metoda obsługująca powrót do widoku z opcjami.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void backToOptions(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}