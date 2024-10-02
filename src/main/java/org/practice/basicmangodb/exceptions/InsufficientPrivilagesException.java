package org.practice.basicmangodb.exceptions;

public class InsufficientPrivilagesException extends RuntimeException{
    public InsufficientPrivilagesException(String msg){
        super(msg);
    }
}
