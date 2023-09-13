package com.ibrahim.onlinebookshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExist.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyExists(EmailAlreadyExist emailAlreadyExists,
                                                                     WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                emailAlreadyExists.getMessage(),
                webRequest.getDescription(false),
                "Email is already Taken"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                            WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "User_Not_Found"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBookId.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(InvalidBookId invalidBookId,
                                                                            WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
               invalidBookId.getMessage(),
                webRequest.getDescription(false),
                "Invalid operation"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyOwned.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(AlreadyOwned alreadyOwned,
                                                                            WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                alreadyOwned.getMessage(),
                webRequest.getDescription(false),
                "Invalid Operation"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedPeople.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(UnAuthorizedPeople unAuthorizedPeople,
                                                                            WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                unAuthorizedPeople.getMessage(),
                webRequest.getDescription(false),
                "Invalid Operation"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFound.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(ReviewNotFound unAuthorizedPeople,
                                                                            WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                unAuthorizedPeople.getMessage(),
                webRequest.getDescription(false),
                "Invalid Operation"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }
}
