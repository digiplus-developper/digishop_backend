package com.sid.digishopheroku.WebRestfull.Forms;

public class PaymentDTO {
    private double montantPayment;
    private String typepayment;

    public String getTypepayment() {
        return typepayment;
    }

    public void setTypepayment(String typepayment) {
        this.typepayment = typepayment;
    }

    public double getMontantPayment() {
        return montantPayment;
    }

    public void setMontantPayment(double montantPayment) {
        this.montantPayment = montantPayment;
    }
}
