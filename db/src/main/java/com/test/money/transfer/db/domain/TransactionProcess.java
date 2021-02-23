package com.test.money.transfer.db.domain;

import com.test.money.transfer.db.domain.enums.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Track each state change, during entire money transaction lifecycle, to track every change in it.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionProcess {
    private TransactionState transactionState;
    private LocalDateTime timestamp;
}
