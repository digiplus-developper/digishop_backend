package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_Item;
    private int quantite;
    private double prixvendu;//le prix vendu au client
    private double subtotal=0;
    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart cartcartitem;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idCommande")
    private Commande commandeitem;



    public Long getId_Item() {
        return id_Item;
    }

    public void setId_Item(Long id_Item) {
        this.id_Item = id_Item;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Cart getCartcartitem() {
        return cartcartitem;
    }

    public void setCartcartitem(Cart cartcartitem) {
        this.cartcartitem = cartcartitem;
    }

    public Commande getCommandeitem() {
        return commandeitem;
    }

    public void setCommandeitem(Commande commandeitem) {
        this.commandeitem = commandeitem;
    }

    public double getPrixvendu() {
        return prixvendu;
    }

    public void setPrixvendu(double prixvendu) {
        this.prixvendu = prixvendu;
    }
}
