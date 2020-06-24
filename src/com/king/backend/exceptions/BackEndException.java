package com.king.backend.exceptions;

public class BackEndException extends Exception {

    private static final long serialVersionUID = 1L;

    public static final String GENERIC_ERROR_MESSAGE = "Something wrong happened.";

       public BackEndException() {
        super();
    }

   
    public BackEndException(String msg) {
        super(msg);
    }


    public BackEndException(Exception ex) {
        super(ex);
    }
}
