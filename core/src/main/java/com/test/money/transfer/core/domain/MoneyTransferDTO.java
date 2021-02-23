package com.test.money.transfer.core.domain;

import com.test.money.transfer.db.domain.enums.TransactionOutcome;
import com.test.money.transfer.db.domain.enums.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class MoneyTransferDTO implements Serializable {
    private String id;

    private TransactionState currentState;
    private TransactionOutcome currentStatus;

    private SenderDTO sender;
    private ReceiverDTO receiver;

    private Double amount;

    private String sourceCurrency;
    private String targetCurrency;
    private Double exchangeRate;

    private List<TransactionProcessDTO> transactionProcesses = new ArrayList<>();
}
