package com.ih.service;

import java.util.List;
import java.util.UUID;

import com.ih.model.Transaction;
import com.ih.model.TransactionAuthorize;

public interface TransactionService {
    void save(Transaction transaction);

    void update(Transaction transaction);

    void delete(Transaction transaction);

    Transaction findByUuid(UUID uuid);

    TransactionAuthorize findByReferenceId(Integer referenceId);

    List<Transaction> findAll();

    List<Transaction> findAllOlderThenHour();

}
