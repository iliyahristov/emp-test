package com.ih.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.ih.model.Transaction;
import com.ih.model.TransactionAuthorize;
import com.ih.model.TransactionCharge;
import com.ih.model.TransactionRefund;
import com.ih.model.TransactionReversal;
import com.ih.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transaction.setCreatedOn(new java.util.Date());
        transaction.setUuid(Generators.timeBasedGenerator().generate());
        transactionRepository.save(transaction);
    }
    public void update(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void delete(Transaction transaction){
        transactionRepository.deleteById(transaction.getTransactionId());
    }

    @Override
    public Transaction findByUuid(UUID uuid) {
        return transactionRepository.findByUuid(uuid);
    }

    @Override
    public TransactionAuthorize findByReferenceId(Integer referenceId) {
        return transactionRepository.findByReferenceId(referenceId);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findAllOlderThenHour(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Date oneHour = new java.sql.Date(cal.getTimeInMillis());

        return transactionRepository.findAllOlderThenHour(oneHour);
    }

    protected TransactionAuthorize initiateTransaction(){
        TransactionAuthorize result = new TransactionAuthorize();

        return result;
    }

    protected TransactionCharge chargeTramsaction(){
        TransactionCharge result = new TransactionCharge();

        return result;
    }

    protected TransactionRefund refundTramsaction(){
        TransactionRefund result = new TransactionRefund();

        return result;
    }

    protected TransactionReversal reversalTramsaction(){
        TransactionReversal result = new TransactionReversal();

        return result;
    }

}
