package com.ih.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ih.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Merchant findByUsername(String username);

    Merchant findByMerchantId(Long merchantId);

}
