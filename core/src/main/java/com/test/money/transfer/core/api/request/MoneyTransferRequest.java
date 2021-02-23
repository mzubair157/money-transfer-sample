package com.test.money.transfer.core.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class MoneyTransferRequest implements Serializable {
    private String senderName;
    private String senderIdentificationNumber;
    private String senderPhoneNumber;


    private String receiverName;
    private String receiverIdentificationNumber;
    private String receiverPhoneNumber;

    private String sourceCurrency;
    private String targetCurrency;

    private Double amount;
}
