package com.ih.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REVERSAL")
public class TransactionReversal extends Transaction {

}
