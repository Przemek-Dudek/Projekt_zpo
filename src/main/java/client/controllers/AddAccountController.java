package client.controllers;

import client.connection.Connector;
import db.entities.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAccountController implements Initializable {

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField ownerNameField;

    @FXML
    private TextField balanceField;

    @FXML
    private TableView<Account> tableView;

    @FXML
    private TableColumn<Account, Integer> accountNumberColumn;

    @FXML
    private TableColumn<Account, String> ownerNameColumn;

    @FXML
    private TableColumn<Account, Double> balanceColumn;

    @FXML
    private TextField deletedAccountNumberField;

    private static ObservableList<Account> accounts = FXCollections.observableArrayList();

    @FXML
    private void handleAddButton(ActionEvent event) {
        String accountNumber = accountNumberField.getText();
        String ownerName = ownerNameField.getText();
        BigDecimal balancePLN;
        BigDecimal balanceEUR = BigDecimal.ZERO;
        String phoneNumber = "";

        try {
            balancePLN = new BigDecimal(balanceField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter a valid numeric value for balance.");
            alert.showAndWait();

            return;
        }

        System.out.println("DEBUG:" + " " + accountNumber + " " + ownerName + " " + balancePLN  + " " + balanceEUR + " " + phoneNumber);
        Account account = new Account(accountNumber, ownerName, balancePLN, balanceEUR, phoneNumber);

        System.out.println("DEBUG accout obj:" + " " + account.getAccountNumber() + " " + account.getName() + " " + account.getBalancePLN());
        Connector.addAccount(account);

        clearFields();
    }



    @FXML
    private void handleDeleteButtonAction(ActionEvent actionEvent) {
        Integer accountNumber = Integer.parseInt(deletedAccountNumberField.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete account number: " + accountNumber + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Connector.deleteAccount(accountNumber);
                deletedAccountNumberField.clear();
            }
        });
    }

    private void clearFields() {
        accountNumberField.clear();
        ownerNameField.clear();
        balanceField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!accounts.isEmpty()) {
            accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
            ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
            balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

            tableView.setItems(accounts);
        }
    }
}
