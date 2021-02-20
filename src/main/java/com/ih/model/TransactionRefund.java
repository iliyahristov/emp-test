package com.ih.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REFUND")
public class TransactionRefund extends Transaction {

}
