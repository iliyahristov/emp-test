package com.ih.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ih.helper.MerchantHelper;
import com.ih.model.Merchant;
import com.ih.repository.MerchantRepository;

@Service
public class MerchantImportServiceImpl {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantHelper merchantHelper;

    public void saveAll(MultipartFile file) {
        try {
            List<Merchant> merchantList = merchantHelper.csvToMerchantList(file.getInputStream());
            List<Merchant> merchantsListToSave = new ArrayList<>();
            merchantList.forEach( merchant -> {
                Merchant foundMerchant = merchantRepository.findByUsername(merchant.getUsername());
                if (foundMerchant == null){
                    merchantsListToSave.add(merchant);
                }
            });
            merchantRepository.saveAll(merchantsListToSave);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
