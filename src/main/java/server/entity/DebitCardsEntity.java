package server.entity;

import jakarta.persistence.*;

import java.sql.Date;


/**
 * Zwraca hashcode tego obiektu.
 * @return Hashcode tego obiektu.
 */
@Entity
@Table(name = "debitcards", schema = "atm")
public class DebitCardsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "account_id")
    private int accountId;
    @Basic
    @Column(name = "card_number")
    private String cardNumber;
    @Basic
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Basic
    @Column(name = "cvv")
    private String cvv;
    @Basic
    @Column(name = "pin")
    private String pin;

    /**
     * Zwraca ID karty debetowej.
     * @return ID karty.
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia ID karty debetowej.
     * @param id ID karty.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca ID konta, do którego przypisana jest karta.
     * @return ID konta.
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Ustawia ID konta, do którego przypisana jest karta.
     * @param accountId ID konta.
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    /**
     * Zwraca numer karty debetowej.
     * @return Numer karty.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Ustawia numer karty debetowej.
     * @param cardNumber Numer karty.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Zwraca datę ważności karty debetowej.
     * @return Data ważności karty.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Ustawia datę ważności karty debetowej.
     * @param expirationDate Data ważności karty.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Zwraca kod CVV karty debetowej.
     * @return Kod CVV.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Ustawia kod CVV karty debetowej.
     * @param cvv Kod CVV.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * Zwraca PIN karty debetowej.
     * @return PIN karty.
     */
    public String getPin() {
        return pin;
    }

    /**
     * Ustawia PIN karty debetowej.
     * @param pin PIN karty.
     */
    public void setPin(String pin) {
        this.pin = pin;
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

        DebitCardsEntity that = (DebitCardsEntity) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (cvv != null ? !cvv.equals(that.cvv) : that.cvv != null) return false;
        if (pin != null ? !pin.equals(that.pin) : that.pin != null) return false;

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
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (cvv != null ? cvv.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        return result;
    }
}
