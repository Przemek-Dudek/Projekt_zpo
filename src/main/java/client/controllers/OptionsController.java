package client.controllers;

import client.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


/**
 * Klasa OptionsController jest kontrolerem obsługującym operacje związane z różnymi opcjami dostępnymi dla użytkownika.
 * Zawiera metody umożliwiające wyświetlenie bieżącego salda, wyświetlenie opcji wypłaty, wyświetlenie opcji wypłaty w euro,
 * wyświetlenie opcji wpłaty, wyświetlenie opcji zmiany PINu, wyświetlenie opcji doładowania telefonu, wylogowanie oraz wyświetlenie listy transakcji.
 */
public class OptionsController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Metoda obsługująca wyświetlenie bieżącego salda użytkownika.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showCurrentBalance(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/balance-view.fxml"));
        root = loader.load();
        CurrentBalanceController controller = loader.getController();

        double balance = ConnectionManager.getBalance();

        controller.setCurrentBalance(String.valueOf(balance) + " " + "PLN");

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie opcji wypłaty.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showWithdrawal(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/withdrawal-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie opcji wypłaty w euro.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showEurWithdrawal(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/eur-withdrawal-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie opcji wpłaty.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showDeposit(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/deposit-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie opcji zmiany PINu.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showChangePin(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/change-pin-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie opcji doładowania telefonu.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showPhoneTopUp(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/views/top-up-phone-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wylogowanie użytkownika.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void logOut(ActionEvent event) throws IOException {

        // switch scene to pin-view.fxml
        root = FXMLLoader.load(getClass().getResource("/views/welcome-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Metoda obsługująca wyświetlenie listy transakcji użytkownika.
     * @param event zdarzenie wywołane przez użytkownika
     * @throws IOException jeśli wystąpi błąd podczas ładowania widoku
     */
    @FXML
    protected void showTransactionsList(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/transaction-list-view.fxml"));
        root = loader.load();
        TransactionListController controller = loader.getController();

        List<String> transactionsList = ConnectionManager.getTransactionsList();

        controller.setTransactionList(transactionsList);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
