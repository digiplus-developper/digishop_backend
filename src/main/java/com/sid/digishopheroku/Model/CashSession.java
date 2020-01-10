package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
public class CashSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double initialAmount;
    private Date startDate;
    private Date endDate;
    private double closureTheoricalAmount;
    private double closurePhysicalAmount;
    private String cashStatus;
    private double solde;
    @JsonIgnore
    @OneToMany(mappedBy = "cashSession")
    List<CashTransaction> transactionList;
    @ManyToOne
    @JoinColumn(name = "id_caise")
    private Caisse caisse;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;

    public CashSession() {
    }

    public CashSession(double initialAmount, Date startDate, Date endDate, double closureTheoricalAmount, double closurePhysicalAmount, String cashStatus) {
        this.initialAmount = initialAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closureTheoricalAmount = closureTheoricalAmount;
        this.closurePhysicalAmount = closurePhysicalAmount;
        this.cashStatus = cashStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getClosureTheoricalAmount() {
        return closureTheoricalAmount;
    }

    public void setClosureTheoricalAmount(double closureTheoricalAmount) {
        this.closureTheoricalAmount = closureTheoricalAmount;
    }

    public double getClosurePhysicalAmount() {
        return closurePhysicalAmount;
    }

    public void setClosurePhysicalAmount(double closurePhysicalAmount) {
        this.closurePhysicalAmount = closurePhysicalAmount;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }

    public List<CashTransaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<CashTransaction> transactionList) {
        this.transactionList = transactionList;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
}
