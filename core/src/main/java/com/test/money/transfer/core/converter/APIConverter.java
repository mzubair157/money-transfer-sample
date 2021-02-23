package com.test.money.transfer.core.converter;

import com.test.money.transfer.core.api.request.MoneyTransferRequest;
import com.test.money.transfer.core.domain.MoneyTransferDTO;
import com.test.money.transfer.core.domain.ReceiverDTO;
import com.test.money.transfer.core.domain.SenderDTO;
import com.test.money.transfer.core.domain.TransactionProcessDTO;
import com.test.money.transfer.db.domain.ExchangeRate;
import com.test.money.transfer.db.domain.MoneyTransaction;
import com.test.money.transfer.db.domain.Receiver;
import com.test.money.transfer.db.domain.Sender;
import com.test.money.transfer.db.exceptions.ExchangeRateNotFoundException;
import com.test.money.transfer.db.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class APIConverter {
    private final ExchangeRateRepository exchangeRateRepository;

    public MoneyTransaction convertRequestToEntity(MoneyTransferRequest mt) throws ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateBySourceAndTarget(
                Currency.getInstance(mt.getSourceCurrency()),
                Currency.getInstance(mt.getTargetCurrency()));

        Sender sender = Sender.builder()
                .fullName(mt.getSenderName())
                .mobileNumber(mt.getSenderPhoneNumber())
                .govtIdentificationNumber(mt.getSenderIdentificationNumber())
                .build();

        Receiver receiver = Receiver.builder()
                .fullName(mt.getReceiverName())
                .mobileNumber(mt.getReceiverPhoneNumber())
                .govtIdentificationNumber(mt.getReceiverIdentificationNumber())
                .build();

        return MoneyTransaction.builder()
                .exchangeRate(exchangeRate)
                .sender(sender)
                .receiver(receiver)
                .amount(mt.getAmount())
                .build();
    }

    public MoneyTransferDTO convertToDTO(MoneyTransaction transaction) {
        return MoneyTransferDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .currentState(transaction.getCurrentState())
                .currentStatus(transaction.getCurrentStatus())
                .exchangeRate(transaction.getExchangeRate().getRate())
                .receiver(ReceiverDTO.builder()
                        .fullName(transaction.getReceiver().getFullName())
                        .govtIdentificationNumber(transaction.getReceiver().getGovtIdentificationNumber())
                        .mobileNumber(transaction.getReceiver().getMobileNumber())
                        .build())
                .sender(SenderDTO.builder()
                        .fullName(transaction.getSender().getFullName())
                        .govtIdentificationNumber(transaction.getSender().getGovtIdentificationNumber())
                        .mobileNumber(transaction.getSender().getMobileNumber())
                        .build())
                .transactionProcesses(transaction.getTransactionProcesses().parallelStream()
                        .map(tx -> TransactionProcessDTO.builder().transactionState(tx.getTransactionState())
                                .timestamp(tx.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME)).build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }
}
