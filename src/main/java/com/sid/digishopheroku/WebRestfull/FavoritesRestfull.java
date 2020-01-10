package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.IDaoRepository.AppUserRepository;
import com.sid.digishopheroku.IDaoRepository.FavoritesRepository;
import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Metier.MetierFavorites;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Favorites;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/favorites")
public class FavoritesRestfull {
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    MetierFavorites metierFavorites;
    @Autowired
    MetierProduit metierProduit;
    @Autowired
    FavoritesRepository favoritesRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    MetierBoutique metierBoutique;
    /*********************************** PRODUITS **********************************/
    /*Recuperer la liste des produits qu'un Utilisateur a ajoute dans ses favoris */
  /*
   @GetMapping("/produits")
    public List<Produit> favorites_Produits(Principal principal){
        AppUser user = userRepository.findByUsername(principal.getName());
        return  metierFavorites.produitsFavoris_dun_User(user);
    }
    */
    /*Recuperer la liste des produits qu'un Utilisateur a ajoute dans ses favoris ***********************/
    @GetMapping("/produits/user/{iduser}")
    public List<Produit> favorites_Produits(@PathVariable Long iduser){

        AppUser user = userRepository.findUserById(iduser);

        return  metierFavorites.produitsFavoris_dun_User(user);
    }
    /* Ajouter un produit aux Favoris d 'un Utilisateur ********************************/
    @GetMapping("/addProduit/{id_product}/user/{iduser}")
    public  boolean AddProduct_To_Favorites(@PathVariable Long id_product, @PathVariable Long iduser){

        Produit produit = metierProduit.findproduiById(id_product);
        AppUser user = userRepository.findUserById(iduser);
        /* verifie si la Boutique existe deja dans la liste des Boutiques favorites de l user */
        List<Produit> produitList = metierFavorites.produitsFavoris_dun_User(user);
        if (!(produitList.contains(produit))){
            metierFavorites.Ajouter_Produit_to_Favoris(produit,user);
            System.out.println(produit.getNomProduit() + " successfully added to your Favorites!");
            return true;
        }else {
            System.out.println(produit.getNomProduit() + " existe deja dans vos Favoris");
            return false;
        }

    }
    /* Supprimer toutes les produits dans les favoris  d un User */
    @RequestMapping("clear/allProduits/{iduser}")
    public void clearProduits(@PathVariable Long iduser){
        AppUser user =  userRepository.findUserById(iduser);
        metierFavorites.Retirer_Tous_lesProduits(user);

    }

    /* Retirer un produit des Favoris d un Utilisateur */
    @RequestMapping("clear/produit/{id}/user/{iduser}")
    public void removeProduit(@PathVariable Long id,@PathVariable Long iduser){
        AppUser user =  userRepository.findUserById(iduser);
        Produit produit = produitRepository.findPrByid(id);
        metierFavorites.Retirer_Produit_desFavoris(produit,user);

    }

    /* Recuperer toutes les boutiques ayant une occurence dans un Favoris*/
    @GetMapping("produits/Allproduits/")
    public List<Produit> allProduitsFavorites(){

        return metierFavorites.suppression_doublons_produitList(metierFavorites.AllProduitsFavoris());
    }
    /* les x produits les plus aimés des Utilisateurs */
    @GetMapping("prduits/top-{x}")
    public List<Produit> x_meilleursproduits(@PathVariable int x){
        return metierFavorites.x_meilleurs_produits(x);
    }


    /* classer les Produits par fovoris decroissants -- les meilleurs produits --  */
    @GetMapping("produits/top")
    public  List<Produit> findMostFavoritesProduits(){
        return metierFavorites.produuits_les_plus_favoris();
    }

    /* le produit ayant le plus d occurence dans les favoris des Users*/
    @GetMapping("produits/topProduit")
    public Produit produit_le_plus_aime(){
        return metierFavorites.produit_favoris();
    }

