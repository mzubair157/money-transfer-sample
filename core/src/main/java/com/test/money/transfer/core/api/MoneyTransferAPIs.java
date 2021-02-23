package com.test.money.transfer.core.api;

import com.google.gson.Gson;
import com.test.money.transfer.core.api.request.MarkAsCompleteRequest;
import com.test.money.transfer.core.api.request.MoneyTransferRequest;
import com.test.money.transfer.core.converter.APIConverter;
import com.test.money.transfer.core.domain.MoneyTransferDTO;
import com.test.money.transfer.db.domain.MoneyTransaction;
import com.test.money.transfer.db.exceptions.ExchangeRateNotFoundException;
import com.test.money.transfer.db.repository.ExchangeRateRepository;
import com.test.money.transfer.db.repository.TransactionRepository;
import com.test.money.transfer.db.repository.impl.ExchangeRateRepositoryImpl;
import com.test.money.transfer.db.repository.impl.TransactionRepositoryImpl;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class MoneyTransferAPIs {
    public static final String MONEY_TRANSFER_ENDPOINT = "/api/v1/money/transfers";
    public static final String APPLICATION_JSON = "application/json";
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();
    private final APIConverter apiConverter = new APIConverter(exchangeRateRepository);

    public MoneyTransferAPIs() {
        get(MONEY_TRANSFER_ENDPOINT, this::getMoneyTransfersForSender);

        post(MONEY_TRANSFER_ENDPOINT, this::createMoneyTransfer);

        put(MONEY_TRANSFER_ENDPOINT, this::markAsComplete);

        exception(ExchangeRateNotFoundException.class, (e, request, response) -> {
            response.status(400);
            response.body("Invalid source or destination currency");
        });

        exception(IllegalArgumentException.class, (e, request, response) -> {
            response.status(400);
            response.body("Invalid source or destination currency");
        });
    }

    private String markAsComplete(Request request, Response response) {
        response.type(APPLICATION_JSON);
        MarkAsCompleteRequest markAsCompleteRequest = new Gson().fromJson(request.body(), MarkAsCompleteRequest.class);
        transactionRepository.completeMoneyTransaction(markAsCompleteRequest.getId());

        return new Gson().toJson("successfully completed.");
    }

    private String createMoneyTransfer(Request request, Response response) {
        response.type(APPLICATION_JSON);
        MoneyTransferDTO transactionDTO = saveAndReturnMoneyTransfer(request);
        return new Gson().toJson(transactionDTO);
    }

    private MoneyTransferDTO saveAndReturnMoneyTransfer(Request request) {
        MoneyTransferRequest mt = new Gson().fromJson(request.body(), MoneyTransferRequest.class);
        MoneyTransaction transaction = apiConverter.convertRequestToEntity(mt);
        transactionRepository.save(transaction);
        return apiConverter.convertToDTO(transaction);
    }

    private String getMoneyTransfersForSender(Request request, Response response) {
        response.type(APPLICATION_JSON);
        String senderIdentificationNumber = request.queryParams("senderID");
        List<MoneyTransferDTO> transfersHistory = Optional.ofNullable(transactionRepository.findBySender(senderIdentificationNumber))
                .stream().flatMap(Collection::stream)
                .map(apiConverter::convertToDTO).collect(Collectors.toUnmodifiableList());

        return new Gson().toJson(transfersHistory);
    }
}
