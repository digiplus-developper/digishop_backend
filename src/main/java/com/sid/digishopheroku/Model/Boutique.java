package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class Boutique implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoutique;
    @Column(unique=true)
    private String nomBoutique;
    private String adressBoutique;
    @Column(unique=true)
    private String emailBoutique;
    @Column(unique=true)
    private String telephoneBoutique;
    private String photoboutique;
    private int nbredevues;
    private int nbreDeFavoris;

    @OneToMany(mappedBy = "boutique")
    @JsonIgnore
    private List<Commande> commandeList;
    @JsonIgnore
    @OneToMany(mappedBy = "boutiquesProduit")
    private List<Produit> listProduitBoutique;
    @JsonIgnore
    @OneToMany(mappedBy = "boutique")
    private Collection<AppUser> appusers;

    @OneToOne
    @JoinColumn(name = "id_caise")
    private Caisse caisse;
    @JsonIgnore
    @ManyToMany
    private List<Favorites> favorites;
    @JsonIgnore
    @OneToMany(mappedBy = "boutique")
    private List<Inventaire> inventaireList;
@OneToMany
@JsonIgnore
private List<Rating>  ratings;


    public Boutique(String nomBoutique, String adressBoutique, String emailBoutique, String telephoneBoutique, List<Produit> listProduitBoutique) {
        this.nomBoutique = nomBoutique;
        this.adressBoutique = adressBoutique;
        this.emailBoutique = emailBoutique;
        this.telephoneBoutique = telephoneBoutique;
        this.listProduitBoutique = listProduitBoutique;

    }

    public Boutique() {
    }



    public Collection<AppUser> getAppusers() {
        return appusers;
    }

    public void setAppusers(Collection<AppUser> appusers) {
        this.appusers = appusers;
    }

    public Long getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Long idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getNomBoutique() {
        return nomBoutique;
    }

    public void setNomBoutique(String nomBoutique) {
        this.nomBoutique = nomBoutique;
    }

    public String getAdressBoutique() {
        return adressBoutique;
    }

    public void setAdressBoutique(String adressBoutique) {
        this.adressBoutique = adressBoutique;
    }

    public String getEmailBoutique() {
        return emailBoutique;
    }

    public void setEmailBoutique(String emailBoutique) {
        this.emailBoutique = emailBoutique;
    }

    public String getTelephoneBoutique() {
        return telephoneBoutique;
    }

    public void setTelephoneBoutique(String telephoneBoutique) {
        this.telephoneBoutique = telephoneBoutique;
    }

    public List<Produit> getListProduitBoutique() {
        return listProduitBoutique;
    }

    public void setListProduitBoutique(List<Produit> listProduitBoutique) {
        this.listProduitBoutique = listProduitBoutique;
    }

    public String getPhotoboutique() {
        return photoboutique;
    }

    public void setPhotoboutique(String photoboutique) {
        this.photoboutique = photoboutique;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public int getNbredevues() {
        return nbredevues;
    }

    public void setNbredevues(int nbredevues) {
        this.nbredevues = nbredevues;
    }

    public int getNbreDeFavoris() {
        return nbreDeFavoris;
    }

    public void setNbreDeFavoris(int nbreDeFavoris) {
        this.nbreDeFavoris = nbreDeFavoris;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public List<Commande> getCommandeList() {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }

    public List<Inventaire> getInventaireList() {
        return inventaireList;
    }

    public void setInventaireList(List<Inventaire> inventaireList) {
        this.inventaireList = inventaireList;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
