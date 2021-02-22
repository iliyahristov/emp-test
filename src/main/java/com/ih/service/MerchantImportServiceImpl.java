package com.ih.service;

import java.io.IOException;
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
            merchantRepository.saveAll(merchantList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
