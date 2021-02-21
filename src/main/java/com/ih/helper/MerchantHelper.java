package com.ih.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.*;

import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;
import com.ih.model.Merchant;

public class MerchantHelper {

    @Autowired
    private static BCryptPasswordEncoder bCryptPasswordEncoder;

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Merchant> csvToMerchantList(InputStream is) {
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
                merchant.setUsername(
                    bCryptPasswordEncoder.encode(csvRow.get("username"))
                );
                merchant.setPassword(csvRow.get("password"));
                merchant.setEmail(csvRow.get("email"));
                merchant.setStatus(
                    MerchantStatus.valueOf(csvRow.get("status").toLowerCase())
                );
                merchant.setRole(
                    MerchantRole.valueOf(csvRow.get("role").toLowerCase())
                );
                merchantList.add(merchant);
            }

            return merchantList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
