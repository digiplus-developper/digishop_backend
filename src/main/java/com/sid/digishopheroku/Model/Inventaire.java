package com.sid.digishopheroku.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Inventaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateInventaire;
    private String typeInventaire;
    @ManyToOne
    @JoinColumn(name = "id_boutique")
    private  Boutique boutique;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;
    @OneToMany(mappedBy = "inventaire")
    private List<InventaireItem> inventaireItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateInventaire() {
        return dateInventaire;
    }

    public void setDateInventaire(Date dateInventaire) {
        this.dateInventaire = dateInventaire;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<InventaireItem> getInventaireItemList() {
        return inventaireItemList;
    }

    public void setInventaireItemList(List<InventaireItem> inventaireItemList) {
        this.inventaireItemList = inventaireItemList;
    }

    public String getTypeInventaire() {
        return typeInventaire;
    }

    public void setTypeInventaire(String typeInventaire) {
        this.typeInventaire = typeInventaire;
    }
}
