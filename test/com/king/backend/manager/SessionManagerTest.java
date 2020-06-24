package com.king.backend.manager;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.king.backend.models.Session;

public class SessionManagerTest {


    SessionManager sessionManager;

    @Before
    public void setUp() throws Exception {
        sessionManager = new SessionManager();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateNewSession() throws Exception {
        Session session = sessionManager.createNewSession(42);
        System.out.println("session.getUserId() = " + session.getUserId());
        System.out.println("session.getCreatedTime() = " + session.getCreatedTime());
        System.out.println("session.getSessionKey() = " + session.getSessionKey());
        System.out.println("session = " + session);
        Assert.assertNotNull("Session Invalid", session.getSessionKey());
    }

    @Test
    public void testIsSessionValid() throws Exception {
        Session session = sessionManager.createNewSession(42);
        System.out.println("session = " + session);
        String sessionKey = session.getSessionKey();
        boolean valid = sessionManager.isSessionValid(sessionKey);
        Assert.assertTrue("Session Invalid", valid);

    }

    @Test
    public void testIsSessionInvalid() throws Exception {
        String sessionKey = UUID.randomUUID().toString().replace("-", "");
        boolean valid = sessionManager.isSessionValid(sessionKey);
        Assert.assertFalse("Session Invalid", valid);
    }


    @Test
    public void testIsSessionTimeOut() throws Exception {
        Session session = sessionManager.createNewSession(42);
        System.out.println("session = " + session);
        String sessionKey = session.getSessionKey();
        boolean valid = sessionManager.isSessionValid(sessionKey);
        Assert.assertTrue("Session Invalid", valid);
        System.out.println("Wait from = " + new Date());
        TimeUnit.MILLISECONDS.sleep(SessionManager.TIME_TO_LIVE - 1001);
        System.out.println("       to =" + new Date());
        System.out.println("Waited = " + (SessionManager.TIME_TO_LIVE + 1001) + " millis");
        boolean valid1 = sessionManager.isSessionValid(sessionKey);
        Assert.assertTrue("Session Invalid", valid1);
        TimeUnit.MILLISECONDS.sleep(1001);
        boolean invalid = sessionManager.isSessionValid(sessionKey);
        Assert.assertFalse("Session Invalid", invalid);
    }


}
