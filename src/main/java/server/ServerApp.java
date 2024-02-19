package server;


import client.ConnectionManager;
import client.Operations;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa serwera
 * Uruchamia serwer, oczekuje na połączenia i przekazuje je do obsługi w osobnych wątkach
 */
public class ServerApp {
    private static final int MAX_ATMS = 2;
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_ATMS);

        try (ServerSocket serverSocket = new ServerSocket(32777)) {
            System.out.println("Serwer uruchomiony, oczekiwanie na połączenia...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nowe połączenie od klienta: " + socket.getInetAddress());

                executorService.execute(() -> handleClient(socket));
            }
        } catch (IOException e) {
            System.out.println("Błąd serwera: " + e.getMessage());
        }
    }

    /**
     * Metoda obsługująca operacje klienta
     * @param op - operacja do wykonania
     * @param input - strumień wejściowy
     * @param output - strumień wyjściowy
     * @param clientId - identyfikator klienta
     * @throws IOException - wyjątek wejścia/wyjścia
     * @throws ClassNotFoundException - wyjątek klasa nie znaleziona
     */
    public static void handleOperation(Operations op, ObjectInputStream input, ObjectOutputStream output, String clientId) throws IOException, ClassNotFoundException {

        int cardId;
        boolean res;

        switch (op) {
            case VERIFY_CARD:

                String cardNumber = (String) input.readObject();

                int accountId = Queries.verifyCard(cardNumber);

                if (accountId > 0) {
                    output.writeObject(true);
                } else {
                    output.writeObject(false);
                }

                SessionManager.getSession(clientId).setAccountId(accountId);
                cardId = Queries.getCardId(cardNumber);
                SessionManager.getSession(clientId).setCardId(cardId);

                break;

            case VERIFY_PIN:

                String pin = (String) input.readObject();

                cardId = SessionManager.getSession(clientId).getCardId();

                boolean result = Queries.verifyPin(cardId, pin);

                output.writeObject(result);
                break;

            case SHOW_BALANCE:
                accountId = SessionManager.getSession(clientId).getAccountId();
                double balance = Queries.getBalance(accountId);
                output.writeObject(balance);
                break;

            case WITHDRAW:
                accountId = SessionManager.getSession(clientId).getAccountId();
                String amountString = (String) input.readObject();
                double amount = Double.parseDouble(amountString);
                res = Queries.withdraw(accountId, amount);
                output.writeObject(res);
                break;

            case DEPOSIT:
                accountId = SessionManager.getSession(clientId).getAccountId();
                amountString = (String) input.readObject();
                amount = Double.parseDouble(amountString);
                res = Queries.deposit(accountId, amount);
                output.writeObject(res);
                break;

            case EUR_WITHDRAW:
                accountId = SessionManager.getSession(clientId).getAccountId();
                amountString = (String) input.readObject();
                amount = Double.parseDouble(amountString);
                res = Queries.eurWithdraw(accountId, amount);
                output.writeObject(res);
                break;

            case CHANGE_PIN:
                cardId = SessionManager.getSession(clientId).getCardId();
                String newPin1 = (String) input.readObject();
                String newPin2 = (String) input.readObject();
                if (!newPin1.equals(newPin2)) {
                    output.writeObject(false);
                    break;
                }
                res = Queries.changePin(cardId, newPin1);
                output.writeObject(res);
                break;

            case TOP_UP_PHONE:
                accountId = SessionManager.getSession(clientId).getAccountId();
                String phone = (String) input.readObject();
                amountString = (String) input.readObject();
                amount = Double.parseDouble(amountString);
                res = Queries.topUpPhone(accountId, phone, amount);
                output.writeObject(res);
                break;

            case SHOW_TRANSACTIONS:

                accountId = SessionManager.getSession(clientId).getAccountId();
                List<String> transactions = Queries.getTransactions(accountId);
                output.writeObject(transactions);
                break;

            default:
                System.out.println("Nieznana operacja");
        }
    }

    /**
     * Metoda obsługująca klienta
     * @param socket - gniazdo
     */
    private static void handleClient(Socket socket) {
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String clientId = socket.getRemoteSocketAddress().toString();

        while (true) {
            try {
                Operations op = (Operations) in.readObject();
                System.out.println(op);
                handleOperation(op, in, out, clientId);


            } catch (IOException e) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();

                } finally {
                    return;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
