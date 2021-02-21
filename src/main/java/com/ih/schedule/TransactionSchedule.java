package com.ih.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ih.model.Transaction;
import com.ih.service.MerchantService;
import com.ih.service.TransactionService;

@Component
public class TransactionSchedule {

    @Autowired
    TransactionService transactionService;

    @Autowired
    MerchantService merchantService;

    @Scheduled(cron = "0 0 * * * *")
    public void reportCurrentTime() {
        List<Transaction> allOlderThenHour = transactionService.findAllOlderThenHour();
        allOlderThenHour.forEach( transaction -> {
                merchantService.decreaseTotalSum(transaction.getMerchant(), transaction.getAmount());
                transactionService.delete(transaction);
            }
        );
    }
}
