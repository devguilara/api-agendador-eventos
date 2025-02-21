package com.devguilara.api.cadastroeventos.DTO;

public class SubscriptionConflictException extends RuntimeException {
    public SubscriptionConflictException(String message) {
        super(message);
    }
}
