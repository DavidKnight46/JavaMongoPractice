package org.practice.basicmangodb.exceptions;

public class NoGamesFoundException extends RuntimeException{
    public NoGamesFoundException(String msg){
        super(msg);
    }
}
