package com.ih.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ih.enums.MerchantStatus;
import com.ih.model.Merchant;
import com.ih.model.Transaction;
import com.ih.repository.MerchantRepository;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Merchant merchant){
        merchant.setPassword(bCryptPasswordEncoder.encode(merchant.getPassword()));
        merchant.setStatus(MerchantStatus.ACTIVE);
        merchantRepository.save(merchant);
    }

    @Override
    public Merchant findByUsername(String username) {
        return merchantRepository.findByUsername(username);
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }
}
