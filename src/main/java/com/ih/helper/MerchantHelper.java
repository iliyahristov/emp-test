package com.ih.helper;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ih.model.Merchant;

public interface MerchantHelper {

    boolean hasCSVFormat(MultipartFile file);

    List<Merchant> csvToMerchantList(InputStream is);

}
