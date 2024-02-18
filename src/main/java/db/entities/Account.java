package db.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "accounts", schema = "bazazpo")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "balance_pln", nullable = false)
    private BigDecimal balancePLN;

    @Column(name = "balance_eur", nullable = false)
    private BigDecimal balanceEUR;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "senderAccount")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AccountOperation> sentOperations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receiverAccount")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AccountOperation> receivedOperations = new LinkedHashSet<>();

    public Account(String accountNumber, String name, String surname, BigDecimal balancePLN, BigDecimal balanceEUR, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.surname = surname;
        this.balancePLN = balancePLN;
        this.balanceEUR = balanceEUR;
        this.phoneNumber = phoneNumber;
    }

    public Account() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getBalancePLN() {
        return balancePLN;
    }

    public void setBalancePLN(BigDecimal balancePLN) {
        this.balancePLN = balancePLN;
    }

    public BigDecimal getBalanceEUR() {
        return balanceEUR;
    }

    public void setBalanceEUR(BigDecimal balanceEUR) {
        this.balanceEUR = balanceEUR;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<AccountOperation> getSentOperations() {
        return sentOperations;
    }

    public void setSentOperations(Set<AccountOperation> sentOperations) {
        this.sentOperations = sentOperations;
    }

    public Set<AccountOperation> getReceivedOperations() {
        return receivedOperations;
    }

    public void setReceivedOperations(Set<AccountOperation> receivedOperations) {
        this.receivedOperations = receivedOperations;
    }
}