    /*********************************************** Boutique ************************/
    /* Ajouter une Boutique aux Favoris d'un Utilisateur */
    @GetMapping("addBoutique/{id_shop}/user/{iduser}")
    public boolean AddBoutique_To_Favorites(@PathVariable Long id_shop, @PathVariable(name = "iduser")Long iduser){
        Boutique boutique =  metierBoutique.getBoutiqueById(id_shop);
        AppUser user = userRepository.findUserById(iduser);

        /* verifie si la Boutique existe deja dans la liste des Boutiques favorites de l user */
        List<Boutique> boutiqueList = metierFavorites.boutiquesFavoris_dun_User(user);
        if (!(boutiqueList.contains(boutique))){
            metierFavorites.Ajouter_Boutique_au_Favoris(boutique,user);
            System.out.println(boutique.getNomBoutique() + " successfully added to your Favorites!");
            return true;
        }else {
            System.out.println(boutique.getNomBoutique() + " existe deja dans vos Favoris");
            return false;
        }

    }

    /*Recuperer la liste des boutiques qu'un Utilisateur a ajouté dans ses favoris */
    @GetMapping("boutiques/user/{iduser}")
    public List<Boutique> favorites_Boutiques(@PathVariable Long iduser){
        AppUser user = userRepository.findUserById(iduser);
        return metierFavorites.boutiquesFavoris_dun_User(user);
    }
    /* Supprimer toutes les boutiques dans les favoris  d un User */
    @RequestMapping("clear/allBoutiques/{iduser}")
    public void clearBoutiques(@PathVariable Long iduser){
        AppUser user =  userRepository.findUserById(iduser);
        metierFavorites.Retirer_toutes_lesBoutique(user);

    }

    /* Retirer une boutique des Favoris d un Utilisateur */
    @RequestMapping("clear/boutique/{id}/user/{iduser}")
    public void removeBoutique(@PathVariable Long id,@PathVariable Long iduser){
        AppUser user =  userRepository.findUserById(iduser);
        Boutique boutique = metierBoutique.getBoutiqueById(id);

        metierFavorites.Retirer_Boutique_desFavoris(boutique,user);

    }

    /* Recuperer toutes les boutiques ayant une occurence dans un Favoris*/
    @GetMapping("boutiques/Allboutiques/")
    public List<Boutique> allBoutiquesFavorites(){

        return metierFavorites.suppression_doublons_boutiqueList(metierFavorites.AllBoutiqueFavoris());
    }

    /* les x boutiques les plus aimées des Utilisateurs */
    @GetMapping("boutiques/top-{x}")
    public List<Boutique> x_meilleuresboutiques(@PathVariable int x){
        return metierFavorites.x_meilleures_boutiques(x);
    }

    /* classer les Boutiques par fovoris decroissants -- les meilleures boutiques --  */
    @GetMapping("boutiques/top")
    public  List<Boutique> findMostFavoritesBoutiques(){
        return metierFavorites.boutiques_les_plus_favorites();
    }

    /* La Boutique ayant le plus d occurence dans les favoris des Users */
    @GetMapping("boutiques/topBoutique")
    public Boutique boutique_la_plus_aimee(){
        return metierFavorites.boutique_favorite();
    }

    /************************************** Recuperer tous les favoris ********************/
    @GetMapping("All")
    public List<Favorites> findAllFavorites(){

        return metierFavorites.favoritesList();
        //return favoritesRepository.findAll();
    }

    /* Recuperer la table Favoris d un Utilisateur */
    @GetMapping("getByUser/{iduser}")
    public Favorites findFavoritesByUser(@PathVariable Long iduser){

        return metierFavorites.findByUser(userRepository.findUserById(iduser));
    }

    /*  Vider les favoris d un utilisateur */
    @RequestMapping("clear/{iduser}")
    public  void clearFavorite(@PathVariable Long iduser){
        AppUser user =  userRepository.findUserById(iduser);
        metierFavorites.Vider_Favoris(user);
    }



    /* recuperer tous les produits favoris*/
    @GetMapping("AllProduits")
    public List<Produit> findAllProduits(){
        return metierFavorites.AllProduitsFavoris();
    }
}