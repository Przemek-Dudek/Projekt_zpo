package server;

import server.entity.AccountsEntity;
import server.entity.DebitCardsEntity;
import server.entity.TransactionsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Queries zawiera metody do wykonywania operacji na bazie danych.
 */
public class Queries {

    /**
     * Metoda do weryfikacji numeru karty.
     * @param cardNumber Numer karty do weryfikacji.
     * @return Zwraca identyfikator konta powiązanego z kartą.
     */
    public static int verifyCard(String cardNumber) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        int accountId;

        try {
            transaction.begin();

            DebitCardsEntity debitCardsEntity = entityManager.createQuery("SELECT d FROM DebitCardsEntity d WHERE d.cardNumber = :cardNumber", DebitCardsEntity.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();

            if (debitCardsEntity != null) {
                accountId = debitCardsEntity.getAccountId();
            } else {
                accountId = 0;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            accountId = 0;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return accountId;
    }

    /**
     * Metoda do weryfikacji numeru PIN.
     * @param cardId Identyfikator karty.
     * @param pin Numer PIN do weryfikacji.
     * @return Zwraca true, jeśli numer PIN jest poprawny, w przeciwnym razie false.
     */
    public static boolean verifyPin(int cardId, String pin) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        boolean result = false;

        try {
            transaction.begin();

            DebitCardsEntity debitCardsEntity = entityManager.find(DebitCardsEntity.class, cardId);

            if (debitCardsEntity.getPin().equals(pin)) {
                result = true;

            } else {
                result = false;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do pobierania identyfikatora karty.
     * @param cardNumber Numer karty.
     * @return Zwraca identyfikator karty.
     */
    public static int getCardId(String cardNumber) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        int cardId;

        try {
            transaction.begin();

            DebitCardsEntity debitCardsEntity = entityManager.createQuery("SELECT d FROM DebitCardsEntity d WHERE d.cardNumber = :cardNumber", DebitCardsEntity.class)
                    .setParameter("cardNumber", cardNumber)
                    .getSingleResult();

            if (debitCardsEntity != null) {
                cardId = debitCardsEntity.getId();
            } else {
                cardId = 0;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            cardId = 0;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return cardId;
    }

    /**
     * Metoda do pobierania salda konta.
     * @param accountId Identyfikator konta.
     * @return Zwraca saldo konta.
     */
    public static double getBalance(int accountId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        double balance;

        try {
            transaction.begin();

            balance = entityManager.find(AccountsEntity.class, accountId).getBalance().doubleValue();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            balance = 0;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return balance;
    }

    /**
     * Metoda do realizacji wypłaty.
     * @param accountId Identyfikator konta.
     * @param amount Kwota do wypłaty.
     * @return Zwraca true, jeśli operacja się powiodła, w przeciwnym razie false.
     */
    public static boolean withdraw(int accountId, double amount) {


        if (amount <= 0) return false;
        if (amount % 50 != 0) return false;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        double balance;

        boolean result = false;

        try {
            transaction.begin();

            balance = entityManager.find(AccountsEntity.class, accountId).getBalance().doubleValue();

            if (balance >= amount) {
                balance -= amount;
                entityManager.find(AccountsEntity.class, accountId).setBalance(BigDecimal.valueOf(balance));
                // new transaction insert into query
                TransactionsEntity transactionsEntity = new TransactionsEntity();
                transactionsEntity.setAccountId(accountId);
                transactionsEntity.setTransactionType("withdrawal");
                transactionsEntity.setAmount(BigDecimal.valueOf(amount));
                entityManager.persist(transactionsEntity);

                transaction.commit();
                result = true;
            } else {
                transaction.rollback();
                result = false;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do realizacji wpłaty.
     * @param accountId Identyfikator konta.
     * @param amount Kwota do wpłaty.
     * @return Zwraca true, jeśli operacja się powiodła, w przeciwnym razie false.
     */
    public static boolean deposit(int accountId, double amount) {

        if (amount <= 0) return false;
        if (amount % 10 != 0) return false;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        double balance;

        boolean result = false;

        try {
            transaction.begin();

            balance = entityManager.find(AccountsEntity.class, accountId).getBalance().doubleValue();


            balance += amount;
            entityManager.find(AccountsEntity.class, accountId).setBalance(BigDecimal.valueOf(balance));

            TransactionsEntity transactionsEntity = new TransactionsEntity();
            transactionsEntity.setAccountId(accountId);
            transactionsEntity.setTransactionType("deposit");
            transactionsEntity.setAmount(BigDecimal.valueOf(amount));
            entityManager.persist(transactionsEntity);

            transaction.commit();
            result = true;


            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do realizacji wypłaty w euro.
     * @param accountId Identyfikator konta.
     * @param amount Kwota do wypłaty.
     * @return Zwraca true, jeśli operacja się powiodła, w przeciwnym razie false.
     */
    public static boolean eurWithdraw(int accountId, double amount) {

        if (amount <= 0) return false;
        if (amount % 50 != 0) return false;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        double balance;

        boolean result = false;

        double balanceInEur = 0;

        final double exchangeRate = 4.34;

        double amountInPln = amount * exchangeRate;

        try {
            transaction.begin();

            balance = entityManager.find(AccountsEntity.class, accountId).getBalance().doubleValue();

            balanceInEur = balance / exchangeRate;

            if (balanceInEur >= amount) {
                balance -= amountInPln;
                entityManager.find(AccountsEntity.class, accountId).setBalance(BigDecimal.valueOf(balance));

                TransactionsEntity transactionsEntity = new TransactionsEntity();
                transactionsEntity.setAccountId(accountId);
                transactionsEntity.setTransactionType("withdrawal");
                transactionsEntity.setAmount(BigDecimal.valueOf(amountInPln));
                entityManager.persist(transactionsEntity);

                transaction.commit();
                result = true;
            } else {
                transaction.rollback();
                result = false;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do zmiany numeru PIN.
     * @param cardId Identyfikator karty.
     * @param newPin Nowy numer PIN.
     * @return Zwraca true, jeśli operacja się powiodła, w przeciwnym razie false.
     */
    public static boolean changePin(int cardId, String newPin) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        boolean result = false;

        try {
            transaction.begin();

            DebitCardsEntity debitCardsEntity = entityManager.find(DebitCardsEntity.class, cardId);
            debitCardsEntity.setPin(newPin);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do doładowania telefonu.
     * @param accountId Identyfikator konta.
     * @param phoneNumber Numer telefonu do doładowania.
     * @param amount Kwota doładowania.
     * @return Zwraca true, jeśli operacja się powiodła, w przeciwnym razie false.
     */
    public static boolean topUpPhone(int accountId, String phoneNumber, double amount) {

        if (amount <= 0) return false;
        if (amount % 1 != 0) return false;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        double balance;

        boolean result = false;

        try {
            transaction.begin();

            balance = entityManager.find(AccountsEntity.class, accountId).getBalance().doubleValue();

            if (balance >= amount) {
                balance -= amount;
                entityManager.find(AccountsEntity.class, accountId).setBalance(BigDecimal.valueOf(balance));

                TransactionsEntity transactionsEntity = new TransactionsEntity();
                transactionsEntity.setAccountId(accountId);
                transactionsEntity.setTransactionType("top-up");
                transactionsEntity.setAmount(BigDecimal.valueOf(amount));
                entityManager.persist(transactionsEntity);

                transaction.commit();
                result = true;
            } else {
                transaction.rollback();
                result = false;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    /**
     * Metoda do pobierania listy transakcji.
     * @param accountId Identyfikator konta.
     * @return Zwraca listę transakcji.
     */
    public static List<String> getTransactions(int accountId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        ArrayList<String> transactions = new ArrayList<>();

        try {
            transaction.begin();

            List<TransactionsEntity> transactionsEntities = entityManager.createQuery("SELECT t FROM TransactionsEntity t WHERE t.accountId = :accountId", TransactionsEntity.class)
                    .setParameter("accountId", accountId)
                    .getResultList();

            int i = 1;
            for (TransactionsEntity t : transactionsEntities) {
                transactions.add(i + ". " + t.getTransactionType() + " " + t.getAmount() + " PLN " + t.getTransactionDate());
                i++;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return transactions;
    }

}
