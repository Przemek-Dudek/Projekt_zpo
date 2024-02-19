package client;


/**
 * Typ wyliczeniowy reprezentujący operacje, które może wykonać użytkownik.
 */
public enum Operations {
    VERIFY_CARD,
    VERIFY_PIN,

    SHOW_BALANCE,

    WITHDRAW,
    DEPOSIT,
    CHANGE_PIN,

    TOP_UP_PHONE,

    EUR_WITHDRAW,

    SHOW_TRANSACTIONS,

    END_SESSION

}
