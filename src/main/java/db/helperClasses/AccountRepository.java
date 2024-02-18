package db.helperClasses;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

import db.entities.Account;


public class AccountRepository {
    private Map<String, Account> accounts;

    public AccountRepository() {
        accounts = new HashMap<>();
    }

    public String createAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return "Account with the same number already exists.";
        } else {
            accounts.put(account.getAccountNumber(), account);
            return "Account created successfully.";
        }
    }

    public BigDecimal getAccountBalancePLN(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.getBalancePLN();
        } else {
            return null;
        }
    }

    public BigDecimal getAccountBalanceEUR(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.getBalanceEUR();
        } else {
            return null;
        }
    }
}
