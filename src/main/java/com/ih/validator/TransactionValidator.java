package com.ih.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ih.model.Transaction;
import com.ih.service.TransactionService;

@Component
public class TransactionValidator implements Validator {
    @Autowired
    private TransactionService transactionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Transaction.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Transaction transaction = (Transaction) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "NotEmpty");
        if (transaction.getAmount() > 0) {
            errors.rejectValue("amount", "Size.merchantForm.username");
        }

    }
}
