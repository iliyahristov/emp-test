package com.ih.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;
import com.ih.model.Merchant;

@Controller
public class MerchantHelperImpl implements MerchantHelper {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static String TYPE = "text/csv";

    @Override
    public boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    @Override
    public List<Merchant> csvToMerchantList(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {

            List<Merchant> merchantList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRow : csvRecords) {

                Merchant merchant = new Merchant();
                merchant.setName(csvRow.get("name"));
                merchant.setDescription(csvRow.get("description"));
                merchant.setUsername(csvRow.get("username"));
                merchant.setPassword(
                    bCryptPasswordEncoder.encode(csvRow.get("password"))
                );
                merchant.setEmail(csvRow.get("email"));
                merchant.setStatus(
                    MerchantStatus.valueOf(csvRow.get("status").toUpperCase())
                );
                merchant.setRole(
                    MerchantRole.valueOf(csvRow.get("role").toUpperCase())
                );
                merchant.setTotalTransactionSum(0);
                merchantList.add(merchant);
            }

            return merchantList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
