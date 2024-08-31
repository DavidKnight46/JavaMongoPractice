package org.practice.basicmangodb.exceptions;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException(String msg){
        super(msg);
    }
}
