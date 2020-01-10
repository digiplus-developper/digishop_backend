package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
public class CashTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionType;
    private double amount;
    private double lastTransaction;
    private Date dateTransaction;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_cashssesion")
    private CashSession cashSession;

    public CashTransaction() {
    }

    public CashTransaction(String transactionType, double amount, double lastTransaction, Date dateTransaction, String comment) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.lastTransaction = lastTransaction;
        this.dateTransaction = dateTransaction;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(double lastTransaction) {
        this.lastTransaction = lastTransaction;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CashSession getCashSession() {
        return cashSession;
    }

    public void setCashSession(CashSession cashSession) {
        this.cashSession = cashSession;
    }
}
