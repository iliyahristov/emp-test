package com.ih.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.uuid.Generators;
import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;
import com.ih.enums.TransactionStatus;
import com.ih.message.ResponseMessage;
import com.ih.model.Merchant;
import com.ih.model.Transaction;
import com.ih.model.TransactionAuthorize;
import com.ih.model.TransactionCharge;
import com.ih.model.TransactionRefund;
import com.ih.model.TransactionReversal;
import com.ih.service.TransactionService;

@Controller
public class PaymentController {
    private Merchant merchant;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantController merchantController;

    @GetMapping("/transaction/transactionList")
    public String transactionList(Model model) {
        List<Transaction> transactionList = new ArrayList<>();
        Merchant merchant = merchantController.findLoggedUser();
        if (merchant != null) {
            if (merchant.getRole().equals(MerchantRole.ADMIN)) {
                transactionList = transactionService.findAll();
            } else {
                transactionList = merchant.getTransactionList();
            }
        }
        model.addAttribute("transactionList",
            transactionList.stream()
            .filter(transaction -> transaction instanceof TransactionAuthorize)
            .collect(Collectors.toList()));
        model.addAttribute("isAdmin", merchantController.isAdmin());
        return "/transaction/transactionList";
    }

    @PostMapping("/api/transaction/pay")
    public ResponseEntity<ResponseMessage> initiatePayment(@RequestBody TransactionAuthorize transactionAuthorize, BindingResult bindingResult){

        if (isMerchantAbleToMakeTransactions()){
            transactionAuthorize.setMerchant(merchant);
            transactionAuthorize.setStatus(TransactionStatus.APPROVED);
            transactionService.save(transactionAuthorize);
            // After some logic we can confirm the payment
            if (transactionAuthorize.getStatus().equals(TransactionStatus.APPROVED)) {
                confirmPayment(transactionAuthorize);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your payment was placed successfully"));
            } else {
                reversePayment(transactionAuthorize);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("There was an error with your payment"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("Your payment was declined"));
        }

    }


    @PostMapping("/api/transaction/refund/{referenceId}")
    public ResponseEntity<ResponseMessage> refundPayment(@PathVariable(name = "referenceId") Integer referenceId){
        if (isMerchantAbleToMakeTransactions()) {
            TransactionRefund transactionRefund = new TransactionRefund();
            TransactionAuthorize transactionAuthorize = transactionService.findByReferenceId(referenceId);
            if (transactionAuthorize != null && transactionAuthorize.getStatus().equals(TransactionStatus.APPROVED)) {
                asignChield(transactionRefund, transactionAuthorize);
                transactionAuthorize.setStatus(TransactionStatus.REFUNDED);

                transactionAuthorize.getMerchant().setTotalTransactionSum(
                    transactionAuthorize.getMerchant().getTotalTransactionSum() - transactionAuthorize.getAmount()
                );

                transactionService.update(transactionAuthorize);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("The payment was refunded successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("We can not find your transaction"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.LOCKED).body(new ResponseMessage("That merchant is not allowed to make transactions at this moment"));
        }
    }

    private Boolean isMerchantAbleToMakeTransactions(){
        merchant = merchantController.findLoggedUser();
        return merchant != null && merchant.getRole().equals(MerchantRole.MERCHANT) && merchant.getStatus().equals(MerchantStatus.ACTIVE);
    }

    private void confirmPayment(TransactionAuthorize transactionAuthorize){
        TransactionCharge transactionCharge = new TransactionCharge();

        asignChield(transactionCharge, transactionAuthorize);

        transactionAuthorize.getMerchant().setTotalTransactionSum(
            transactionAuthorize.getMerchant().getTotalTransactionSum() + transactionAuthorize.getAmount()
        );

        transactionService.update(transactionAuthorize);
    }

    private void reversePayment(TransactionAuthorize transactionAuthorize){
        TransactionReversal transactionReversal = new TransactionReversal();

        asignChield(transactionReversal, transactionAuthorize);

        transactionAuthorize.setStatus(TransactionStatus.REVERSED);

        transactionService.update(transactionAuthorize);
    }

    private void asignChield(Transaction transaction, TransactionAuthorize transactionAuthorize){
        transaction.setAmount(transactionAuthorize.getAmount());
        transaction.setMerchant(transactionAuthorize.getMerchant());
        transaction.setBelongsTo(transactionAuthorize);
        transaction.setCustomerEmail(transactionAuthorize.getCustomerEmail());
        transaction.setCustomerPhone(transactionAuthorize.getCustomerPhone());
        transaction.setStatus(TransactionStatus.APPROVED);
        transaction.setCreatedOn(new Date());
        transaction.setUuid(Generators.timeBasedGenerator().generate());

        if (transactionAuthorize.getTransactionList() == null){
            transactionAuthorize.setTransactionList(new ArrayList<>());
        }
        transactionAuthorize.getTransactionList().add(transaction);
    }
}
