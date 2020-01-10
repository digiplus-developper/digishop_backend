package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class CategorieProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idCategorie")
    private Long id;
   // @Column(unique = true)
    private String nomCategorie;
    private String classe;
    private String type;

    private String imageCategorie;
    @JsonIgnore
    @OneToMany(mappedBy = "categorieProduit")
    private List<CatalogueProduit> catalogueProduits;

    public CategorieProduit(String nomCategorie, String classe, String type, String gamme) {
        this.nomCategorie = nomCategorie;
        this.classe = classe;
        this.type = type;

    }

    public CategorieProduit(String nomCategorie, String classe, String type) {
        this.nomCategorie = nomCategorie;
        this.classe = classe;
        this.type = type;
    }

    public CategorieProduit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<CatalogueProduit> getCatalogueProduits() {
        return catalogueProduits;
    }

    public void setCatalogueProduits(List<CatalogueProduit> catalogueProduits) {
        this.catalogueProduits = catalogueProduits;
    }

    public String getImageCategorie() {
        return imageCategorie;
    }

    public void setImageCategorie(String imageCategorie) {
        this.imageCategorie = imageCategorie;
    }

}


