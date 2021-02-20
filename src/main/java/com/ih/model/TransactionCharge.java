package com.ih.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CHARGE")
public class TransactionCharge extends Transaction {

}
