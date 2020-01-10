package com.sid.digishopheroku.WebRestfull.Forms;

public class CashTransactionForm {
    private Long idVendeur;
    private double montant;
    private String commentaire;
    private Long id_cashsession;

    public Long getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(Long idVendeur) {
        this.idVendeur = idVendeur;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getId_cashsession() {
        return id_cashsession;
    }

    public void setId_cashsession(Long id_cashsession) {
        this.id_cashsession = id_cashsession;
    }
}
