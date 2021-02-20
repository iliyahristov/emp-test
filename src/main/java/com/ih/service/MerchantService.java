package com.ih.service;

import java.util.List;

import com.ih.model.Merchant;

public interface MerchantService {

    void save(Merchant merchant);

    Merchant findByUsername(String username);

    List<Merchant> findAll();
}
