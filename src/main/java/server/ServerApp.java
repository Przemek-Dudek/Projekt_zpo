package server;

//import db.entities.Grade;

import client.Operations;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
//import db.entities.Student;
//import db.entities.Subject;
//import db.repositories.StudentRepository;
//import db.repositories.SubjectRepository;

//Nasze
//import db.entities.Account;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    private static final int MAX_CLIENTS = 100;
//    private static final StudentRepository studentRepository = new StudentRepository();
//    private static final SubjectRepository subjectRepository = new SubjectRepository();

    public static void main(String[] args) {

//        try {
//            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);

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

    public static void handleOperation(Operations op, ObjectInputStream input, ObjectOutputStream output, String clientId) throws IOException, ClassNotFoundException {

//        String cardNumber = null;
//        int accountId;

        int cardId;
        boolean res;

        switch (op) {
            case VERIFY_CARD:

                // read card number from client
                String cardNumber = (String) input.readObject();

                // verify card number
                int accountId = Queries.verifyCard(cardNumber);

                // send result to client
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

                // read pin from client
                String pin = (String) input.readObject();

                cardId = SessionManager.getSession(clientId).getCardId();

                // verify pin
                boolean result = Queries.verifyPin(cardId, pin);

                // send result to client
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

    private static void handleClient(Socket socket) {
        ObjectInputStream in;
        ObjectOutputStream out;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
//            System.out.println(e.getMessage());
            return;
        }

        String clientId = socket.getRemoteSocketAddress().toString();

        while (true) {
            try {
                Operations op = (Operations) in.readObject();
                System.out.println(op);
                handleOperation(op, in, out, clientId);

//                in.close();
//                out.close();
//                socket.close();
            } catch (IOException e) {
//                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
