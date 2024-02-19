package server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Klasa zarządzająca sesjami użytkowników
 * Przechowuje informacje o sesji użytkownika
 */
public class SessionManager {
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * Metoda zwracająca sesję użytkownika
     * @param clientId - identyfikator klienta
     * @return - sesja użytkownika
     */
    public static Session getSession(String clientId) {
        return sessions.computeIfAbsent(clientId, k -> new Session());
    }

    /**
     * Metoda kończąca sesję użytkownika
     * @param clientId - identyfikator klienta
     */
    public static void endSession(String clientId) {
        sessions.remove(clientId);
    }

    /**
     * Klasa reprezentująca sesję użytkownika
     */
    public static class Session {
        private int accountId;

        private int cardId;

        /**
         * Metoda zwracająca identyfikator konta
         * @return identyfikator konta
         */
        public int getAccountId() {
            return accountId;
        }

        /**
         * Metoda ustawiająca identyfikator konta
         * @param accountId - identyfikator konta
         */
        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        /**
         * Metoda zwracająca identyfikator karty
         * @return identyfikator karty
         */
        public int getCardId() {
            return cardId;
        }

        /**
         * Metoda ustawiająca identyfikator karty
         * @param cardId - identyfikator karty
         */
        public void setCardId(int cardId) {
            this.cardId = cardId;
        }

    }
}