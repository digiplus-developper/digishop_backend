package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Caisse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_caise;
    private String referenceCommande;
    private Date date=new Date();
    private double solde;//ceci defini ce qui est reellement en caisse
    @JsonIgnore
    @OneToOne(mappedBy = "caisse")
    private Boutique boutique;
    @JsonIgnore
    @OneToMany(mappedBy = "caisse")
    private List<CashSession> cashSessionList;

    public Long getId_caise() {
        return id_caise;
    }

    public void setId_caise(Long id_caise) {
        this.id_caise = id_caise;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getReferenceCommande() {
        return referenceCommande;
    }

    public void setReferenceCommande(String referenceCommande) {
        this.referenceCommande = referenceCommande;
    }

    public List<CashSession> getCashSessionList() {
        return cashSessionList;
    }

    public void setCashSessionList(List<CashSession> cashSessionList) {
        this.cashSessionList = cashSessionList;
    }
}
