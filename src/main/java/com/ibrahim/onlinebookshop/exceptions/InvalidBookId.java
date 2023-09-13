package com.ibrahim.onlinebookshop.exceptions;

public class InvalidBookId extends RuntimeException{

    public InvalidBookId(String message) {
        super(message);
    }
}
