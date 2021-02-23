package com.test.money.transfer.db.repository.impl;

import com.test.money.transfer.db.domain.ExchangeRate;
import com.test.money.transfer.db.domain.MoneyTransaction;
import com.test.money.transfer.db.domain.Receiver;
import com.test.money.transfer.db.domain.Sender;
import com.test.money.transfer.db.repository.ExchangeRateRepository;
import com.test.money.transfer.db.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static com.test.money.transfer.db.domain.enums.Country.ENGLAND;
import static com.test.money.transfer.db.domain.enums.Country.MALAYSIA;
import static com.test.money.transfer.db.domain.enums.TransactionOutcome.SUCCESS;
import static com.test.money.transfer.db.domain.enums.TransactionState.COMPLETE;
import static com.test.money.transfer.db.domain.enums.TransactionState.RECEIVED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JUnit4.class)
public class TransactionRepositoryTest {
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();

    @Test
    public void addAMoneyTransfer(){
        MoneyTransaction moneyTransaction = getMoneyTransaction();
        MoneyTransaction transaction = transactionRepository.save(moneyTransaction);

        //indicates that a new record inserted
        assertNull(transaction);
        verifyDataInDatabase(senderIdentificationNumber);
    }

    @Test
    public void completeAndVerifyTransaction(){
        MoneyTransaction moneyTransaction = getMoneyTransaction();

        MoneyTransaction transaction = transactionRepository.save(moneyTransaction);
        //indicates that a new record inserted
        assertNull(transaction);

        List<MoneyTransaction> transactions = transactionRepository.findBySender(senderIdentificationNumber);
        assertEquals(1, transactions.size());

        transaction = transactions.get(0);
        transactionRepository.completeMoneyTransaction(transaction.getId());
        verifyCompletedTransaction(transaction);

    }

    private void verifyCompletedTransaction(MoneyTransaction transaction) {
        assertEquals(COMPLETE, transaction.getCurrentState());
        assertEquals(SUCCESS, transaction.getCurrentStatus());
        assertEquals(1000d, transaction.getAmount(), 0.0d);

        assertEquals(3, transaction.getTransactionProcesses().size());
    }

    private MoneyTransaction getMoneyTransaction() {
        Sender sender = new Sender();
        sender.setVerified(true);
        sender.setFullName("New Sender");
        sender.setMobileNumber("+123456789");
        sender.setGovtIdentificationNumber(senderIdentificationNumber);

        Receiver receiver = new Receiver();
        receiver.setFullName("New Receiver");
        receiver.setMobileNumber("+987654321");
        receiver.setGovtIdentificationNumber(UUID.randomUUID().toString());

        Currency source = Currency.getInstance(ENGLAND.getCurrencyCode());
        Currency target = Currency.getInstance(MALAYSIA.getCurrencyCode());
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateBySourceAndTarget(source, target);

        Double amount = 1000d;

        return MoneyTransaction.builder()
                .amount(amount)
                .sender(sender)
                .receiver(receiver)
                .exchangeRate(exchangeRate)
                .build();
    }

    private void verifyDataInDatabase(String senderIdentificationNumber) {
        List<MoneyTransaction> transactions = transactionRepository.findBySender(senderIdentificationNumber);
        assertEquals(1, transactions.size());

        MoneyTransaction transaction = transactions.get(0);
        assertEquals(RECEIVED, transaction.getCurrentState());
        assertNull(transaction.getCurrentStatus());
        assertEquals(1000d, transaction.getAmount(), 0.0d);
    }

    private final String senderIdentificationNumber = UUID.randomUUID().toString().replace("-", "");
}
