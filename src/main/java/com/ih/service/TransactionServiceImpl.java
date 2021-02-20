package com.ih.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ih.model.Transaction;
import com.ih.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void save(Transaction merchant) {
        transactionRepository.save(merchant);
    }

    @Override
    public Transaction findByUuid(UUID uuid) {
        return transactionRepository.findByUuid(uuid);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
