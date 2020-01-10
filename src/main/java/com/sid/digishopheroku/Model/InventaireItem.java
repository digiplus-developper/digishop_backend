package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class InventaireItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantiteTheorique;
    private int quantitePhysique;//le prix vendu au client
    private int difference;
    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_inventaire")
    private Inventaire inventaire;


    public InventaireItem() {
    }

    public InventaireItem(int quantiteTheorique, int quantitePhysique, int difference) {
        this.quantiteTheorique = quantiteTheorique;
        this.quantitePhysique = quantitePhysique;
        this.difference = difference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantiteTheorique() {
        return produit.getQuantiteEnStock();
    }

    public void setQuantiteTheorique(int quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }

    public int getQuantitePhysique() {
        return quantitePhysique;
    }

    public void setQuantitePhysique(int quantitePhysique) {
        this.quantitePhysique = quantitePhysique;
    }

    public int getDifference() {
        return this.difference;

    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }


}
