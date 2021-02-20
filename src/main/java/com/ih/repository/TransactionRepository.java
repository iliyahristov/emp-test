package com.ih.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ih.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByUuid(UUID uuid);
}
