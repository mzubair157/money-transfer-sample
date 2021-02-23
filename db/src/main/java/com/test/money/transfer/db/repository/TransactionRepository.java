package com.test.money.transfer.db.repository;

import com.test.money.transfer.db.domain.MoneyTransaction;

import java.util.List;

public interface TransactionRepository {
    MoneyTransaction save(MoneyTransaction transaction);

    void completeMoneyTransaction(String id);

    List<MoneyTransaction> findBySender(String senderID);
}
