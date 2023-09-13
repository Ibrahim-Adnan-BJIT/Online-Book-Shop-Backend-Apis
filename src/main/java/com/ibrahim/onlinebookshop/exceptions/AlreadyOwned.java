package com.ibrahim.onlinebookshop.exceptions;

public class AlreadyOwned extends RuntimeException{
    public AlreadyOwned(String message) {
        super(message);
    }
}
