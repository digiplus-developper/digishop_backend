package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Adresse {
    @Id
    @GeneratedValue
    private Long id_adresse;
private String typeAdress;

    private String city_address;
    private String state_address;
    private String country_address;
    private String zipcode_address;

    private String quartier_address;
    private String secteurBloc_address;
    private String repere_address;
    private String phone_livraison;
    @JsonIgnore
    @OneToMany(mappedBy = "adresse")
    private List<Commande> commandeList;
    @JsonIgnore
@ManyToOne
private AppUser user;


    public Long getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(Long id_adresse) {
        this.id_adresse = id_adresse;
    }


    public String getPhone_livraison() {
        return phone_livraison;
    }

    public void setPhone_livraison(String phone_livraison) {
        this.phone_livraison = phone_livraison;
    }

    public String getTypeAdress() {
        return typeAdress;
    }

    public void setTypeAdress(String typeAdress) {
        this.typeAdress = typeAdress;
    }

    public String getCity_address() {
        return city_address;
    }

    public void setCity_address(String city_address) {
        this.city_address = city_address;
    }

    public String getState_address() {
        return state_address;
    }

    public void setState_address(String state_address) {
        this.state_address = state_address;
    }

    public String getCountry_address() {
        return country_address;
    }

    public void setCountry_address(String country_address) {
        this.country_address = country_address;
    }

    public String getZipcode_address() {
        return zipcode_address;
    }

    public void setZipcode_address(String zipcode_address) {
        this.zipcode_address = zipcode_address;
    }

    public String getQuartier_address() {
        return quartier_address;
    }

    public void setQuartier_address(String quartier_address) {
        this.quartier_address = quartier_address;
    }

    public String getSecteurBloc_address() {
        return secteurBloc_address;
    }

    public void setSecteurBloc_address(String secteurBloc_address) {
        this.secteurBloc_address = secteurBloc_address;
    }

    public String getRepere_address() {
        return repere_address;
    }

    public void setRepere_address(String repere_address) {
        this.repere_address = repere_address;
    }

    public List<Commande> getCommandeList() {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
