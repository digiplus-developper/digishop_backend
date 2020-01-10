package com.sid.digishopheroku.WebRestfull.Forms;

public class InventaireForm {
    private Long idProduit;
    private Long idVendeur;
    private int quantityOnHand;

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public Long getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(Long idVendeur) {
        this.idVendeur = idVendeur;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
