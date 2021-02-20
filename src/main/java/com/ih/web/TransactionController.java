package com.ih.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ih.model.Transaction;
import com.ih.service.TransactionService;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction/transactionList")
    public String transactionList(Model model) {
        List<Transaction> transactionList = transactionService.findAll();
        model.addAttribute("transactionList", transactionList);

        return "/transaction/transactionList";
    }
}
