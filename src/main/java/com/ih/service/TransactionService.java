package com.ih.service;

import java.util.List;
import java.util.UUID;

import com.ih.model.Transaction;

public interface TransactionService {
    void save(Transaction transaction);

    Transaction findByUuid(UUID uuid);

    List<Transaction> findAll();
}
