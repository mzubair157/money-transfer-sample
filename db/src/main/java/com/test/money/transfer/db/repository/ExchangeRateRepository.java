package com.test.money.transfer.db.repository;

import com.test.money.transfer.db.domain.ExchangeRate;

import java.util.Currency;

public interface ExchangeRateRepository {
    ExchangeRate findExchangeRateBySourceAndTarget(Currency source, Currency target);
}
