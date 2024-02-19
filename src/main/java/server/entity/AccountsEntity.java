package server.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Klasa reprezentująca tabelę accounts
 * Zawiera pola tabeli oraz metody dostępowe do tych pól
 * Klasa jest mapowana na tabelę accounts w bazie danych
 */
@Entity
@Table(name = "accounts", schema = "atm")
public class AccountsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "account_number")
    private String accountNumber;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * Metoda zwracająca identyfikator konta
     * @return - identyfikator konta
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda ustawiająca identyfikator konta
     * @param id - identyfikator konta
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda zwracająca numer konta
     * @return - numer konta
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Metoda ustawiająca numer konta
     * @param accountNumber - numer konta
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Metoda zwracająca imię właściciela konta
     * @return  - imię właściciela konta
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Metoda ustawiająca imię właściciela konta
     * @param firstName - imię właściciela konta
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Metoda zwracająca nazwisko właściciela konta
     * @return - nazwisko właściciela konta
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Metoda ustawiająca nazwisko właściciela konta
     * @param lastName  - nazwisko właściciela konta
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Metoda zwracająca saldo konta
     * @return - saldo konta
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Metoda ustawiająca saldo konta
     * @param balance - saldo konta
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Metoda porównująca obiekty
     * @param o - obiekt do porównania
     * @return - true jeśli obiekty są równe, false w przeciwnym wypadku
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountsEntity that = (AccountsEntity) o;

        if (id != that.id) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;

        return true;
    }

    /**
     * Metoda zwracająca hashcode obiektu
     * @return - hashcode obiektu
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
