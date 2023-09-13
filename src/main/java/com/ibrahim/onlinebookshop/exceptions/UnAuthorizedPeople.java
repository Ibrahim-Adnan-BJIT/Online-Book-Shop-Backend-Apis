package com.ibrahim.onlinebookshop.exceptions;

public class UnAuthorizedPeople extends RuntimeException{
    public UnAuthorizedPeople(String message) {
        super(message);
    }
}
