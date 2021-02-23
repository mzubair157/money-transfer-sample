package com.test.money.transfer.db.domain.enums;

import lombok.Getter;

public enum Country {
    ENGLAND("GBP"),
    AMERICA("USD"),
    SINGAPORE("SGD"),

    MALAYSIA("MYR"),
    INDONESIA("IDR"),
    INDIA("INR");

    @Getter
    private final String currencyCode;

    Country(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
