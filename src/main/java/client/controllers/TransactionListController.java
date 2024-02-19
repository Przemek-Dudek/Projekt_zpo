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

/**
 * Klasa TransactionListController jest kontrolerem obsługującym operacje związane z listą transakcji.
 * Zawiera metody umożliwiające wyświetlanie listy transakcji oraz powrót do poprzedniego widoku.
 */
public class TransactionListController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ListView<String> transactionList;


    /**
     * Metoda ustawiająca listę transakcji do wyświetlenia.
     * @param transactions lista transakcji do wyświetlenia
     */
    public void setTransactionList(List<String> transactions) {
        transactionList.getItems().addAll(transactions);
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