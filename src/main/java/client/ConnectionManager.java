package client;


import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Klasa ConnectionManager zarządza połączeniem z serwerem.
 * Umożliwia nawiązanie i zakończenie połączenia, a także przesyłanie i odbieranie danych.
 */
public class ConnectionManager {

    private static boolean loading;

    private static boolean connected;

    private static Socket socket = null;
    private static ObjectInputStream input = null;
    private static ObjectOutputStream out = null;

    /**
     * Metoda sprawdzająca, czy istnieje aktywne połączenie z serwerem.
     * @return true jeśli połączenie jest aktywne, false w przeciwnym razie.
     */
    public static boolean isConnected() {
        return connected;
    }


    /**
     * Metoda nawiązująca połączenie z serwerem.
     */
    public static void connect() {
        try {
            loading = true;
            connected = false;
            socket = new Socket("localhost", 32777);
            out = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected");
        } catch (UnknownHostException u) {

            System.out.println("Cannot connect to server");

            loading = false;
            return;
        } catch (IOException i) {
//            System.out.println(i);
            loading = false;
            return;
        }
//        catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        loading = false;
        connected = true;
    }

    /**
     * Metoda zamykająca połączenie z serwerem.
     */
    public void disconnect() {
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            loading = false;
            return;
        }
        loading = false;
        connected = false;
        socket = null;
        System.out.println("Disconnected");
    }

    /**
     * Metoda sprawdzająca poprawność numeru karty.
     * @param cardNumber numer karty do sprawdzenia.
     * @return true jeśli numer karty jest poprawny, false w przeciwnym razie.
     */
    public static boolean veryfiyCardNumber(String cardNumber) {

        boolean message;

        try {
            out.writeObject(Operations.VERIFY_CARD);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(cardNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            message = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    /**
     * Metoda sprawdzająca poprawność numeru PIN.
     * @param pinNumber numer PIN do sprawdzenia.
     * @return true jeśli numer PIN jest poprawny, false w przeciwnym razie.
     */
    public static boolean veryfiyPin(String pinNumber) {

        boolean message;

        try {
            out.writeObject(Operations.VERIFY_PIN);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(pinNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            message = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    /**
     * Metoda pobierająca saldo konta.
     * @return saldo konta.
     */
    public static double getBalance() {

        double balance;

        try {
            out.writeObject(Operations.SHOW_BALANCE);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }

        try {
            balance = (double) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return balance;

    }

    /**
     * Metoda realizująca wypłatę z konta.
     * @param amount kwota do wypłaty.
     * @return true jeśli operacja się powiodła, false w przeciwnym razie.
     */
    public static boolean withdraw(String amount) {

        boolean result;

        try {
            out.writeObject(Operations.WITHDRAW);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            result = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    /**
     * Metoda realizująca wypłatę w euro z konta.
     * @param amount kwota do wypłaty.
     * @return true jeśli operacja się powiodła, false w przeciwnym razie.
     */
    public static boolean eurWithdraw(String amount) {

        boolean result;

        try {
            out.writeObject(Operations.EUR_WITHDRAW);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            result = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Metoda realizująca wpłatę na konto.
     * @param amount kwota do wpłaty.
     * @return true jeśli operacja się powiodła, false w przeciwnym razie.
     */
    public static boolean deposit(String amount) {

        boolean result;

        try {
            out.writeObject(Operations.DEPOSIT);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            result = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Metoda zmieniająca numer PIN.
     * @param newPin1 nowy numer PIN.
     * @param newPin2 powtórzenie nowego numeru PIN.
     * @return true jeśli operacja się powiodła, false w przeciwnym razie.
     */
    public static boolean changePin(String newPin1, String newPin2) {

        boolean result;

        try {
            out.writeObject(Operations.CHANGE_PIN);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(newPin1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(newPin2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            result = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    /**
     * Metoda realizująca doładowanie telefonu.
     * @param phoneNumber numer telefonu do doładowania.
     * @param amount kwota doładowania.
     * @return true jeśli operacja się powiodła, false w przeciwnym razie.
     */
    public static boolean topUpPhone(String phoneNumber, String amount) {

        boolean result;

        try {
            out.writeObject(Operations.TOP_UP_PHONE);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(phoneNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            result = (boolean) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Metoda pobierająca listę transakcji.
     * @return lista transakcji.
     */
    public static List<String> getTransactionsList() {

        List<String> transactions;

        try {
            out.writeObject(Operations.SHOW_TRANSACTIONS);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }

        try {
            transactions = (List<String>) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;

    }

    /**
     * Metoda wyświetlająca komunikat o błędzie połączenia.
     */
    public static void printConnectionLost() {
        System.out.println("Connection error");
    }
}
