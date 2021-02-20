package com.ih.enums;

public enum MerchantRole {
     ADMIN("admin"),MERCHANT("merchant");

    private final String code;

    MerchantRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
