package com.sid.digishopheroku.Model;

import javax.persistence.*;


@Entity
@Table(name = "PayerLivraison")
@PrimaryKeyJoinColumn(name = "id_Paiement")
public class PayerLivraison extends Payment {

    private String referenceuser;
    private double montantCashdeli;
    public PayerLivraison() {
    }



    public double getMontantCashdeli() {
        return montantCashdeli;
    }

    public void setMontantCashdeli(double montantCashdeli) {
        this.montantCashdeli = montantCashdeli;
    }

    public String getReferenceuser() {
        return referenceuser;
    }

    public void setReferenceuser(String referenceuser) {
        this.referenceuser = referenceuser;
    }


}
