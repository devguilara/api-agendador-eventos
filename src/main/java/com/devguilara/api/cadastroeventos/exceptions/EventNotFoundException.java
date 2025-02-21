package com.devguilara.api.cadastroeventos.exceptions;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(
            String message
    ) {
        super(message);
    }
}
