package com.test.money.transfer.db.repository.impl;

import com.test.money.transfer.db.domain.MoneyTransaction;
import com.test.money.transfer.db.domain.TransactionProcess;
import com.test.money.transfer.db.domain.enums.TransactionOutcome;
import com.test.money.transfer.db.domain.enums.TransactionState;
import com.test.money.transfer.db.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<String, MoneyTransaction> database = new ConcurrentHashMap<>();

    @Override
    public MoneyTransaction save(MoneyTransaction transaction) {
        transaction.setCurrentState(TransactionState.RECEIVED);
        transaction.getTransactionProcesses().add(TransactionProcess.builder()
                .transactionState(TransactionState.RECEIVED)
                .timestamp(LocalDateTime.now())
                .build());
        return database.put(transaction.getId(), transaction);
    }

    @Override
    public void completeMoneyTransaction(String id) {
        Optional<MoneyTransaction> moneyTransactionOptional = Optional.ofNullable(database.get(id));

        moneyTransactionOptional.ifPresent(this::markAsComplete);
    }

    private void markAsComplete(MoneyTransaction moneyTransaction) {
        moneyTransaction.setCurrentState(TransactionState.COMPLETE);
        moneyTransaction.setCurrentStatus(TransactionOutcome.SUCCESS);

        moneyTransaction.getTransactionProcesses().add(TransactionProcess.builder()
                .transactionState(TransactionState.PROCESSING)
                .timestamp(LocalDateTime.now())
                .build());

        moneyTransaction.getTransactionProcesses().add(TransactionProcess.builder()
                .transactionState(TransactionState.COMPLETE)
                .timestamp(LocalDateTime.now())
                .build());

        database.put(moneyTransaction.getId(), moneyTransaction);
    }

    @Override
    public List<MoneyTransaction> findBySender(String senderIdentificationNumber) {
        return database.entrySet().parallelStream()
                .filter(m -> m.getValue().getSender().getGovtIdentificationNumber().equals(senderIdentificationNumber))
                .map(Map.Entry::getValue)
                .collect(Collectors.toUnmodifiableList());
    }
}
