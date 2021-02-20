package com.ih.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AUTHORIZE")
public class TransactionAuthorize extends Transaction {

}
