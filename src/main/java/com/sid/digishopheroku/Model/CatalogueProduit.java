package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class CatalogueProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private CategorieProduit categorieProduit;
    @JsonIgnore
    @OneToMany(mappedBy = "catalogueProduit")
    private List<Produit> produits;
    @Column(unique = true)
    private String nomProduit;

    private String genreClient;
    @Column(columnDefinition = "text")
    private String image;

    public CatalogueProduit() {
    }

    public CatalogueProduit(CategorieProduit categorieProduit, String nomProduit, String genreClient, String image) {
        this.categorieProduit = categorieProduit;
        this.nomProduit = nomProduit;
        this.genreClient = genreClient;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieProduit getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(CategorieProduit categorieProduit) {
        this.categorieProduit = categorieProduit;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getGenreClient() {
        return genreClient;
    }

    public void setGenreClient(String genreClient) {
        this.genreClient = genreClient;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

