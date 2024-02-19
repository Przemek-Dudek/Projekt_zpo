package server.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Klasa TransactionsEntity reprezentuje tabelę transactions w bazie danych.
 * Zawiera pola odpowiadające kolumnom w tabeli oraz metody dostępowe do tych pól.
 */
@Entity
@Table(name = "transactions", schema = "atm")
public class TransactionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "account_id")
    private int accountId;
    @Basic
    @Column(name = "transaction_type")
    private String transactionType;
    @Basic
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic
    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    /**
     * Zwraca ID transakcji.
     * @return ID transakcji.
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia ID transakcji.
     * @param id ID transakcji.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca ID konta, na którym wykonano transakcję.
     * @return ID konta.
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Ustawia ID konta, na którym wykonano transakcję.
     * @param accountId ID konta.
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    /**
     * Zwraca typ transakcji.
     * @return Typ transakcji.
     */
    public Object getTransactionType() {
        return transactionType;
    }

    /**
     * Ustawia typ transakcji.
     * @param transactionType Typ transakcji.
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Zwraca kwotę transakcji.
     * @return Kwota transakcji.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Ustawia kwotę transakcji.
     * @param amount Kwota transakcji.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Zwraca datę i czas wykonania transakcji.
     * @return Data i czas wykonania transakcji.
     */
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    /**
     * Ustawia datę i czas wykonania transakcji.
     * @param transactionDate Data i czas wykonania transakcji.
     */
    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Porównuje ten obiekt z podanym obiektem.
     * @param o Obiekt do porównania.
     * @return true jeśli obiekty są równe, false w przeciwnym wypadku.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionsEntity that = (TransactionsEntity) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        if (transactionType != null ? !transactionType.equals(that.transactionType) : that.transactionType != null)
            return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;

        return true;
    }


    /**
     * Zwraca hashcode tego obiektu.
     * @return Hashcode tego obiektu.
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountId;
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        return result;
    }
}
