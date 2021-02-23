package com.test.money.transfer.db.domain;

import com.test.money.transfer.db.domain.enums.TransactionOutcome;
import com.test.money.transfer.db.domain.enums.TransactionState;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MoneyTransaction implements Serializable {
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private final String id = UUID.randomUUID().toString();

    private TransactionState currentState;
    private TransactionOutcome currentStatus;

    private Sender sender;
    private Receiver receiver;

    private Double amount;

    private ExchangeRate exchangeRate;

    @Builder.Default
    private List<TransactionProcess> transactionProcesses = new ArrayList<>();
}
