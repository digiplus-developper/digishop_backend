package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
//@NoArgsConstructor

public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idProduit;
    private String nomProduit;

    private double prixProduit;
    private boolean afficherprix;
    private boolean appliqueduction=false;// cette variable permet de donner l'autorisation a un produit qui va subire une reduction
    private boolean appliqueReduction =false;// cette variable permet de donner l'autorisation a un produit qui va subir une reduction
    private String nomCategorie;
    private String referenceProduit;
    private String description;
    private String caracteristiques;

    private String photoProduit;
    private boolean arrivage=true;
    private String qcode="";
    public int quantity;
    private int quantiteEnStock;

    private String marque;
    private String gamme;

    private boolean active=false;//elle est à false car cest le proprietaire qui initie l'ajout d'un produit et l'admin central peut l'activer
    private double fraisLivraisonPointRelais;
    private double fraisLivraisonPointSpecifique;


    /* popularite du produit */
    private BigDecimal purchases;
    private int nbredevues;
    private int nbreDeFavoris;


    /* **************/
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date dateproduit=new Date();

    private  Date dateDebutPromotion=new Date();
    private  Date dateFinPromotion=new Date();
    private  double prixpromotionnel=0;
    private  double pourcentagepromotion=0;
    /* les promotions sur un produit*/
    private Boolean promoAcitve = Boolean.TRUE;

    @JsonIgnore
    @ManyToMany

    private List<Favorites> favorites;


    @ManyToOne
    @JoinColumn(name="idBoutique")
    private Boutique boutiquesProduit;
    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private Set<CartItem> cartItemProd;
    @JsonIgnore
    @OneToMany(mappedBy = "produit")
    private List<Produit> listproduitAssociers;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "listproduitAssociers")
    private Produit produit;
    @JsonIgnore
    @OneToOne
    private Stock stock;
    @OneToMany
    private List<InventaireItem> inventaireItemList;

    @ManyToOne
    @JoinColumn(name = "idcatalogue")
    private CatalogueProduit catalogueProduit;

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getPrixProduit() {
        Date currentDate = new Date();

        if (Boolean.TRUE.equals(this.getPromoAcitve()) && currentDate.after(dateDebutPromotion) && currentDate.before(dateFinPromotion)) {

            return this.prixProduit - (this.prixProduit * (this.pourcentagepromotion));
        }else {
            return prixProduit;
        }
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public CatalogueProduit getCatalogueProduit() {
        return catalogueProduit;
    }

    public void setCatalogueProduit(CatalogueProduit catalogueProduit) {
        this.catalogueProduit = catalogueProduit;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public double getPrixpromotionnel() {
        return prixpromotionnel;
    }

    public void setPrixpromotionnel(double prixpromotionnel) {
        this.prixpromotionnel = prixpromotionnel;
    }

    public boolean isAppliqueReduction() {
        return appliqueReduction;
    }

    public void setAppliqueReduction(boolean appliqueReduction) {
        this.appliqueReduction = appliqueReduction;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getPhotoProduit() {
        return photoProduit;
    }

    public void setPhotoProduit(String photoProduit) {
        this.photoProduit = photoProduit;
    }

    public boolean isArrivage() {
        return arrivage;
    }

    public void setArrivage(boolean arrivage) {
        this.arrivage = arrivage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantiteEnStock() {
        return this.quantiteEnStock;
       // return stock.getTotalStock();
    }

    public void setQuantiteEnStock(int quantiteEnStock) {
        this.quantiteEnStock = quantiteEnStock;
    }

    public int getNbredevues() {
        return nbredevues;
    }

    public void setNbredevues(int nbredevues) {
        this.nbredevues = nbredevues;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getFraisLivraisonPointRelais() {
        return fraisLivraisonPointRelais;
    }

    public void setFraisLivraisonPointRelais(double fraisLivraisonPointRelais) {
        this.fraisLivraisonPointRelais = fraisLivraisonPointRelais;
    }

    public double getFraisLivraisonPointSpecifique() {
        return fraisLivraisonPointSpecifique;
    }

    public void setFraisLivraisonPointSpecifique(double fraisLivraisonPointSpecifique) {
        this.fraisLivraisonPointSpecifique = fraisLivraisonPointSpecifique;
    }

    public BigDecimal getPurchases() {
        return purchases;
    }

    public void setPurchases(BigDecimal purchases) {
        this.purchases = purchases;
    }

    public int getNbreDeFavoris() {
        return nbreDeFavoris;
    }

    public void setNbreDeFavoris(int nbreDeFavoris) {
        this.nbreDeFavoris = nbreDeFavoris;
    }

    public Date getDateproduit() {
        return dateproduit;
    }

    public void setDateproduit(Date dateproduit) {
        this.dateproduit = dateproduit;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        this.favorites = favorites;
    }

    public Boutique getBoutiquesProduit() {
        return boutiquesProduit;
    }

    public void setBoutiquesProduit(Boutique boutiquesProduit) {
        this.boutiquesProduit = boutiquesProduit;
    }

    public Set<CartItem> getCartItemProd() {
        return cartItemProd;
    }

    public void setCartItemProd(Set<CartItem> cartItemProd) {
        this.cartItemProd = cartItemProd;
    }

    public List<Produit> getListproduitAssociers() {
        return listproduitAssociers;
    }

    public void setListproduitAssociers(List<Produit> listproduitAssociers) {
        this.listproduitAssociers = listproduitAssociers;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Date getDateFinPromotion() {
        return dateFinPromotion;
    }

    public void setDateFinPromotion(Date dateFinPromotion) {
        this.dateFinPromotion = dateFinPromotion;
    }

    public Date getDateDebutPromotion() {
        return dateDebutPromotion;
    }

    public void setDateDebutPromotion(Date dateDebutPromotion) {
        this.dateDebutPromotion = dateDebutPromotion;
    }

    public double getPourcentagepromotion() {
        return pourcentagepromotion;
    }

    public void setPourcentagepromotion(double pourcentagepromotion) {
        this.pourcentagepromotion = pourcentagepromotion;
    }

    public Boolean getPromoAcitve() {
        return promoAcitve;
    }

    public void setPromoAcitve(Boolean promoAcitve) {
        this.promoAcitve = promoAcitve;
    }

    public String getQcode() {
        return qcode;
    }

    public void setQcode(String qcode) {
        this.qcode = qcode;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<InventaireItem> getInventaireItemList() {
        return inventaireItemList;
    }

    public void setInventaireItemList(List<InventaireItem> inventaireItemList) {
        this.inventaireItemList = inventaireItemList;
    }


    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    /* constructeur**/
    public Produit(String nomProduit, int prixProduit, String referenceProduit, String photoProduit, boolean arrivage,
                   Date dateproduit, Boutique boutiquesProduit, Set<CartItem> cartItemProd, List<Produit> listproduitAssociers) throws ParseException {
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;


//        this.referenceProduit = "PDT-"+boutiquesProduit.getNomBoutique()+"- "+ UUID.randomUUID().toString().substring(26).toUpperCase();
        this.photoProduit = photoProduit;
        this.arrivage = arrivage;
        this.dateproduit = dateproduit;
        this.boutiquesProduit = boutiquesProduit;
        this.cartItemProd = cartItemProd;
        this.listproduitAssociers = listproduitAssociers;


    }

    public Produit() {
    }

    public boolean isAfficherprix() {
        return afficherprix;
    }

    public void setAfficherprix(boolean afficherprix) {
        this.afficherprix = afficherprix;
    }
}
