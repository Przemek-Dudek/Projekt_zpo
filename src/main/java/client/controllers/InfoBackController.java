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
 * Klasa InfoBackController jest kontrolerem obsługującym operacje powrotu do poprzedniego widoku.
 * Zawiera metody umożliwiające ustawienie tekstu informacyjnego oraz powrót do poprzedniego widoku.
 */
public class InfoBackController {


    @FXML
    private Label infoText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metoda umożliwiająca ustawienie tekstu informacyjnego.
     * @param text tekst do wyświetlenia
     */
    public void setInfoText(String text) {
        infoText.setText(text);
    }

    /**
     * Metoda obsługująca powrót do poprzedniego widoku.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void back(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/options-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
