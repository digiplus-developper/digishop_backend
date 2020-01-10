package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.AppUserRepository;
import com.sid.digishopheroku.IDaoRepository.BoutiqueRepository;
import com.sid.digishopheroku.IDaoRepository.FavoritesRepository;
import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.Metier.MetierFavorites;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Favorites;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Transactional
@Service(value = "servicefavorites")
public class ServiceFavorites implements MetierFavorites {
    @Autowired
    FavoritesRepository favoritesRepository;

    @Autowired AppUserRepository userRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    BoutiqueRepository boutiqueRepository;

    @Override
    public Favorites findByUser(AppUser user) {
        return favoritesRepository.findByAppUser(user);
    }

    @Override
    public List<Produit> produitsFavoris_dun_User(AppUser user) {
        Favorites f = favoritesRepository.findByAppUser(user);
        if (f==null){
            return null;
        }else {
            List<Produit> produits = f.getProduitsfavotisList();
            return produits;
        }
    }

    @Override
    public List<Boutique> boutiquesFavoris_dun_User(AppUser user) {
        Favorites f = favoritesRepository.findByAppUser(user);
        if (f==null){
            return null;
        }else {
            List<Boutique> boutiques = f.getBoutiquefavorisList();
            return boutiques;
        }
    }

    @Override
    public List<Favorites> favoritesList() {
        System.out.println(" Ave Cesaria " + favoritesRepository.findAll().size());
        return favoritesRepository.findAll();
    }

    @Override
    public List<Produit> AllProduitsFavoris() {
        List<Produit> produits = new ArrayList<>();
        List<Favorites> favoritesList = favoritesRepository.findAll();
        Favorites favoris = new Favorites();

        for (Favorites favorites : favoritesList){
            List<Produit> produits1 = favorites.getProduitsfavotisList();
            produits.addAll(produits1);
        }
        favoris.setProduitsfavotisList(produits);
        return favoris.getProduitsfavotisList();
    }

    @Override
    public List<Boutique> AllBoutiqueFavoris() {
        List<Boutique> boutiques = new ArrayList<>();

        List<Favorites> favoritesList = favoritesRepository.findAll();

        Favorites favoris = new Favorites();


        for (Favorites favorites : favoritesList){
            List<Boutique> boutiques1 = favorites.getBoutiquefavorisList();

            boutiques.addAll(boutiques1);
        }
        favoris.setBoutiquefavorisList(boutiques);
/*
        for (Favorites favorites : favoritesList){
            List<Boutique> boutiques1 = favorites.getBoutiquefavorisList();
            for (Boutique b : boutiques1){
                for (Boutique boutique : boutiques){
                    if (b.getIdBoutique()!=boutique.getIdBoutique());
                    boutiques.add(b);
                }
            }
        }
 */
        return favoris.getBoutiquefavorisList();
    }

