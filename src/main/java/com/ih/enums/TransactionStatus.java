package com.ih.enums;

public enum TransactionStatus {
     APPROVED("approved"),REVERSED("reversed"),REFUNDED("refunded"),ERROR("error");

    private final String code;

    TransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
