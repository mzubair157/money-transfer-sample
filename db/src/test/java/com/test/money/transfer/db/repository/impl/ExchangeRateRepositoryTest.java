package com.test.money.transfer.db.repository.impl;

import com.test.money.transfer.db.domain.ExchangeRate;
import com.test.money.transfer.db.exceptions.ExchangeRateNotFoundException;
import com.test.money.transfer.db.repository.ExchangeRateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Currency;

import static com.test.money.transfer.db.domain.enums.Country.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class ExchangeRateRepositoryTest {
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();

    @Test
    public void findUKtoMalaysia(){
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateBySourceAndTarget(
                Currency.getInstance(ENGLAND.getCurrencyCode()),
                Currency.getInstance(MALAYSIA.getCurrencyCode()));

        assertNotNull(exchangeRate);
        assertEquals(ENGLAND.getCurrencyCode(), exchangeRate.getSource().getCurrencyCode());
        assertEquals(MALAYSIA.getCurrencyCode(), exchangeRate.getTarget().getCurrencyCode());
        assertEquals(5.3d, exchangeRate.getRate(), 0.0d);
    }

    @Test
    public void findAmericatoIndonesia(){
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateBySourceAndTarget(
                Currency.getInstance(AMERICA.getCurrencyCode()),
                Currency.getInstance(INDONESIA.getCurrencyCode()));

        assertNotNull(exchangeRate);
        assertEquals(AMERICA.getCurrencyCode(), exchangeRate.getSource().getCurrencyCode());
        assertEquals(INDONESIA.getCurrencyCode(), exchangeRate.getTarget().getCurrencyCode());
        assertEquals(14135.7d, exchangeRate.getRate(), 0.0d);
    }

    @Test
    public void findSingaporetoIndia(){
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateBySourceAndTarget(
                Currency.getInstance(SINGAPORE.getCurrencyCode()),
                Currency.getInstance(INDIA.getCurrencyCode()));

        assertNotNull(exchangeRate);
        assertEquals(SINGAPORE.getCurrencyCode(), exchangeRate.getSource().getCurrencyCode());
        assertEquals(INDIA.getCurrencyCode(), exchangeRate.getTarget().getCurrencyCode());
        assertEquals(54.8d, exchangeRate.getRate(), 0.0d);
    }

    @Test(expected = ExchangeRateNotFoundException.class)
    public void noExchangeRate(){
        exchangeRateRepository.findExchangeRateBySourceAndTarget(Currency.getInstance(AMERICA.getCurrencyCode()), Currency.getInstance(ENGLAND.getCurrencyCode()));
    }
}
