package com.ih.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ih.model.Merchant;
import com.ih.service.MerchantService;

@Component
public class MerchantEditValidator implements Validator {
    @Autowired
    private MerchantService merchantService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Merchant.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Merchant merchant = (Merchant) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (merchant.getUsername().length() < 6 || merchant.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.merchant.username");
        }
    }
}
