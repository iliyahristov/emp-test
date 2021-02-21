package com.ih.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ih.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByUuid(UUID uuid);

    @Query("SELECT t FROM Transaction t WHERE t.createdOn < :date")
    List<Transaction> findAllOlderThenHour(@Param("date") java.sql.Date date);
}
