package com.test.money.transfer.core.domain;

import com.test.money.transfer.db.domain.enums.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Track each state change, during entire money transaction lifecycle, to track every change in it.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionProcessDTO {
    private TransactionState transactionState;
    private String timestamp;
}
