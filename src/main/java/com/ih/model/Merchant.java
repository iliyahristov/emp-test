package com.ih.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import com.ih.enums.MerchantRole;
import com.ih.enums.MerchantStatus;

@Entity
@Table(name = "merchant")
public class Merchant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="merchant_id")
    private Long merchantId;

    @Enumerated(EnumType.STRING)
    private MerchantRole role;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    @Max(100)
    private String name;

    @Max(255)
    private String description;

    @Email
    private String email;

    @Column(name = "total_transaction_sum")
    private Integer totalTransactionSum;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public MerchantRole getRole() {
        return role;
    }

    public void setRole(MerchantRole role) {
        this.role = role;
    }

    public MerchantStatus getStatus() {
        return status;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTotalTransactionSum() {
        return totalTransactionSum;
    }

    public void setTotalTransactionSum(Integer totalTransactionSum) {
        this.totalTransactionSum = totalTransactionSum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
