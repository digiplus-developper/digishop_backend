package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usershop")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id_user;
    @Column(unique=true)
    private String username;
    private String password;

    //ID utilisé pour generer le jeton de verification de mot de passe / publicId
    @Column(nullable = false, length = 50)
    private String userId;
    private String emailVerificationToken;

    @Column(nullable = true, columnDefinition = "boolean default false")
    private Boolean emailVerificationStatus;


    private String firstName;
    private String lastName;
    private String code_confirmation;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private boolean activervendeur=true ;
    private boolean enabled=true;
    @OneToOne(mappedBy = "usercart")
    private Cart cartuser;
    @OneToOne
    private Favorites favorites ;

    private String defaultAdress;
   // private Adresse defaultAddresse;
    @OneToMany(mappedBy = "user")
    private List<Adresse> addresseList;

    @ManyToMany(fetch = FetchType.EAGER)//EAGER veut dire qu'a chaque fois que je charge un utilisateur il charge aussi ses roles
            //FetchType.EAGER : indique que la relation doit être chargée en même temps que l'entité qui la porte.
    List<AppRole> Roles =new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "usercommande",fetch = FetchType.EAGER)
    private Set<Commande> listcommandeuser;
    //association de l user-vendeur a sa boutique
    @ManyToOne
    @JoinColumn(name = "idBoutique")
    private Boutique boutique;
    @OneToMany
    private List<Inventaire> inventaireList;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CashSession> cashSessionList;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    public AppUser() {
    }

    public AppUser(String username, String password, String email, String phone, List<AppRole> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        Roles = roles;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public List<AppRole> getRoles() {
        return Roles;
    }

    public void setRoles(List<AppRole> roles) {
        Roles = roles;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   @JsonIgnore//pour ignorer le mot de passe dans la deserialisation
    public String getPassword() {
        return password;
    }
   @JsonSetter//pour ignorer le mot de passe dans la deserialisation
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Commande> getListcommandeuser() {
        return listcommandeuser;
    }

    public void setListcommandeuser(Set<Commande> listcommandeuser) {
        this.listcommandeuser = listcommandeuser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Cart getCartuser() {
        return cartuser;
    }

    public void setCartuser(Cart cartuser) {
        this.cartuser = cartuser;
    }

    public String getCode_confirmation() {
        return code_confirmation;
    }

    public void setCode_confirmation(String code_confirmation) {
        this.code_confirmation = code_confirmation;
    }

    public boolean isActivervendeur() {
        return activervendeur;
    }

    public void setActivervendeur(boolean activervendeur) {
        this.activervendeur = activervendeur;
    }

    public List<Inventaire> getInventaireList() {
        return inventaireList;
    }

    public void setInventaireList(List<Inventaire> inventaireList) {
        this.inventaireList = inventaireList;
    }


    public List<CashSession> getCashSessionList() {
        return cashSessionList;
    }

    public void setCashSessionList(List<CashSession> cashSessionList) {
        this.cashSessionList = cashSessionList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

//    public Adresse getDefaultAddresse() {
//        return defaultAddresse;
//    }
//
//    public void setDefaultAddresse(Adresse defaultAddresse) {
//        this.defaultAddresse = defaultAddresse;
//    }

    public List<Adresse> getAddresseList() {
        return addresseList;
    }

    public void setAddresseList(List<Adresse> addresseList) {
        this.addresseList = addresseList;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getDefaultAdress() {
        return defaultAdress;
    }

    public void setDefaultAdress(String defaultAdress) {
        this.defaultAdress = defaultAdress;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