    @Override
    public List<Boutique> suppression_doublons_boutiqueList(List<Boutique> boutiques) {
        List<Boutique> list = new ArrayList<>();
        for (Boutique b : boutiques){

            if (!(list.contains(b))) {
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public List<Produit> suppression_doublons_produitList(List<Produit> produits) {
        List<Produit> list = new ArrayList<>();
        for (Produit p : produits){

            if (!(list.contains(p))) {
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<Boutique> boutiques_les_plus_favorites() {
        /*toutes les boutiques dans les favoris*/
        List<Boutique> boutiques = suppression_doublons_boutiqueList(AllBoutiqueFavoris());
        List<Boutique> list = AllBoutiqueFavoris();

        /* tableau pour le tri des boutiques*/
        Boutique[] classementBoutique = new Boutique[boutiques.size()];
        /* nombre d occurence de chaque boutique*/
        int [] occ_b = new  int[boutiques.size()];

        for(Boutique b:boutiques){
            int j=0;
            for (Boutique boutique : list){
                if (b.getIdBoutique().equals(boutique.getIdBoutique())){
                    j++;
                }
            }
            classementBoutique[boutiques.indexOf(b)]= b;
            occ_b[boutiques.indexOf(b)]=j;
            b.setNbreDeFavoris(j);
        }
        // return tri_boutique_parFavoris(classementBoutique,occ_b);
        return boutiques;
    }

    @Override
    public List<Produit> produuits_les_plus_favoris() {
        List<Produit> produits = suppression_doublons_produitList(AllProduitsFavoris());
        List<Produit> list = AllProduitsFavoris();

        Produit[] classementProduit = new  Produit[produits.size()];
        int[] occ_p = new int[produits.size()];

        for (Produit p : produits){
            int i=0;
            for (Produit produit : list){
                if (p.getIdProduit().equals(produit.getIdProduit())){
                    i++;
                }
            }
            classementProduit[produits.indexOf(p)] = p;
            occ_p[produits.indexOf(p)]= i;
            p.setNbreDeFavoris(i);
        }
        return produits;
    }

    /*tri des produits par ordre des favoris decroissant*/
    List<Produit> tri_produit_parFavoris(Produit[] produits, int [] occurence){
        for (int i =0;i<occurence.length;i++){

            if (occurence[i]<occurence[i+1]){
                int tampon = occurence[i];
                occurence[i]=occurence[i+1];
                occurence[i+1] = tampon;

                Produit temp =produits[i];
                produits[i] = produits[i+1];
                produits[i+1] =temp;
            }
        }
        List<Produit> p = new ArrayList<>(Arrays.asList(produits));
        return p;

    }

    /*tri des boutiquess par ordre des favoris decroissant*/
    List<Boutique> tri_boutique_parFavoris(Boutique[] boutiques, int[] occ){
        for (int j=0; j<occ.length;++j){
            if (occ[j]<occ[j+1]){
                int tampon = occ[j];
                occ[j] = occ[j+1];
                occ[j+1] = tampon;

                Boutique b = boutiques[j];
                boutiques[j] = boutiques[j+1];
                boutiques[j+1] = b;
            }
        }
        List<Boutique> boutiqueList = new ArrayList<>(Arrays.asList(boutiques));
        return boutiqueList;
    }


    @Override
    public Boutique boutique_favorite() {
        return boutiques_les_plus_favorites().get(0);
    }

    @Override
    public Produit produit_favoris() {
        return produuits_les_plus_favoris().get(0);
    }


    @Override
    public boolean Ajouter_Produit_to_Favoris(Produit produit, AppUser user) {
        List<Produit> produitList = produitsFavoris_dun_User(user);

        //Favorites favorites = favoritesRepository.findByAppUser(user);
        produitList.add(produit);
        // favorites.setProduitsfavotisList(produitList);
        favoritesRepository.findByAppUser(user).setProduitsfavotisList(produitList);
        produit.setNbreDeFavoris(produit.getNbreDeFavoris()+1);
        produitRepository.save(produit);
        return true;
           /*
       for (Produit p : produitList){
            if (produit.getIdProduit()==p.getIdProduit()){
               System.out.println("le produit est deja dans vos Favoris.");
            }
        }
        */

        /*if (produitList == null  || favorites==null){
            List<Produit> produits = new ArrayList<>();
            produits.add(produit);
                //favorites.setProduitsfavotisList(produits);
                user.setFavorites(favorites);
                //favoritesRepository.save(favorites);
                System.out.println("le produit " + produit.getIdProduit() + " a bien été ajouté à vos Favoris.");
        }else {
            produitList.add(produit);
            System.out.println(produitList+"\n\n");
            favorites.getProduitsfavotisList().add(produit);
            //favorites.setProduitsfavotisList(favorites.getProduitsfavotisList().add(produit));
            user.setFavorites(favorites);
            //favoritesRepository.save(favorites);
            System.out.println("le produit " + produit.getIdProduit() + " a correctement été ajouté à vos Favoris.");
        }
*/
    }

    @Override
    public boolean Ajouter_Boutique_au_Favoris(Boutique boutique, AppUser user) {
        List<Boutique> boutiqueList = boutiquesFavoris_dun_User(user);

        boutiqueList.add(boutique);
        // Favorites favorites = favoritesRepository.findByAppUser(user);
        //favorites.setBoutiquefavorisList(boutiqueList);

        favoritesRepository.findByAppUser(user).setBoutiquefavorisList(boutiqueList);
        //   System.out.println("la Boutique " + boutique.getIdBoutique() + " a bien été ajouté à vos Favoris.");
        boutique.setNbreDeFavoris(boutique.getNbreDeFavoris()+1);
        boutiqueRepository.save(boutique);
        return true;

    }

    @Override
    public void Retirer_Produit_desFavoris(Produit produit, AppUser user) {
        List<Produit> produitList = produitsFavoris_dun_User(user);
        System.out.println("la taille des produits est: "+produitList.size());
        for (Produit p : produitList){
            if (produit.getIdProduit() == p.getIdProduit()){
                produitList.remove(p);
            }
        }
        Favorites favorites = favoritesRepository.findByAppUser(user);
        favorites.setProduitsfavotisList(produitList);
        favoritesRepository.save(favorites);
        produit.setNbreDeFavoris(produit.getNbreDeFavoris()-1);
        produitRepository.save(produit);
    }

    @Override
    public void Retirer_Boutique_desFavoris(Boutique boutique, AppUser user) {
        List<Boutique> boutiqueList = boutiquesFavoris_dun_User(user);
        for (Boutique b : boutiqueList){
            if (Objects.equals(boutique.getIdBoutique(), b.getIdBoutique())){
                boutiqueList.remove(b);
            }
        }
        Favorites favorites = favoritesRepository.findByAppUser(user);
        favorites.setBoutiquefavorisList(boutiqueList);
        favoritesRepository.save(favorites);
        boutique.setNbreDeFavoris(boutique.getNbreDeFavoris()-1);

    }

    @Override
    public void Vider_Favoris(AppUser user) {
        Favorites favorites = favoritesRepository.findByAppUser(user);
        for (Produit produit : favorites.getProduitsfavotisList()){
            produit.setNbreDeFavoris(produit.getNbreDeFavoris()-1);
        }
        for (Boutique boutique : favorites.getBoutiquefavorisList()){
            boutique.setNbreDeFavoris(boutique.getNbreDeFavoris()-1);
        }

        favorites.setBoutiquefavorisList(null);
        favorites.setProduitsfavotisList(null);
        favoritesRepository.save(favorites);

    }


    @Override
    public void Retirer_Tous_lesProduits(AppUser user) {

        Favorites favorites = favoritesRepository.findByAppUser(user);

        for (Produit produit : favorites.getProduitsfavotisList()){
            produit.setNbreDeFavoris(produit.getNbreDeFavoris()-1);
        }

        favorites.setProduitsfavotisList(null);
        favoritesRepository.save(favorites);
    }

    @Override
    public void Retirer_toutes_lesBoutique(AppUser user) {
        Favorites favorites = favoritesRepository.findByAppUser(user);
        for (Boutique boutique : favorites.getBoutiquefavorisList()){
            boutique.setNbreDeFavoris(boutique.getNbreDeFavoris()-1);
        }

        favorites.setBoutiquefavorisList(null);
        favoritesRepository.save(favorites);
    }

    @Override
    public Favorites addFavorites(Favorites favorites) {
        return favoritesRepository.save(favorites);
    }

    @Override
    public List<Produit> x_meilleurs_produits(int x) {
        return produitRepository.findByNbreDeFavorisGreaterThanOrderByNbreDeFavorisDesc(x);
    }

    @Override
    public List<Boutique> x_meilleures_boutiques(int x) {
        return boutiqueRepository.findByNbreDeFavorisGreaterThanOrderByNbreDeFavoris(x);
    }

}