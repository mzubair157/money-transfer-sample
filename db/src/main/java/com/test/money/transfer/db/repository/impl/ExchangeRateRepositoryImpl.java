package com.test.money.transfer.db.repository.impl;

import com.test.money.transfer.db.domain.ExchangeRate;
import com.test.money.transfer.db.exceptions.ExchangeRateNotFoundException;
import com.test.money.transfer.db.repository.ExchangeRateRepository;

import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.test.money.transfer.db.domain.enums.Country.*;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {
    private final Map<String, ExchangeRate> database = new ConcurrentHashMap<>();

    public ExchangeRateRepositoryImpl() {
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .source(Currency.getInstance(ENGLAND.getCurrencyCode()))
                .target(Currency.getInstance(MALAYSIA.getCurrencyCode()))
                .rate(5.3d)
                .build();//UK to Malaysia
        database.put(exchangeRate.getId(), exchangeRate);

        exchangeRate = ExchangeRate.builder()
                .source(Currency.getInstance(AMERICA.getCurrencyCode()))
                .target(Currency.getInstance(INDONESIA.getCurrencyCode()))
                .rate(14135.7d)
                .build();//AMERICA to INDONESIA
        database.put(exchangeRate.getId(), exchangeRate);

        exchangeRate = ExchangeRate.builder()
                .source(Currency.getInstance(SINGAPORE.getCurrencyCode()))
                .target(Currency.getInstance(INDIA.getCurrencyCode()))
                .rate(54.8d)
                .build();//SINGAPORE to INDIA
        database.put(exchangeRate.getId(), exchangeRate);
    }

    @Override
    public ExchangeRate findExchangeRateBySourceAndTarget(Currency source, Currency target) {
        return database.entrySet().parallelStream()
                .filter(e -> e.getValue().getSource().equals(source) &&
                        e.getValue().getTarget().equals(target))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }
}
