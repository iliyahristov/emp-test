package com.ih.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.ih.service.TransactionService;

@Controller
public class PaymentController {

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
        model.addAttribute("transactionList", transactionList);

        return "/transaction/transactionList";
    }

    @PostMapping("/api/transaction/pay")
    public ResponseEntity<ResponseMessage> initiatePayment(@RequestBody TransactionAuthorize transactionAuthorize, BindingResult bindingResult){
        Merchant merchant = merchantController.findLoggedUser();
        if (merchant != null && merchant.getRole().equals(MerchantRole.MERCHANT) && merchant.getStatus().equals(MerchantStatus.ACTIVE)){
            transactionAuthorize.setMerchant(merchant);
            transactionAuthorize.setStatus(TransactionStatus.APPROVED);
            transactionService.save(transactionAuthorize);
            // After some logic we can confirm the payment
            confirmPayment(transactionAuthorize);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your payment was placed successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("Your payment was declined"));
        }
    }

    public void confirmPayment(TransactionAuthorize transactionAuthorize){
        Merchant merchant = merchantController.findLoggedUser();
        if (merchant != null && merchant.getStatus().equals(MerchantStatus.ACTIVE)) {
            //Initiate Transaction
            TransactionCharge transactionCharge = new TransactionCharge();
            transactionCharge.setAmount(transactionAuthorize.getAmount());
            transactionCharge.setMerchant(transactionAuthorize.getMerchant());
            transactionCharge.setBelongsTo(transactionAuthorize);
            transactionCharge.setCustomerEmail(transactionAuthorize.getCustomerEmail());
            transactionCharge.setCustomerPhone(transactionAuthorize.getCustomerPhone());
            transactionCharge.setStatus(TransactionStatus.APPROVED);
            transactionService.save(transactionCharge);
            merchantController.increaseTotalSum(transactionAuthorize.getMerchant(), transactionAuthorize.getAmount());
            // Else Reversal transaction
        }
    }

    public void refundPayment(){
        TransactionRefund transactionRefund = new TransactionRefund();
        merchantController.decreaseTotalSum( transactionRefund.getMerchant(), transactionRefund.getAmount() );
    }
}
