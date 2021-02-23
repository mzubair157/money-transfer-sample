package com.test.money.transfer.db.exceptions;

public class ExchangeRateNotFoundException extends RuntimeException{
    public ExchangeRateNotFoundException() {
        super("Exchange rate not found");
    }
}
