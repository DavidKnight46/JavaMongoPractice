package org.practice.basicmangodb.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoGamesFoundException.class)
    public ResponseEntity<Object> handleNoGamesFoundException(NoGamesFoundException e, WebRequest request){
        return handleExceptionInternal(e,
                e.getMessage(),
                HttpHeaders.EMPTY,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(){
        return handleNoSuchElementException();
    }

    @ExceptionHandler(UnableToAddGameException.class)
    public ResponseEntity<Object> handleUnableToAddGameException(UnableToAddGameException e, WebRequest request){
        return handleExceptionInternal(e,
                e.getMessage(),
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<Object> handleNoUserFoundExceptionException(NoUserFoundException e, WebRequest request){
        return handleExceptionInternal(e,
                e.getMessage(),
                HttpHeaders.EMPTY,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(InsufficientPrivilagesException.class)
    public ResponseEntity<Object> handleInsufficientPrivilagesException(InsufficientPrivilagesException e, WebRequest request){
        return handleExceptionInternal(e,
                e.getMessage(),
                HttpHeaders.EMPTY,
                HttpStatus.FORBIDDEN,
                request);
    }
}
