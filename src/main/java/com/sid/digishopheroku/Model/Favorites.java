package com.sid.digishopheroku.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*-------------- ---------------
     * MAPPING
     * --------------- -------------*/
    @OneToOne
    private AppUser appUser;
    @ManyToMany
    private List<Produit> produitsfavotisList;
    @ManyToMany
    private List<Boutique> boutiquefavorisList;

    /* Assesseurs */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Produit> getProduitsfavotisList() {
        return produitsfavotisList;
    }

    public void setProduitsfavotisList(List<Produit> produitsfavotisList) {
        this.produitsfavotisList = produitsfavotisList;
    }

    public List<Boutique> getBoutiquefavorisList() {
        return boutiquefavorisList;
    }

    public void setBoutiquefavorisList(List<Boutique> boutiquefavorisList) {
        this.boutiquefavorisList = boutiquefavorisList;
    }

    public Favorites(AppUser appUser, List<Produit> produitsfavotisList, List<Boutique> boutiquefavorisList) {
        this.appUser = appUser;
        this.produitsfavotisList = produitsfavotisList;
        this.boutiquefavorisList = boutiquefavorisList;
    }

    public Favorites() {

    }

}
