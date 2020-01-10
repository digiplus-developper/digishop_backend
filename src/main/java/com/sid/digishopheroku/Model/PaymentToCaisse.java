package com.sid.digishopheroku.Model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentToCaisse")
@PrimaryKeyJoinColumn(name = "id_Paiement")

public class PaymentToCaisse extends Payment{
    private String reference;
    private double montant;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
