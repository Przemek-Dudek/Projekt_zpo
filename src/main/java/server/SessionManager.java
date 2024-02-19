package server;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session getSession(String clientId) {
        return sessions.computeIfAbsent(clientId, k -> new Session());
    }

    // end session
    public static void endSession(String clientId) {
        sessions.remove(clientId);
    }

    public static class Session {
        private int accountId;

        private int cardId;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getCardId() {
            return cardId;
        }

        public void setCardId(int cardId) {
            this.cardId = cardId;
        }

    }
}