package com.ih.service;

import java.util.List;

import com.ih.model.Merchant;

public interface MerchantService {

    void save(Merchant merchant);

    void edit(Merchant merchant);

    void delete(Merchant merchant);

    Merchant findByUsername(String username);

    Merchant findByMerchantId(Long merchantId);

    List<Merchant> findAll();
}
