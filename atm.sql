DROP
DATABASE IF EXISTS atm;

CREATE
DATABASE atm;

USE
atm;

CREATE TABLE Accounts
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    first_name     VARCHAR(50)        NOT NULL,
    last_name      VARCHAR(50)        NOT NULL,
    balance        DECIMAL(10, 2) DEFAULT 0
);

CREATE TABLE Transactions
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    account_id       INT            NOT NULL,
    transaction_type ENUM('deposit', 'withdrawal','top-up') NOT NULL,
    amount           DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts (id)
);

CREATE TABLE DebitCards
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    account_id      INT                NOT NULL,
    card_number     VARCHAR(16) UNIQUE NOT NULL,
    expiration_date DATE               NOT NULL,
    cvv             VARCHAR(3)         NOT NULL,
    pin             VARCHAR(4)         NOT NULL,
    FOREIGN KEY (account_id) REFERENCES Accounts (id)
);


-- Dodawanie przykładowych kont
INSERT INTO Accounts (account_number, first_name, last_name, balance)
VALUES ('12345678901234567890123456', 'Łukasz', 'Stanisławowski', 11000.00),
       ('98765432109876543210987654', 'Jan', 'Kowalski', 2500.00),
       ('99111122223333444455556666', 'Hanna', 'Nowak', 32000.00);

-- Dodawanie przykładowych transakcji
-- Wpłaty na konto o numerze '123456789'
INSERT INTO Transactions (account_id, transaction_type, amount)
VALUES (1, 'deposit', 500.00),
       (1, 'deposit', 300.00),
       (1, 'withdrawal', 200.00);

-- Wypłaty z konta o numerze '987654321'
INSERT INTO Transactions (account_id, transaction_type, amount)
VALUES (2, 'withdrawal', 100.00),
       (2, 'deposit', 50.00);

-- Wpłaty i wypłaty na konto o numerze '1111222233334444'
INSERT INTO Transactions (account_id, transaction_type, amount)
VALUES (3, 'deposit', 1000.00),
       (3, 'withdrawal', 500.00),
       (3, 'deposit', 700.00);

-- Dodajemy przykładowe karty debetowe dla kont w bazie danych

-- Przykładowe karty dla konta o id = 1
INSERT INTO DebitCards (account_id, card_number, expiration_date, cvv, pin)
VALUES (1, '1234567812345678', '2026-12-31', '123', '5678'),
       (1, '8765432187654321', '2024-01-31', '456', '8888');

-- Przykładowe karty dla konta o id = 2
INSERT INTO DebitCards (account_id, card_number, expiration_date, cvv, pin)
VALUES (2, '9876543298765432', '2027-10-30', '789', '4444'),
       (2, '2345678923456789', '2024-04-30', '321', '3333');

-- Przykładowe karty dla konta o id = 3
INSERT INTO DebitCards (account_id, card_number, expiration_date, cvv, pin)
VALUES (3, '3456789034567890', '2025-08-31', '654', '1234');