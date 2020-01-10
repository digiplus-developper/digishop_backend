package com.sid.digishopheroku.Metier;


import com.sid.digishopheroku.Model.*;

import java.util.List;

public interface MetierFavorites {

    /*1- Recuperer la liste des produits favoris d'un utilisateur*/
    List<Produit> produitsFavoris_dun_User(AppUser user);

    /*2- Recuperer la liste des boutiques favorites d'un utilisateur*/
    List<Boutique> boutiquesFavoris_dun_User(AppUser user);

    /*3 Recuperer la liste de tous les favoris confondus*/
    List<Favorites> favoritesList();

    /* Tous les produits existant dans Favoris sans repetition*/
    List<Produit> AllProduitsFavoris();

    /* Tous les boutique existant dans Favoris sans repetition*/
    List<Boutique> AllBoutiqueFavoris();

    /* boutiques ayant le plus d'occurences dans les favoris des users
     * on pourra utiliser pour lister les boutiques les plus aimées*/
    List<Boutique> boutiques_les_plus_favorites();

    /*produits ayant le plus d'occurences dans ls favoris des users
     * on pourra utiliser pour lister les produits les plus aimés*/
    List<Produit> produuits_les_plus_favoris();

    /* Boutique apparaissant le plus dans les favoris des users
     * on peut considerer qu'elle est la plus aimee*/
    Boutique boutique_favorite();

    /* Produit apparaissant le plus dans les favoris des users
     * on peut considerer qu'il est la plus aimé*/

    Produit produit_favoris();

    /*4- Ajout d un produit au favoris*/
    boolean Ajouter_Produit_to_Favoris(Produit produit, AppUser user);

    /* 5- ajout d un Boutique au favoris*/
    boolean Ajouter_Boutique_au_Favoris(Boutique boutique, AppUser user);

    /* Retirer un produit des favoris de l User*/
    void Retirer_Produit_desFavoris(Produit produit, AppUser user);

    /* Retirer tous les produits dans les Favoris d un User*/
    void Retirer_Tous_lesProduits(AppUser user);

    /* Retirer une boutique des favoris de l User*/
    void  Retirer_Boutique_desFavoris(Boutique boutique, AppUser user);

    /* Retirer toutes les boutiques dans les Favoris d un User*/
    void Retirer_toutes_lesBoutique(AppUser user);

    /* effacer tout le contenu(Boutique Produit) des favoris d un User*/
    void Vider_Favoris(AppUser user);

    Favorites addFavorites(Favorites favorites);


    Favorites findByUser(AppUser user);



    /* suppression des doublons*/
    List<Boutique> suppression_doublons_boutiqueList(List<Boutique> boutiques);

    List<Produit> suppression_doublons_produitList(List<Produit> produits);

    List<Produit> x_meilleurs_produits(int x);
    List<Boutique> x_meilleures_boutiques(int x);


}
