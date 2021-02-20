package com.ih.enums;

public enum MerchantStatus {
     ACTIVE("active"),INACTIVE("inactive");

    private final String code;

    MerchantStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
