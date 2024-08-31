package org.practice.basicmangodb.exceptions;

public class UnableToAddGameException extends RuntimeException{
    public UnableToAddGameException(String msg){
        super(msg);
    }
}
