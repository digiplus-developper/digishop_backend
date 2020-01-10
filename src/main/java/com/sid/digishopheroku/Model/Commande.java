package com.sid.digishopheroku.Model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity

public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcommande;
    private String referencecommande;
    @OneToMany(mappedBy = "commandeitem")
    private List<CartItem> cartitemscomand=new ArrayList<>();
    private double GrandTotal;
    private String nomvendeur;
    private String statut;

    private double fraislivraison;//c'est cxe code qui permet de confirmer que le produit a été livré
    private Date dateCommande = new Date();
    @ManyToOne
    @JoinColumn(name = "id_user")//ici c'est l'id de la personne qui passe la commande
    private AppUser usercommande;
    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;
    @ManyToOne
    @JoinColumn(name = "id_Paiement")
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "idBoutique")
    private Boutique boutique;

    @OneToMany
    private List<InventaireItem> inventaireItemList;

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Long getIdcommande() {
        return idcommande;
    }

    public void setIdcommande(Long idcommande) {
        this.idcommande = idcommande;
    }

    public AppUser getUsercommande() {
        return usercommande;
    }

    public void setUsercommande(AppUser usercommande) {
        this.usercommande = usercommande;
    }

    public String getNomvendeur() {
        return nomvendeur;
    }

    public void setNomvendeur(String nomvendeur) {
        this.nomvendeur = nomvendeur;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) { this.payment = payment; }


    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public String getReferencecommande() {
        return referencecommande;
    }

    public void setReferencecommande(String referencecommande) {
        this.referencecommande = referencecommande;
    }

    public List<CartItem> getCartitemscomand() {
        return cartitemscomand;
    }

    public void setCartitemscomand(List<CartItem> cartitemscomand) {
        this.cartitemscomand = cartitemscomand;
    }

    public List<InventaireItem> getInventaireItemList() {
        return inventaireItemList;
    }

    public void setInventaireItemList(List<InventaireItem> inventaireItemList) {
        this.inventaireItemList = inventaireItemList;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        GrandTotal = grandTotal;
    }

    public double getFraislivraison() {
        return fraislivraison;
    }

    public void setFraislivraison(double fraislivraison) {
        this.fraislivraison = fraislivraison;
    }
}
