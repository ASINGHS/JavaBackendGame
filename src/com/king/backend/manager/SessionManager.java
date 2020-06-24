package com.king.backend.manager;

import com.king.backend.models.Session;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class SessionManager {

   
    public static final int TIME_TO_LIVE = 600000;

   // ConcurrentMap<sessionKey, Session> with all the Sessions actives in the server.
     
    private final ConcurrentMap<String, Session> sessionActives;

   
    public SessionManager() {
        sessionActives = new ConcurrentHashMap<>();
    }

   
     //Method used to create a new Session for the selected userId
   
    public synchronized Session createNewSession(final Integer userId) {
        final long now = System.currentTimeMillis();
        final String newSessionKey = UUID.randomUUID().toString().replace("-", "");
        final Session session = new Session(userId, newSessionKey, now);
        sessionActives.put(newSessionKey, session);
        System.out.println(sessionActives);
        return session;
    }

    //Method used to validate if an sessionKey is associated with a Valid and Active Session in the Server
     
    public synchronized boolean isSessionValid(final String sessionKey) {
        Session sessionActive = sessionActives.get(sessionKey);
        if (sessionActive != null) {
            if ((System.currentTimeMillis() - sessionActive.getCreatedTime()) > TIME_TO_LIVE) {
                sessionActives.remove(sessionKey);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //Method used to get the Session from the Map for the sessionKey selected
    
    public Session getSessionActive(final String sessionKey) {
        return sessionActives.get(sessionKey);
    }


    // Method used to remove all the invalid session from the server.
     
    public synchronized void removeInvalidSessions() {
        long now = System.currentTimeMillis();
        for (Session session : new ArrayList<>(sessionActives.values())) {
            if ((now - session.getCreatedTime()) > TIME_TO_LIVE) {
                sessionActives.remove(session.getSessionKey());
            }
        }
    }
}
